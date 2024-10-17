package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.EquipInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.request.EquipInfoReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-10-08
 */
@Mapper
@Repository
public interface EquipInfoMapper extends BaseMapper<EquipInfo> {

    @Select("SELECT MAX(equip_id) AS currentId FROM equip_info WHERE equip_id LIKE 'EQ%'")
    String getCurrentId();

}
