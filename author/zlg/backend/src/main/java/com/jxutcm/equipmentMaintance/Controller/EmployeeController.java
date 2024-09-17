package com.jxutcm.equipmentMaintance.Controller;

import com.jxutcm.equipmentMaintance.Bean.EmployeeInfo;
import com.jxutcm.equipmentMaintance.Service.EmployeeService;
import com.jxutcm.equipmentMaintance.Utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ResultMap resultMap;
    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping("/getEmployees")
    @ResponseBody
    public ResultMap getEmployees(){
        try {
            List<EmployeeInfo> list = employeeService.getEmployees();
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
}