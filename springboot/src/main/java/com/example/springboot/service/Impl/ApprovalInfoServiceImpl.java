package com.example.springboot.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.ApprovalInfo;
import com.example.springboot.service.ApprovalInfoService;
import com.example.springboot.mapper.ApprovalInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【approval_info】的数据库操作Service实现
* @createDate 2024-09-24 11:16:44
*/
@Service
public class ApprovalInfoServiceImpl extends ServiceImpl<ApprovalInfoMapper, ApprovalInfo>
    implements ApprovalInfoService{

}




