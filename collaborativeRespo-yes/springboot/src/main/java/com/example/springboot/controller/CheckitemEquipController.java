package com.example.springboot.controller;


import com.example.springboot.entity.CheckitemEquip;
import com.example.springboot.service.CheckitemEquipService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-11-06
 */
@RestController
@RequestMapping("/checkitemEquip")
public class CheckitemEquipController {

    @Autowired
    private CheckitemEquipService checkitemEquipService;

    @GetMapping("/getcheckitem")
    public Result<List<CheckitemEquip>> getCheckitem(@RequestParam("equipId") String equipId) {
        return checkitemEquipService.getCheckitem(equipId);
    }
}
