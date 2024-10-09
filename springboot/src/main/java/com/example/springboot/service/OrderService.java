package com.example.springboot.service;

import com.example.springboot.entity.OrderInfo;
import com.example.springboot.utils.ResultMap;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface OrderService {
    public List<OrderInfo> getOrdersByPage(String conditionJson) throws JsonProcessingException;
    public int getOrdersCount();
    public List<OrderInfo> getOrdersByCondition(String conditionJson);
}
