package com.example.springboot.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.ChatRecord;
import com.example.springboot.service.ChatRecordService;
import com.example.springboot.mapper.ChatRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【chart_record】的数据库操作Service实现
* @createDate 2024-10-20 21:29:09
*/
@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordMapper, ChatRecord>
    implements ChatRecordService {

}




