package com.example.springboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.springboot.request.MaintenancePlanReq;
import com.example.springboot.response.MaintenanceResp;
import com.example.springboot.service.MaintanceInfoService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @SaCheckLogin
    @PostMapping("/getMaintenancePlan")
    public Result<List<MaintenanceResp>> getMaintenancePlan(MaintenancePlanReq maintenancePlanReq){
        return maintanceInfoService.getMaintenancePlan(maintenancePlanReq);
    }

    @SaCheckLogin
    @PostMapping("getAll")
    public  Result getAllMaintenancePlan(){
        return maintanceInfoService.getAllMaintenancePlan();
    }
}
