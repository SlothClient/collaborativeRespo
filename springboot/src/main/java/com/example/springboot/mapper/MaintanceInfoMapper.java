package com.example.springboot.mapper;

import com.example.springboot.entity.MaintanceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.request.MaintenancePlanReq;
import com.example.springboot.response.MaintenanceResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【maintance_info】的数据库操作Mapper
* @createDate 2024-09-24 11:04:20
* @Entity com.example.springboot.entity.MaintanceInfo
*/

@Mapper
@Repository
public interface MaintanceInfoMapper extends BaseMapper<MaintanceInfo> {


    List<MaintenanceResp> getMaintenancePlan(@Param("maintenancePlanReq") MaintenancePlanReq maintenancePlanReq);
}




