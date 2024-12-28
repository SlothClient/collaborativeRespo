package com.example.springboot.controller;


import com.example.springboot.entity.EquipStatus;
import com.example.springboot.service.EquipStatusService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-10-12
 */
@RestController
@RequestMapping("/equipStatus")
public class EquipStatusController {
    @Autowired
    private EquipStatusService equipStatusService;

    @PostMapping("/getEquipStatus")
    public Result<List<EquipStatus>> getEquipStatus() {
        List<EquipStatus> list= equipStatusService.list();
        System.out.println(list);
        return Result.success(list);
    }
}
