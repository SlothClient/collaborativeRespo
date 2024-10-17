package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.MenuInfo;
import com.example.springboot.service.MenuInfoService;
import com.example.springboot.mapper.MenuInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【menu_info】的数据库操作Service实现
* @createDate 2024-09-23 20:23:17
*/
@Service
public class MenuInfoServiceImpl extends ServiceImpl<MenuInfoMapper, MenuInfo>
    implements MenuInfoService{

}




