package com.example.springboot.service;

import com.example.springboot.entity.EquipInfo;
import com.example.springboot.entity.OrderInfo;
import com.example.springboot.utils.ResultMap;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface OrderService {
    public List<OrderInfo> getOrdersByPage(String conditionJson) throws JsonProcessingException;
    public int getOrdersCountByCondition(String conditionJson) throws JsonProcessingException;
    public List<OrderInfo> getOrdersByCondition(String conditionJson);
    public List<EquipInfo> getSelectedEquipInfo(String conditionJson) throws JsonProcessingException;
    public void addWorkRecord(String conditionJson) throws JsonProcessingException;
    public void submitOrder(String conditionJson) throws JsonProcessingException;
}
