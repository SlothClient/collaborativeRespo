package com.example.springboot.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.ApprovalInfo;
import com.example.springboot.entity.MaintanceInfoDetail;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.mapper.ApprovalInfoMapper;
import com.example.springboot.mapper.MaintanceInfoMapper;
import com.example.springboot.mapper.UserInfoMapper;
import com.example.springboot.request.FiltersReq;
import com.example.springboot.response.ApprovalDetailResp;
import com.example.springboot.response.ApprovalResp;
import com.example.springboot.service.ApprovalInfoService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.springboot.enums.maintenanceCodeEnum.*;

/**
 * @author Lenovo
 * @description 针对表【approval_info】的数据库操作Service实现
 * @createDate 2024-09-24 11:16:44
 */
@Service
public class ApprovalInfoServiceImpl extends ServiceImpl<ApprovalInfoMapper, ApprovalInfo>
        implements ApprovalInfoService {

    @Autowired
    private ApprovalInfoMapper approvalInfoMapper;

    @Autowired
    private MaintanceInfoMapper maintenanceMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public Result<IPage<ApprovalResp>> getApprovalList(FiltersReq filtersReq) {
        // 初始化分页参数
        Page<ApprovalResp> page = new Page<>(filtersReq.getCurrentPage(), filtersReq.getPageSize());

        // 获取当前登录用户和角色信息
        String userId = (String) StpUtil.getLoginId();
        List<String> userRoles = StpUtil.getRoleList(userId);
        Integer responsibleStepOrder = getStepOrdersForRoles(userRoles);

        IPage<ApprovalResp> approvalRespPage = approvalInfoMapper.selectApprovalRespPage(
                page, filtersReq, responsibleStepOrder);

        if (approvalRespPage.getRecords().isEmpty()) {
            return Result.fail("查询失败，稍后重试");
        }

        return Result.success(approvalRespPage);
    }

    @Override
    public Result<ApprovalDetailResp> getApprovalDetail(String planID) {
        ApprovalDetailResp approvalDetailResp = approvalInfoMapper.getApprovalDetail(planID);

        if(approvalDetailResp == null)
        {
            return Result.fail("查询失败，联系后台管理员处理");
        }
        return Result.success(approvalDetailResp);
    }

    @Override
    public Result approve(String planId,String approvalRemark) {

        //当前登录用户
        String userId = (String) StpUtil.getLoginId();
        System.out.println(userId);
        List<String> userRoles = StpUtil.getRoleList(userId);

        int responsibleStepOrder = getStepOrdersForRoles(userRoles);
        System.out.println(responsibleStepOrder);

        if (responsibleStepOrder < 1 || responsibleStepOrder > 2) {
            return Result.fail("您无权审批此保养计划");
        }

        // 当前保养计划
        MaintanceInfoDetail maintanceInfoDetail = maintenanceMapper.selectOne(
                new LambdaQueryWrapper<MaintanceInfoDetail>()
                        .eq(MaintanceInfoDetail::getPlanId, planId)
        );
        if (maintanceInfoDetail == null) {
            return Result.fail("保养计划未找到，请联系管理员");
        }
        if (!maintanceInfoDetail.getStatus().equals(APPROVING.getCode())){
            Result.fail("该保养计划已经处理完成，无需后续处理");
        }
        // 获取一级审批信息
        ApprovalInfo firstStepApproval = getApprovalInfoByStepOrder(planId, 1);
        if (firstStepApproval == null) {
            return Result.fail("一级审批信息未找到，请联系管理员");
        }

        // 一级审批
        if (responsibleStepOrder == 1) {
            if(!Objects.equals(firstStepApproval.getApprovalStatus(), PENDING_APPROVAL.getCode())){
                return Result.fail("您已处理无需重复处理");
            }

            firstStepApproval.setApprovalStatus(APPROVAL_PASS.getCode());
            firstStepApproval.setManipTime(new Timestamp(System.currentTimeMillis()));
            firstStepApproval.setApprovalRemark(approvalRemark);
            firstStepApproval.setApplicantId(userId);
            approvalInfoMapper.updateById(firstStepApproval);
            maintanceInfoDetail.setStatus(APPROVING.getCode());
            maintenanceMapper.updateById(maintanceInfoDetail);
            return Result.success("您已审核成功");
        }

        // 获取二级审批信息
        ApprovalInfo secondStepApproval = getApprovalInfoByStepOrder(planId, 2);
        if (responsibleStepOrder == 2) {
            if (Objects.equals(firstStepApproval.getApprovalStatus(), PENDING_APPROVAL.getCode())) {
                return Result.fail("一级审批未完成，无法进行二级审批");
            }
            if (secondStepApproval == null) {
                return Result.fail("二级审批信息未找到，请联系管理员");
            }
            if(!Objects.equals(secondStepApproval.getApprovalStatus(), PENDING_APPROVAL.getCode())){
                return Result.fail("您已处理无需重复处理");
            }

            secondStepApproval.setApprovalStatus(APPROVAL_PASS.getCode());
            secondStepApproval.setApprovalRemark(approvalRemark);
            secondStepApproval.setManipTime(new Timestamp(System.currentTimeMillis()));
            secondStepApproval.setApplicantId(userId);
            approvalInfoMapper.updateById(secondStepApproval);

            maintanceInfoDetail.setStatus(APPROVAL_PASS.getCode());
            maintenanceMapper.updateById(maintanceInfoDetail);
            return Result.success("您已审核成功，该保养计划已通过，请等待后续派单。");
        }

        return Result.fail("审核失败，出现未知错误，请联系后台管理员");
    }

    @Override
    public Result<String> reject(String planId, String approvalRemark) {
        // 获取当前登录用户
        String userId = (String) StpUtil.getLoginId();
        List<String> userRoles = StpUtil.getRoleList(userId);
        int responsibleStepOrder = getStepOrdersForRoles(userRoles);

        if (responsibleStepOrder < 1 || responsibleStepOrder > 2) {
            return Result.fail("您无权驳回此保养计划");
        }

        // 查询保养计划详情
        MaintanceInfoDetail maintanceInfoDetail = maintenanceMapper.selectOne(
                new LambdaQueryWrapper<MaintanceInfoDetail>()
                        .eq(MaintanceInfoDetail::getPlanId, planId)
        );


        if (maintanceInfoDetail == null) {
            return Result.fail("保养计划未找到，请联系管理员");
        }

        if (!Objects.equals(maintanceInfoDetail.getStatus(), APPROVING.getCode())){
            Result.fail("该保养计划已经处理完成，无需后续处理");
        }

        // 获取一级审批信息
        ApprovalInfo firstStepApproval = getApprovalInfoByStepOrder(planId, 1);
        if (firstStepApproval == null) {
            return Result.fail("一级审批信息未找到，请联系管理员");
        }

        // 一级审批驳回逻辑
        if (responsibleStepOrder == 1) {
            if (!Objects.equals(firstStepApproval.getApprovalStatus(), PENDING_APPROVAL.getCode())) {
                return Result.fail("该保养计划已处理，无法重复处理");
            }

            firstStepApproval.setApprovalStatus(APPROVAL_REJECT.getCode()); // 设置审批状态为驳回
            firstStepApproval.setApprovalRemark(approvalRemark);
            firstStepApproval.setManipTime(new Timestamp(System.currentTimeMillis()));
            firstStepApproval.setApplicantId(userId);
            approvalInfoMapper.updateById(firstStepApproval);

            // 更新保养计划状态为驳回
            maintanceInfoDetail.setStatus(APPROVAL_REJECT.getCode());
            maintenanceMapper.updateById(maintanceInfoDetail);

            return Result.success("您已驳回该保养计划");
        }

        // 获取二级审批信息
        ApprovalInfo secondStepApproval = getApprovalInfoByStepOrder(planId, 2);
        if (responsibleStepOrder == 2) {
            if (Objects.equals(firstStepApproval.getApprovalStatus(), PENDING_APPROVAL.getCode())) {
                return Result.fail("一级审批未完成，无法进行二级审批驳回");
            }
            if (secondStepApproval == null) {
                return Result.fail("二级审批信息未找到，请联系管理员");
            }
            if (!Objects.equals(secondStepApproval.getApprovalStatus(), PENDING_APPROVAL.getCode())) {
                return Result.fail("该保养计划已处理，无法重复处理");
            }

            secondStepApproval.setApprovalStatus(APPROVAL_REJECT.getCode()); // 设置审批状态为驳回
            secondStepApproval.setApprovalRemark(approvalRemark);
            secondStepApproval.setManipTime(new Timestamp(System.currentTimeMillis()));
            secondStepApproval.setApplicantId(userId);
            approvalInfoMapper.updateById(secondStepApproval);

            // 更新保养计划状态为驳回
            maintanceInfoDetail.setStatus(APPROVAL_REJECT.getCode());
            maintenanceMapper.updateById(maintanceInfoDetail);

            return Result.success("您已驳回该保养计划");
        }

        return Result.fail("驳回失败，出现未知错误，请联系后台管理员");
    }

    private ApprovalInfo getApprovalInfoByStepOrder(String planId, int stepOrder) {
        return approvalInfoMapper.selectOne(
                new LambdaQueryWrapper<ApprovalInfo>()
                        .eq(ApprovalInfo::getPlanId, planId)
                        .eq(ApprovalInfo::getStepOrder, stepOrder)
        );
    }

    /**
     * 根据用户角色获取对应的stepOrder
     *
     * @param userRoles 用户角色列表
     * @return 负责的stepOrder
     */
    private Integer getStepOrdersForRoles(List<String> userRoles) {
        // 遍历角色列表，返回对应的stepOrder
        for (String role : userRoles) {
            switch (role) {
                case "R003":
                    return 0;
                case "R002":
                    return 1;  // R002角色对应stepOrder 1
                case "R001":
                    return 2;  // R001角色对应stepOrder 2
                default:
                    break;
            }
        }
        return 0;  // 如果没有匹配到角色，返回默认stepOrder 0
    }
}




