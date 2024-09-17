package com.jxutcm.equipmentMaintance.Controller;

import com.jxutcm.equipmentMaintance.Bean.OrderInfo;
import com.jxutcm.equipmentMaintance.Bean.User;
import com.jxutcm.equipmentMaintance.Service.OrderService;
import com.jxutcm.equipmentMaintance.Utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ResultMap resultMap;
    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping("/getOrder")
    @ResponseBody
    public ResultMap getOrder(String conditionJson){
        try {
            List<OrderInfo> list = orderService.getOrder(conditionJson);
            resultMap.setStatus(true);
            resultMap.setList(list);
            resultMap.setMsg("success");
        }
        catch (Exception e){
            resultMap.setStatus(false);
            resultMap.setMsg(e.getMessage());
        }
        return resultMap;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping("/addOrder")
    @ResponseBody
    public ResultMap addOrder(String orderJson, HttpSession session){
        try {
//            User user = (User) session.getAttribute("user");
//            orderService.addOrder(orderJson,user.getUname());
            orderService.addOrder(orderJson);
            resultMap.setStatus(true);
            resultMap.setMsg("success");
        }
        catch (Exception e){
            resultMap.setStatus(false);
            resultMap.setMsg(e.getMessage());
        }
        return resultMap;
    }
//
//    @RequestMapping("/delOrder") // 匹配请求
//    @ResponseBody // 将返回值转化为json格式响应给前端
//    public ResultMap delOrder(String dID, HttpSession session){
//        try {
//            User user = (User) session.getAttribute("user");
//            docService.delOrder(dID,user.getuName());
//            resultMap.setStatus(true);
//        }
//        catch (Exception e){
//            resultMap.setStatus(false);
//            resultMap.setMsg(e.getMessage());
//        }
//        return resultMap;
//    }
//
    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping("/updOrder") // 匹配请求
    @ResponseBody // 将返回值转化为json格式响应给前端
    public ResultMap updOrder(String orderJson, HttpSession session){
        try {
//            User user = (User) session.getAttribute("user");
//            orderService.updOrder(orderJson,user.getUname());
            orderService.updOrder(orderJson);
            resultMap.setStatus(true);
        }
        catch (Exception e){
            resultMap.setStatus(false);
            resultMap.setMsg(e.getMessage());
        }
        return resultMap;
    }
}
