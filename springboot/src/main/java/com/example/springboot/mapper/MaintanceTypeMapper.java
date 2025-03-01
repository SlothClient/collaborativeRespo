package com.example.springboot.mapper;

import com.example.springboot.entity.MaintanceType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author Lenovo
* @description 针对表【maintance_type】的数据库操作Mapper
* @createDate 2024-09-25 19:09:25
* @Entity com.example.springboot.entity.MaintanceType
*/

@Mapper
@Repository
public interface MaintanceTypeMapper extends BaseMapper<MaintanceType> {

}




