package com.example.springboot.controller;


import com.example.springboot.entity.Department;
import com.example.springboot.service.DepartmentService;
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
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/getDepartment")
    public Result<List<Department>> getDepartment() {
        List<Department> list =departmentService.list();
        System.out.println(list);
        return Result.success(list);
    }
}
