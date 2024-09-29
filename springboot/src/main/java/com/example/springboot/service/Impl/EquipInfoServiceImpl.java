package com.example.springboot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.EquipInfo;
import com.example.springboot.entity.MaintanceType;
import com.example.springboot.mapper.MaintanceTypeMapper;
import com.example.springboot.response.EquipmentResp;
import com.example.springboot.service.EquipInfoService;
import com.example.springboot.mapper.EquipInfoMapper;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lenovo
 * @description 针对表【equip_info】的数据库操作Service实现
 * @createDate 2024-09-25 16:11:30
 */
@Service
public class EquipInfoServiceImpl extends ServiceImpl<EquipInfoMapper, EquipInfo>
        implements EquipInfoService {

    @Autowired
    private EquipInfoMapper equipInfoMapper;

    @Autowired
    private MaintanceTypeMapper maintanceTypeMapper;

    @Override
    public Result<List<EquipmentResp>> getEquipmentInfo() {
        List<EquipInfo> equipInfoList = equipInfoMapper.selectList(
                new LambdaQueryWrapper<EquipInfo>()
                        .select(EquipInfo::getEquipId, EquipInfo::getEquipName)
        );

        if (equipInfoList.isEmpty()) {
            return Result.fail("查询到的设备为空");
        }
        // 将查询结果映射到 EquipmentResp 对象
        List<EquipmentResp> responseList = equipInfoList.stream()
                .map(equipInfo ->
                        EquipmentResp
                                .builder()
                                .equipName(equipInfo.getEquipName())
                                .equipId(equipInfo.getEquipId()).
                                build())
                .collect(Collectors.toList());
        return Result.success(responseList);
    }

    @Override
    public Result<List<MaintanceType>> getEquipmentMaintenanceType() {
        List<MaintanceType> maintanceTypeList = maintanceTypeMapper.selectList(
                new LambdaQueryWrapper<MaintanceType>()
                        .select(MaintanceType::getTypeId,MaintanceType::getMaintanceName)
        );

        if(maintanceTypeList.isEmpty()){
            return Result.fail("类型为空，请稍后重试");
        }
        return Result.success(maintanceTypeList);
    }
}




