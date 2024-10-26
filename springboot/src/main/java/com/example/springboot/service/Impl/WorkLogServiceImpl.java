package com.example.springboot.service.Impl;

import com.example.springboot.entity.WorkLog;
import com.example.springboot.mapper.WorkLogMapper;
import com.example.springboot.service.WorkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class WorkLogServiceImpl implements WorkLogService {
    @Autowired
    private WorkLogMapper workLogMapper;
    @Override
    public String saveFile(MultipartFile file) {
        try {
            // 文件上传目录
            String uploadDir = "D:/gitTestPkg/code/springboot/uploads";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 保存文件
            String filePath = uploadDir + "/" + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            // 生成可访问的URL
            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(file.getOriginalFilename())
                    .toUriString();

            System.out.println("文件已上传到：" + fileUrl);
            return fileUrl;  // 返回文件的URL，而不是本地路径

        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    public void saveWorkLog(String orderId, String workerId, String wordLog, String filePath) {
        String logId = String.valueOf(System.currentTimeMillis()); // 利用时间戳生成唯一 ID
        System.out.println("logId:"+logId);
        WorkLog workLog = new WorkLog(logId,orderId,workerId,wordLog,filePath);
        workLogMapper.insertWorkLog(workLog);
    }
    @Override
    public List<WorkLog> getLogsByOrderId(String orderId) {
        return workLogMapper.findLogsByOrderId(orderId);
    }
    @Override
    public void deleteFile(String filePath) {
        File file = new File(filePath.replace("/uploads/", "D:/gitTestPkg/code/springboot/uploads/"));
        if (file.exists() && file.isFile()) {
            file.delete(); // 删除文件
        }
    }

    @Override
    public WorkLog getWorkLogById(String logId) {
        return workLogMapper.findLogById(logId); // 假设通过logId查询
    }

    @Override
    public void updateWorkLog(WorkLog workLog) {
        workLogMapper.updateWorkLog(workLog);
    }
}
