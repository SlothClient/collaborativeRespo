package com.jxutcm.equipmentMaintance.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jxutcm.equipmentMaintance.Bean.OrderInfo;

import java.util.List;

public interface OrderService {
    public List<OrderInfo> getOrder(String conditionJson) throws JsonProcessingException;
    public void addOrder(String orderJson) throws JsonProcessingException;
    public void delOrder(String dID,String uName);
    public void updOrder(String orderJson) throws JsonProcessingException;
}
