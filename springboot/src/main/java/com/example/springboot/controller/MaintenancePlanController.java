package com.example.springboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.MaintanceInfoDetail;
import com.example.springboot.entity.MaintanceType;
import com.example.springboot.entity.WorkerInfo;
import com.example.springboot.request.MaintenancePlanReq;
import com.example.springboot.request.WorkOrderReq;
import com.example.springboot.response.EquipmentResp;
import com.example.springboot.response.MaintenanceInfo;
import com.example.springboot.service.EquipInfoService;
import com.example.springboot.service.MaintanceInfoService;
import com.example.springboot.service.WorkerInfoService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ys
 * @date： 2024/9/24 10:24
 */

@RestController
@RequestMapping("/maintenance")
public class MaintenancePlanController {

    @Autowired
    MaintanceInfoService maintanceInfoService;

    @Autowired
    WorkerInfoService workerInfoService;

    @Autowired
    EquipInfoService equipInfoService;

    /**
     * 查询所有保养计划列表
     *
     * @param maintenancePlanReq
     * @return {@link List< MaintenanceInfo >}
     */
    @SaCheckLogin
    @ResponseBody
    @PostMapping("/getMaintenancePlan")
    public Result<IPage<MaintenanceInfo>> getMaintenancePlan(@RequestBody(required = false) MaintenancePlanReq maintenancePlanReq) {
        return maintanceInfoService.getMaintenancePlan(maintenancePlanReq);
    }

    /**
     * 添加保养计划
     * @param maintenanceInfo
     * @return
     */
    @SaCheckLogin
    @ResponseBody
    @PostMapping("/addMaintenancePlan")
    public Result addMaintenancePlan(@RequestBody MaintenanceInfo maintenanceInfo) {
         return maintanceInfoService.addMaintenancePlan(maintenanceInfo);
    }

    /**
     * 撤销保养计划
     * @param planId
     * @return
     */
    @SaCheckLogin
    @ResponseBody
    @GetMapping("/undoMaintenancePlan")
    public Result undoMaintenancePlan(@RequestParam("planId") String planId){
        return maintanceInfoService.undoMaintenancePlan(planId);
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/getMaintenancePlanDetail")
    public Result getMaintenancePlanDetail(@RequestParam("planId") String planId){
        return maintanceInfoService.getMaintenancePlanDetail(planId);
    }


    @SaCheckLogin
    @ResponseBody
    @PostMapping("/updateMaintenance")
    public Result updateMaintenance(@RequestBody MaintanceInfoDetail maintanceInfoDetail){
        System.out.println(maintanceInfoDetail);
        return maintanceInfoService.updateMaintenance(maintanceInfoDetail);
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/getWorkerInfo")
    public Result<List<WorkerInfo>>getWorkerInfo(){
        return workerInfoService.getWorkerInfo();
    }

    @ResponseBody
    @PostMapping("/addWorkOrder")
    public Result<String> addWorkOrder(@RequestBody List<WorkOrderReq> workOrderReqList){
        return workerInfoService.addWorkOrder(workOrderReqList);
    }
    @ResponseBody
    @PostMapping("/getEquipmentInfo")
    public Result<List<EquipmentResp>> getEquipmentInfo(){
        return equipInfoService.getEquipmentInfo();
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/getEquipmentMaintenanceType")
    public Result<List<MaintanceType>> getEquipmentMaintenanceType(){
        return equipInfoService.getEquipmentMaintenanceType();
    }

}
