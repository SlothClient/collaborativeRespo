package com.example.springboot.mapper;

import com.example.springboot.entity.EquipStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-10-12
 */
@Mapper
@Repository
public interface EquipStatusMapper extends BaseMapper<EquipStatus> {

}
