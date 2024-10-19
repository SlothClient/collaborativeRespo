package com.example.springboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.springboot.entity.MaintanceType;
import com.example.springboot.response.EquipmentResp;
import com.example.springboot.service.EquipInfoService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ys
 * @date： 2024/9/25 16:12
 */

@RestController
@RequestMapping("/equipment")
public class equipInfoController {

    @Autowired
    private EquipInfoService equipInfoService;

    @SaCheckLogin
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
