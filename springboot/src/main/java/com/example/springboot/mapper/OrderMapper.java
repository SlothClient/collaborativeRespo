package com.example.springboot.mapper;

import com.example.springboot.entity.EquipInfo;
import com.example.springboot.entity.OrderInfo;
import com.example.springboot.utils.Condition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface OrderMapper {
    public List<OrderInfo> getOrdersByPage(Condition condition);
    public int getOrdersCountByCondition(Condition condition);
    public List<EquipInfo> getSelectedEquipInfo(Condition condition);
}
