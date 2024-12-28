package com.example.springboot.controller;


import com.example.springboot.entity.Type;
import com.example.springboot.service.TypeService;
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
 * @since 2024-10-15
 */
@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @PostMapping("getType")
    public Result<List<Type>>getType(){
        List<Type> list = typeService.list();
        System.out.println(list);
        return Result.success(list);
    }

}
