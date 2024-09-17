package com.jxutcm.equipmentMaintance.Controller;

import com.jxutcm.equipmentMaintance.Bean.MaintanceInfo;
import com.jxutcm.equipmentMaintance.Bean.User;
import com.jxutcm.equipmentMaintance.Service.MaintanceService;
import com.jxutcm.equipmentMaintance.Utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MaintanceController {
    @Autowired
    private MaintanceService maintanceService;
    @Autowired
    private ResultMap resultMap;
    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping("/getMaintance")
    @ResponseBody
    public ResultMap getMaintance(String conditionJson){
        try {
            List<MaintanceInfo> list = maintanceService.getMaintance(conditionJson);
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
    @RequestMapping("/addMaintanceInfo")
    @ResponseBody
    public ResultMap addMaintanceInfo(String maintanceInfoJson, HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            maintanceService.addMaintanceInfo(maintanceInfoJson,user.getUname());
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
//    @RequestMapping("/delDocInfo") // 匹配请求
//    @ResponseBody // 将返回值转化为json格式响应给前端
//    public ResultMap delDocInfo(String dID, HttpSession session){
//        try {
//            User user = (User) session.getAttribute("user");
//            docService.delDocInfo(dID,user.getuName());
//            resultMap.setStatus(true);
//        }
//        catch (Exception e){
//            resultMap.setStatus(false);
//            resultMap.setMsg(e.getMessage());
//        }
//        return resultMap;
//    }
//
//    @RequestMapping("/updDocInfo") // 匹配请求
//    @ResponseBody // 将返回值转化为json格式响应给前端
//    public ResultMap updDocInfo(String docInfoJson, HttpSession session){
//        try {
//            User user = (User) session.getAttribute("user");
//            docService.updDocInfo(docInfoJson,user.getuName());
//            resultMap.setStatus(true);
//        }
//        catch (Exception e){
//            resultMap.setStatus(false);
//            resultMap.setMsg(e.getMessage());
//        }
//        return resultMap;
//    }
}
