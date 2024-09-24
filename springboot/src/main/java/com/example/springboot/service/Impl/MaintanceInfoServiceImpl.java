package com.example.springboot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.ApprovalInfo;
import com.example.springboot.entity.MaintanceInfo;
import com.example.springboot.mapper.ApprovalInfoMapper;
import com.example.springboot.request.MaintenancePlanReq;
import com.example.springboot.response.MaintenanceResp;
import com.example.springboot.service.MaintanceInfoService;
import com.example.springboot.mapper.MaintanceInfoMapper;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lenovo
 * @description 针对表【maintance_info】的数据库操作Service实现
 * @createDate 2024-09-24 11:04:20
 */
@Service
public class MaintanceInfoServiceImpl extends ServiceImpl<MaintanceInfoMapper, MaintanceInfo>
        implements MaintanceInfoService {

    @Autowired
    private MaintanceInfoMapper maintenanceMapper;

    @Autowired
    private ApprovalInfoMapper approvalInfoMapper;

    @Override
    public Result<List<MaintenanceResp>> getMaintenancePlan(MaintenancePlanReq maintenancePlanReq) {

        List<MaintanceInfo> maintanceInfoList = maintenanceMapper.selectList(
                new LambdaQueryWrapper<MaintanceInfo>()
        );

        List<MaintenanceResp> maintenanceRespList = new ArrayList<MaintenanceResp>();

        for (MaintanceInfo maintanceInfo : maintanceInfoList) {

            ApprovalInfo approvalInfoFirst = approvalInfoMapper
                    .selectOne(
                            new LambdaQueryWrapper<ApprovalInfo>()
                                    .eq(ApprovalInfo::getPlanId, maintanceInfo.getPlanId())
                                    .orderByAsc(ApprovalInfo::getManipTime)
                                    .last("LIMIT 1")
                    );
            if (approvalInfoFirst != null) {
                ApprovalInfo approvalInfoLast = approvalInfoMapper
                        .selectOne(
                                new LambdaQueryWrapper<ApprovalInfo>()
                                        .eq(ApprovalInfo::getPlanId, maintanceInfo.getPlanId())
                                        .orderByDesc(ApprovalInfo::getManipTime)
                                        .last("LIMIT 1")
                        );
                MaintenanceResp maintenanceResp = MaintenanceResp.builder()
                        .planId(maintanceInfo.getPlanId())
                        .maintanceType(maintanceInfo.getMaintanceType())
                        .planName(maintanceInfo.getPlanName())
                        .createTime(approvalInfoFirst.getManipTime())
                        .maintanceDesc(maintanceInfo.getMaintanceDesc())
                        .creator(approvalInfoFirst.getApplicantId())
                        .startTime(maintanceInfo.getStartTime())
                        .endTime(maintanceInfo.getEndTime())
                        .equipId(maintanceInfo.getEquipId())
                        .status(maintanceInfo.getStatus())
                        .updatePerson(approvalInfoLast.getApprovalId())
                        .updateTime(approvalInfoLast.getManipTime())
                        .build();
                maintenanceRespList.add(maintenanceResp);
            }
            else {
                MaintenanceResp maintenanceResp = MaintenanceResp.builder()
                        .planId(maintanceInfo.getPlanId())
                        .maintanceType(maintanceInfo.getMaintanceType())
                        .planName(maintanceInfo.getPlanName())
                        .createTime(null)
                        .maintanceDesc(maintanceInfo.getMaintanceDesc())
                        .creator("未开始")
                        .startTime(maintanceInfo.getStartTime())
                        .endTime(maintanceInfo.getEndTime())
                        .equipId(maintanceInfo.getEquipId())
                        .status(maintanceInfo.getStatus())
                        .updatePerson("未开始")
                        .updateTime(null)
                        .build();
                maintenanceRespList.add(maintenanceResp);
            }



        }

        return Result.success(maintenanceRespList);
    }

    @Override
    public Result getAllMaintenancePlan() {
        List<MaintanceInfo> maintanceInfoList = maintenanceMapper.selectList(
                new LambdaQueryWrapper<MaintanceInfo>()
        );

        if(!maintanceInfoList.isEmpty())
            return Result.success(maintanceInfoList.size());
        else
            return Result.fail("记录为空");
    }
}




