package com.example.springboot.service;

import com.example.springboot.entity.WorkLog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WorkLogService {
    public String saveFile(MultipartFile file);
    public void saveWorkLog(String orderId, String workerId, String wordLog, String filePath);
    public List<WorkLog> getLogsByOrderId(String orderId);
    public void updateWorkLog(WorkLog workLog); // 接受 WorkLog 实体类
    public void deleteFile(String filePath); // 新增删除文件接口
    public WorkLog getWorkLogById(String logId); // 新增获取日志信息接口


}
