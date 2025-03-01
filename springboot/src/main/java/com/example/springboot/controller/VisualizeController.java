package com.example.springboot.controller;

import com.example.springboot.entity.VisualInfo;
import com.example.springboot.service.VisualizeService;
import com.example.springboot.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VisualizeController {
    @Autowired
    VisualizeService visualizeService;
    @Autowired
    ResultMap resultMap;

    @PostMapping("/getBigNums")
    @ResponseBody
    public ResultMap getBigNums(){
        try{
            List<VisualInfo> list = visualizeService.getBigNums();
            resultMap.setStatus(true);
            resultMap.setList(list);
            resultMap.setMsg("success");
        }catch (Exception e){
            resultMap.setStatus(false);
            resultMap.setList(new ArrayList<>());
            resultMap.setMsg(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/getEquipPlans")
    @ResponseBody
    public ResultMap getEquipPlans(){
        try{
            List<VisualInfo> list = visualizeService.getEquipPlans();
            resultMap.setStatus(true);
            resultMap.setList(list);
            resultMap.setMsg("success");
        }catch (Exception e){
            resultMap.setStatus(false);
            resultMap.setList(new ArrayList<>());
            resultMap.setMsg(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/getPlanTypes")
    @ResponseBody
    public ResultMap getPlanTypes(){
        try{
            List<VisualInfo> list = visualizeService.getPlanTypes();
            resultMap.setStatus(true);
            resultMap.setList(list);
            resultMap.setMsg("success");
        }catch (Exception e){
            resultMap.setStatus(false);
            resultMap.setList(new ArrayList<>());
            resultMap.setMsg(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/getEquipType")
    @ResponseBody
    public ResultMap getEquipType(){
        try{
            List<VisualInfo> list = visualizeService.getEquipType();
            resultMap.setStatus(true);
            resultMap.setList(list);
            resultMap.setMsg("success");
        }catch (Exception e){
            resultMap.setStatus(false);
            resultMap.setList(new ArrayList<>());
            resultMap.setMsg(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/getWorkerRank")
    @ResponseBody
    public ResultMap getWorkerRank(){
        try{
            List<VisualInfo> list = visualizeService.getWorkerRank();
            resultMap.setStatus(true);
            resultMap.setList(list);
            resultMap.setMsg("success");
        }catch (Exception e){
            resultMap.setStatus(false);
            resultMap.setList(new ArrayList<>());
            resultMap.setMsg(e.getMessage());
        }
        return resultMap;
    }
}
