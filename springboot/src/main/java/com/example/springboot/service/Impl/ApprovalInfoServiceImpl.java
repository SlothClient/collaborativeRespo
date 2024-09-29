package com.example.springboot.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.ApprovalInfo;
import com.example.springboot.mapper.ApprovalInfoMapper;
import com.example.springboot.request.FiltersReq;
import com.example.springboot.response.ApprovalDetailResp;
import com.example.springboot.response.ApprovalResp;
import com.example.springboot.service.ApprovalInfoService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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




