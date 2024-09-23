package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.RoleInfo;
import com.example.springboot.service.RoleInfoService;
import com.example.springboot.mapper.RoleInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【role_info】的数据库操作Service实现
* @createDate 2024-09-23 20:21:56
*/
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo>
    implements RoleInfoService{

}




