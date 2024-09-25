package com.example.springboot.service;

import com.example.springboot.entity.MaintanceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.request.MaintenancePlanReq;
import com.example.springboot.response.MaintenanceResp;
import com.example.springboot.utils.Result;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【maintance_info】的数据库操作Service
* @createDate 2024-09-24 11:04:20
*/
public interface MaintanceInfoService extends IService<MaintanceInfo> {

    Result<List<MaintenanceResp>> getMaintenancePlan(MaintenancePlanReq maintenancePlanReq);

    Result getAllMaintenancePlan();
}
