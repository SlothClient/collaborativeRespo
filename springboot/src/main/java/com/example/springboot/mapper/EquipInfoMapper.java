package com.example.springboot.mapper;

import com.example.springboot.entity.EquipInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author Lenovo
* @description 针对表【equip_info】的数据库操作Mapper
* @createDate 2024-09-25 16:11:30
* @Entity com.example.springboot.entity.EquipInfo
*/

@Mapper
@Repository
public interface EquipInfoMapper extends BaseMapper<EquipInfo> {

}




