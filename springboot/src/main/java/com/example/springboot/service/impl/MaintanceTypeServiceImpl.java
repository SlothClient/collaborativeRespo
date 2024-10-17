package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.MaintanceType;
import com.example.springboot.service.MaintanceTypeService;
import com.example.springboot.mapper.MaintanceTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【maintance_type】的数据库操作Service实现
* @createDate 2024-09-25 19:09:25
*/
@Service
public class MaintanceTypeServiceImpl extends ServiceImpl<MaintanceTypeMapper, MaintanceType>
    implements MaintanceTypeService{

}




