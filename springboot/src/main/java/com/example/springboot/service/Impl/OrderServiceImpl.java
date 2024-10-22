package com.example.springboot.service.Impl;

import com.example.springboot.entity.EquipInfo;
import com.example.springboot.entity.MaintanceInfoDetail;
import com.example.springboot.entity.OrderInfo;
import com.example.springboot.mapper.MaintanceInfoMapper;
import com.example.springboot.mapper.OrderMapper;
import com.example.springboot.service.OrderService;
import com.example.springboot.utils.Condition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.springboot.enums.maintenanceCodeEnum.PLAN_DISPATCHED;
import static com.example.springboot.enums.maintenanceCodeEnum.PLAN_FINISH;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    MaintanceInfoMapper maintanceInfoMapper;

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

    @Override
    public List<EquipInfo> getSelectedEquipInfo(String conditionJson) throws JsonProcessingException {
        Condition condition = new ObjectMapper().readValue(conditionJson,Condition.class);
        return orderMapper.getSelectedEquipInfo(condition);
    }

    @Override
    public void addWorkRecord(String conditionJson) throws JsonProcessingException {
        Condition condition = new ObjectMapper().readValue(conditionJson,Condition.class);
        int count = orderMapper.addWorkRecord(condition);
        if(count == 0){
            throw new RuntimeException("添加工作记录失败！");
        }
    }

    @Override
    public void submitOrder(String conditionJson) throws JsonProcessingException {
        Condition condition = new ObjectMapper().readValue(conditionJson,Condition.class);
        int count = orderMapper.submitOrder(condition);
        int count1 = orderMapper.autoAddHours(condition);
        if(count == 0){
            throw new RuntimeException("结单错误！");
        }
        if(count1 == 0){
            throw new RuntimeException("自动增加工时错误！");
        }
        if(condition.getPlanId()!=null){
            MaintanceInfoDetail maintanceInfoDetail = maintanceInfoMapper.selectById(condition.getPlanId());
            maintanceInfoDetail.setStatus(PLAN_FINISH.getCode());
            maintanceInfoMapper.updateById(maintanceInfoDetail);
        }
    }
}
