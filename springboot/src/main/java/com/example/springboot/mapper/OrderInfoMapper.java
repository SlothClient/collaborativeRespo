package com.example.springboot.mapper;

import com.example.springboot.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author Lenovo
* @description 针对表【order_info】的数据库操作Mapper
* @createDate 2024-10-07 20:47:01
* @Entity com.example.springboot.entity.OrderInfo
*/

@Mapper
@Repository
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}




