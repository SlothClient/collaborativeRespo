package com.example.springboot.service;

import com.example.springboot.entity.WorkLog;
import com.example.springboot.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WorkLogService {
    public FileUploadResponse saveFile(MultipartFile file);
    public void saveWorkLog(String orderId, String workerId, String wordLog, String filePath, String fileName);
    public List<WorkLog> getLogsByOrderId(String orderId);
    public void updateWorkLog(WorkLog workLog); // 接受 WorkLog 实体类
    public void deleteFile(String filePath); // 新增删除文件接口
    public WorkLog getWorkLogById(String logId); // 新增获取日志信息接口


}
