package com.example.springboot.utils;

import com.example.springboot.mapper.EquipInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EquipIdInitializer implements CommandLineRunner {

    @Autowired
    private EquipInfoMapper equipInfoMapper;

    @Override
    public void run(String... args) throws Exception {
        // 从数据库中读取当前的 equipId
        String currentEquipId = equipInfoMapper.getCurrentId();
        // 初始化全局计数器
        EquipIdGenerator.initializeCounter(Integer.parseInt(currentEquipId.substring(3)));
    }
}