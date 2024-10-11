package com.example.springboot.controller;

import com.example.springboot.entity.EquipInfo;
import com.example.springboot.entity.OrderInfo;
import com.example.springboot.service.OrderService;
import com.example.springboot.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    /*结果类*/
    @Autowired
    ResultMap resultMap;

    @RequestMapping("/getOrdersByPage")
    @ResponseBody
    /**
     * 分页查询工单
     */
    public ResultMap getOrdersByPage(String conditionJson){
        return resultMap.handleRequest(conditionJson);
    }

    @RequestMapping("/getOrdersByCondition")
    @ResponseBody
    /**
     * 条件查询工单：状态、编号、时间范围
     */
    public ResultMap getOrdersByCondition(String conditionJson){
        try {
            List<OrderInfo> list = orderService.getOrdersByCondition(conditionJson);
            resultMap.setStatus(true);
            resultMap.setList(list);
            resultMap.setMsg("success");
        }
        catch (Exception e){
            resultMap.setStatus(false);
            resultMap.setList(new ArrayList<>());
            resultMap.setMsg(e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getSelectedEquipInfo")
    @ResponseBody
    public ResultMap getSelectedEquipInfo(String conditionJson){
        try {
            List<EquipInfo> list = orderService.getSelectedEquipInfo(conditionJson);
            resultMap.setStatus(true);
            resultMap.setList(list);
            resultMap.setMsg("success");
        }
        catch (Exception e){
            resultMap.setStatus(false);
            resultMap.setList(new ArrayList<>());
            resultMap.setMsg(e.getMessage());
        }
        return resultMap;
    }
}
