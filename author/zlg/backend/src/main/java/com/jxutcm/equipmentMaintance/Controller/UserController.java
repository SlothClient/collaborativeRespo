package com.jxutcm.equipmentMaintance.Controller;

import com.jxutcm.equipmentMaintance.Bean.User;
import com.jxutcm.equipmentMaintance.Service.UserService;
import com.jxutcm.equipmentMaintance.Utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ResultMap resultMap;
    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping("/login")
    @ResponseBody
    public ResultMap doLogin(String uname, String upwd, HttpSession session){
        try {
            User user = userService.doLogin(uname,upwd);
            session.setAttribute("user",user);
            resultMap.setStatus(true);
            resultMap.setMsg("success");
        }
        catch (Exception e){
            resultMap.setStatus(false);
            resultMap.setMsg(e.getMessage());
        }
        return resultMap;
    }
}
