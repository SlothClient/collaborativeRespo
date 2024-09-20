package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Device;
import com.example.springboot.entity.Params;
import com.example.springboot.service.DeviceService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private static final Logger log = LoggerFactory.getLogger(DeviceController.class);
    @Autowired
    private DeviceService deviceService;

    @GetMapping("/getAll")
    public Result findByAll() {
        List<Device> list = deviceService.getAll();
        return Result.success(list);
    }
//     条件查询
    @GetMapping("/findBySearch")
    public Result findBySearch(Params params) {
        PageInfo<Device> list = deviceService.search(params);
        return Result.success(list);
    }

    @PostMapping("/add")
    @Operation(summary = "添加设备")
    public Result add(@RequestBody Device device) {
        deviceService.add(device);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "修改设备")
    public Result update(@RequestBody Device device) {
        deviceService.update(device);
        return Result.success();
    }
    @GetMapping("/delete")
    @Operation(summary = "删除设备")
    public Result delete(@RequestParam Integer id) {
        System.out.println(id);
        deviceService.delete(id);
        return Result.success();
    }
}
