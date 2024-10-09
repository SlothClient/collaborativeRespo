package com.example.springboot.service.Impl;

import com.example.springboot.entity.OrderInfo;
import com.example.springboot.mapper.OrderMapper;
import com.example.springboot.service.OrderService;
import com.example.springboot.utils.Condition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Override
    public List<OrderInfo> getOrdersByPage(String conditionJson) throws JsonProcessingException {
        Condition condition = new ObjectMapper().readValue(conditionJson,Condition.class);
        List<OrderInfo> list = orderMapper.getOrdersByPage(condition);
        if(list.size() == 0 || list == null){
            throw new RuntimeException("没有找到数据！");
        }
        return list;
    }

    @Override
    public int getOrdersCountByCondition(String conditionJson) throws JsonProcessingException {
        Condition condition = new ObjectMapper().readValue(conditionJson,Condition.class);
        return orderMapper.getOrdersCountByCondition(condition);
    }

    @Override
    public List<OrderInfo> getOrdersByCondition(String conditionJson) {
        return null;
    }
}
