package com.example.springboot.utils;

import com.example.springboot.entity.OrderInfo;
import com.example.springboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("resultMap")
public class ResultMap<T> {
    private String msg;
    private Boolean Status;
    private int totalCount;
    private List<T> list = new ArrayList<T>();

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Autowired
    OrderService orderService;
    /**
     * 处理查询筛选请求
     */
    public ResultMap handleRequest(String conditionJson) {
        ResultMap resultMap = new ResultMap(); // 确保每次都新建一个 ResultMap 实例

        try {
            List<OrderInfo> list = orderService.getOrdersByPage(conditionJson);
            resultMap.setStatus(true);
            resultMap.setList(list);

            int totalCount = orderService.getOrdersCountByCondition(conditionJson);
            resultMap.setTotalCount(totalCount);
            resultMap.setMsg("success");
        } catch (Exception e) {
            // 错误返回可单独进行封装
            resultMap.setStatus(false);
            resultMap.setList(new ArrayList<>());
            resultMap.setTotalCount(0);
            resultMap.setMsg(e.getMessage());
        }

        return resultMap;
    }
}
