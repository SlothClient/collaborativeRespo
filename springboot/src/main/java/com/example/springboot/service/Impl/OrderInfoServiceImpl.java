package com.example.springboot.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.OrderInfo;
import com.example.springboot.service.OrderInfoService;
import com.example.springboot.mapper.OrderInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【order_info】的数据库操作Service实现
* @createDate 2024-10-07 20:47:01
*/
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo>
    implements OrderInfoService{

}




