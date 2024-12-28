package com.example.springboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.EquipInfo;
import com.example.springboot.entity.MaintanceType;
import com.example.springboot.request.EquipInfoReq;
import com.example.springboot.response.EquipmentResp;
import com.example.springboot.service.EquipInfoService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equip")
public class EquipInfoController {
    @Autowired
    private EquipInfoService equipInfoService;

    //     条件查询
//    @PostMapping("/getEquipInfoList")
//    @ResponseBody
//    public R getEquipInfoList(@RequestBody EquipInfoReq equipInfoReq) {
//        PageInfo<EquipInfo> list = equipInfoService.getEquipInfoList(equipInfoReq);
//       return R.success(list);
//    }
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
    //     条件查询
    @PostMapping("/getEquipInfoList")
    @ResponseBody
    public Result<IPage<EquipInfo>> getEquipInfoList(@RequestBody(required = false) EquipInfoReq equipInfoReq) {
        System.out.println(equipInfoReq);
        return equipInfoService.getEquipInfoList(equipInfoReq);
    }
    @SaCheckLogin
    @ResponseBody
    @PostMapping("/addEquipInfo")
    public Result addEquipInfo(@RequestBody EquipInfo equipInfo) {
        System.out.println(equipInfo);
        return equipInfoService.addEquipInfo(equipInfo);
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/updateEquipInfo")
    public Result updateEquipInfo(@RequestBody EquipInfo equipInfo) {
        System.out.println(equipInfo);
        return equipInfoService.updateEquipInfo(equipInfo);
    }

    @GetMapping("/deleteEquipInfo")
    public Result<String> deleteEquipInfo(@RequestParam("equipId") String equipId) {
        return equipInfoService.deleteEquipInfo(equipId);
    }
}
