package com.jxutcm.equipmentMaintance.Service.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jxutcm.equipmentMaintance.Bean.Condition;
import com.jxutcm.equipmentMaintance.Bean.OrderInfo;
import com.jxutcm.equipmentMaintance.Bean.OrderInfo;
import com.jxutcm.equipmentMaintance.Dao.OrderDao;
import com.jxutcm.equipmentMaintance.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Override
    public List<OrderInfo> getOrder(String conditionJson) throws JsonProcessingException {
        Condition condition = new ObjectMapper().readValue(conditionJson,Condition.class);
        List<OrderInfo> list = orderDao.getOrder(condition);
        if(list.size() == 0 || list == null){
            throw new RuntimeException("没有找到数据！");
        }
        return list;
    }

    @Override
    public void addOrder(String orderJson) throws JsonProcessingException {
        OrderInfo orderInfo = new ObjectMapper().readValue(orderJson,OrderInfo.class);
//        String ID = UUID.randomUUID().toString();
//        orderInfo.set工单编号(ID);
//        orderInfo.setManipUser(uName);
//        Date addTime = new Date(new java.util.Date().getTime());
//        orderInfo.setAddTime(addTime);
        int count = orderDao.addOrder(orderInfo);
        if(count==0){
            throw new RuntimeException("派单失败！");
        }
    }

    @Override
    public void delOrder(String dID, String uName) {

    }


//
//    @Override
//    public void delOrder(String dID, String uName) {
//        if(dID.equals("")){
//            throw new RuntimeException("主键错误，删除行不存在！");
//        }
//        Order docInfo = new Order();
//        docInfo.setManipUser(uName);
//        Date delTime = new Date(new java.util.Date().getTime());
//        docInfo.setDelTime(delTime);
//        docInfo.setdID(dID);
//        int count = docDao.delOrder(docInfo);
//        if(count==0){
//            throw new RuntimeException("删除失败，联系管理员维护！");
//        }
//    }
//
    @Override
    public void updOrder(String orderJson) throws JsonProcessingException {
        OrderInfo orderInfo = new ObjectMapper().readValue(orderJson,OrderInfo.class);
//        orderInfo.setManipUser(uName);
//        Date updTime = new Date(new java.util.Date().getTime());
//        orderInfo.setUpdTime(updTime);
        int count = orderDao.updOrder(orderInfo);
        if(count==0){
            throw new RuntimeException("提交工作记录失败！");
        }
    }
}
