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

        List<MaintenanceResp> maintenanceRespList =  maintenanceMapper.getMaintenancePlan(maintenancePlanReq);

        if(maintenanceRespList.isEmpty())
        {
            return Result.fail("查询为空");
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




