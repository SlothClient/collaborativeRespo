package com.example.springboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.MaintanceInfoDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.request.MaintenancePlanReq;
import com.example.springboot.response.MaintenanceInfo;
import com.example.springboot.utils.Result;

/**
* @author Lenovo
* @description 针对表【maintance_info】的数据库操作Service
* @createDate 2024-09-24 11:04:20
*/
public interface MaintanceInfoService extends IService<MaintanceInfoDetail> {

    Result<IPage<MaintenanceInfo>> getMaintenancePlan(MaintenancePlanReq maintenancePlanReq);

    Result addMaintenancePlan(MaintenanceInfo maintenanceInfo);

    Result undoMaintenancePlan(String planId);

    Result getMaintenancePlanDetail(String planId);

    Result updateMaintenance(MaintanceInfoDetail maintanceInfoDetail);

//    Result<IPage<MaintenanceInfo>> getAdvancedMaintenancePlan(AdvancedSearchReq advancedSearchReq);
}
