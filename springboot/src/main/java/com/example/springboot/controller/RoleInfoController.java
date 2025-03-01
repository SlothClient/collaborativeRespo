package com.example.springboot.controller;


import com.example.springboot.entity.RoleInfo;
import com.example.springboot.entity.Type;
import com.example.springboot.service.RoleInfoService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-10-17
 */
@RestController
@RequestMapping("/role")
public class RoleInfoController {
    @Autowired
    private RoleInfoService roleInfoService;

    @PostMapping("getRole")
    public Result<List<RoleInfo>> getRole(){
        List<RoleInfo> list = roleInfoService.list();
        System.out.println(list);
        return Result.success(list);
    }
}
