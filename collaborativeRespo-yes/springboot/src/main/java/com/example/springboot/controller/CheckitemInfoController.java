package com.example.springboot.controller;


import com.example.springboot.entity.CheckitemInfo;
import com.example.springboot.service.CheckitemInfoService;
import com.example.springboot.utils.Result;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-10-27
 */
@RestController
@RequestMapping("/checkitem")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckitemInfoController {

    @Autowired
    private CheckitemInfoService checkitemInfoService;

    @GetMapping("/getCheckitem")
    public Result<List<CheckitemInfo>> getCheckitem(@RequestParam("checkId") String checkId) {
        System.out.println(checkId);
        return checkitemInfoService.getCheckitem(checkId);
    }
    @PostMapping("/create")
    public Result<Integer> createCheckitem(@RequestBody List<CheckitemInfo> checkitemInfos) {
        return checkitemInfoService.createCheckitem(checkitemInfos);
    }

    @PostMapping("/update")
    public Result<Integer> updateCheckitem(@RequestBody List<CheckitemInfo> checkitemInfos) {
        return checkitemInfoService.updateCheckitem(checkitemInfos);
    }

    @PostMapping("/delete")
    public Result<Integer> deleteCheckitem(@RequestBody List<String> checkitemIds) {
        return checkitemInfoService.deleteCheckitem(checkitemIds);
    }
}
