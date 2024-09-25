package com.example.springboot.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.ApprovalInfo;
import com.example.springboot.entity.MaintanceInfoDetail;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.mapper.ApprovalInfoMapper;
import com.example.springboot.mapper.UserInfoMapper;
import com.example.springboot.request.MaintenancePlanReq;
import com.example.springboot.response.MaintenanceInfo;
import com.example.springboot.response.MaintenanceInfoResp;
import com.example.springboot.service.MaintanceInfoService;
import com.example.springboot.mapper.MaintanceInfoMapper;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lenovo
 * @description 针对表【maintance_info】的数据库操作Service实现
 * @createDate 2024-09-24 11:04:20
 */
@Service
public class MaintanceInfoServiceImpl extends ServiceImpl<MaintanceInfoMapper, MaintanceInfoDetail>
        implements MaintanceInfoService {

    @Autowired
    private MaintanceInfoMapper maintenanceMapper;

    @Autowired
    private ApprovalInfoMapper approvalInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Result<MaintenanceInfoResp> getMaintenancePlan(MaintenancePlanReq maintenancePlanReq) {

        // 第一步：获取总记录数（不分页）
        int total = maintenanceMapper.getMaintenancePlanCount(maintenancePlanReq);

        // 第二步：获取分页后的数据
        List<MaintenanceInfo> maintenanceRespList = maintenanceMapper.getMaintenancePlan(maintenancePlanReq);

        // 如果查询结果为空，返回查询为空的提示
        if (maintenanceRespList.isEmpty()) {
            return Result.fail("查询为空");
        }

        // 打印总数
        System.out.println(total);

        // 构建返回响应对象
        MaintenanceInfoResp maintenanceInfoResp = MaintenanceInfoResp.builder()
                .maintenanceInfoList(maintenanceRespList)
                .total(total) // 使用从第一个查询获取的总数
                .build();

        return Result.success(maintenanceInfoResp);
    }


    @Override
    public Result addMaintenancePlan(MaintenanceInfo maintenanceInfo) {
        String userId =(String) StpUtil.getLoginId();
        UserInfo userInfo = userInfoMapper.selectById(userId);
        maintenanceInfo.setCreator(userInfo.getUsername());

        MaintanceInfoDetail maintanceInfoDetail = MaintanceInfoDetail
                .builder()
                .maintanceDesc(maintenanceInfo.getMaintanceDesc())
                .maintanceType(maintenanceInfo.getMaintanceType())
                .planName(maintenanceInfo.getPlanName())
                .startTime(maintenanceInfo.getStartTime())
                .endTime(maintenanceInfo.getEndTime())
                .equipId(maintenanceInfo.getEquipId())
                .status(0)
                .build();

        maintenanceMapper.insert(maintanceInfoDetail);

        ApprovalInfo approvalInfoApplicant = ApprovalInfo
                .builder()
                .planId(maintanceInfoDetail.getPlanId())
                .applicantId(userId)
                .approvalStatus(0)
                .stepOrder(0)
                .manipTime(maintenanceInfo.getCreateTime())
                .build();
        approvalInfoMapper.insert(approvalInfoApplicant);

        ApprovalInfo approvalInfoFirst = ApprovalInfo
                .builder()
                .planId(maintanceInfoDetail.getPlanId())
                .approvalStatus(0)
                .stepOrder(2)
                .fatherId(approvalInfoApplicant.getFatherId())
                .build();
        approvalInfoMapper.insert(approvalInfoFirst);

        ApprovalInfo approvalInfoSecond = ApprovalInfo
                .builder()
                .planId(maintanceInfoDetail.getPlanId())
                .approvalStatus(0)
                .stepOrder(2)
                .fatherId(approvalInfoFirst.getFatherId())
                .build();
        approvalInfoMapper.insert(approvalInfoSecond);

        return Result.success("保养计划已成功添加，请等待审批");
    }

}




