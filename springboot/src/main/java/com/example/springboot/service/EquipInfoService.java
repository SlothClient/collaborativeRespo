package com.example.springboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.EquipInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.MaintanceType;
import com.example.springboot.entity.Params;
import com.example.springboot.request.EquipInfoReq;
import com.example.springboot.response.EquipmentResp;
import com.example.springboot.utils.Result;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【equip_info】的数据库操作Service
* @createDate 2024-09-25 16:11:30
*/
public interface EquipInfoService extends IService<EquipInfo> {

    Result<List<EquipmentResp>> getEquipmentInfo();

    Result<List<MaintanceType>> getEquipmentMaintenanceType();
    Result<IPage<EquipInfo>> getEquipInfoList(EquipInfoReq equipInfoReq);

    Result updateEquipInfo(EquipInfo equipInfo);

    Result addEquipInfo(EquipInfo equipInfo);

    Result<String> deleteEquipInfo(String equipId);


//    PageInfo<EquipInfo> getEquipInfoList(@Param("equipInfoReq")EquipInfoReq equipInfoReq);
}
