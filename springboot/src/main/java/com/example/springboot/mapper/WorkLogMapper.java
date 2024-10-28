package com.example.springboot.mapper;

import com.example.springboot.entity.WorkLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WorkLogMapper {
    public void insertWorkLog(WorkLog workLog);
    public List<WorkLog> findLogsByOrderId(String orderId);
    public WorkLog findLogById(String logId);
    public void updateWorkLog(WorkLog workLog); // 直接传递 WorkLog 实体类
    public int fileRelated(String filePath);
}
