package com.jxutcm.equipmentMaintance.Dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jxutcm.equipmentMaintance.Bean.Condition;
import com.jxutcm.equipmentMaintance.Bean.OrderInfo;
import com.jxutcm.equipmentMaintance.Bean.OrderInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {
    public List<OrderInfo> getOrder(Condition condition);
    public int addOrder(OrderInfo orderInfo);
    public int delDocInfo(OrderInfo orderInfo);
    public int updOrder(OrderInfo orderInfo);
}
