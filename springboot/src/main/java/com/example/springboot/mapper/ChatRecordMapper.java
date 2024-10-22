package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author Lenovo
* @description 针对表【chart_record】的数据库操作Mapper
* @createDate 2024-10-20 21:21:10
* @Entity com.example.springboot.entity.ChartRecord
*/

@Mapper
@Repository
public interface ChatRecordMapper extends BaseMapper<ChatRecord> {

}




