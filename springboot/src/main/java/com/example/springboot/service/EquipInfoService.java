package com.example.springboot.service;

import com.example.springboot.entity.EquipInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.MaintanceType;
import com.example.springboot.response.EquipmentResp;
import com.example.springboot.utils.Result;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【equip_info】的数据库操作Service
* @createDate 2024-09-25 16:11:30
*/
public interface EquipInfoService extends IService<EquipInfo> {

    Result<List<EquipmentResp>> getEquipmentInfo();

    Result<List<MaintanceType>> getEquipmentMaintenanceType();
}
