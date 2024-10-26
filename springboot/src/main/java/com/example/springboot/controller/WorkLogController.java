package com.example.springboot.controller;

import com.example.springboot.entity.WorkLog;
import com.example.springboot.request.WorkLogRequest;
import com.example.springboot.service.WorkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@Controller
public class WorkLogController {

    @Autowired
    private WorkLogService workLogService;

    @PostMapping("/upload-log")
    public ResponseEntity<String> uploadLog(@ModelAttribute WorkLogRequest workLogRequest) {

        String filePath = workLogService.saveFile(workLogRequest.getFileLog()); // 保存文件
        workLogService.saveWorkLog(workLogRequest.getOrderId(), workLogRequest.getWorkerId(), workLogRequest.getWordLog(), filePath); // 保存工作日志

        return ResponseEntity.status(HttpStatus.CREATED).body("工作日志提交成功");
    }
    @GetMapping("work-log/{orderId}")
    public ResponseEntity<List<WorkLog>> getLogsByOrderId(@PathVariable String orderId) {
        List<WorkLog> logs = workLogService.getLogsByOrderId(orderId);
        return ResponseEntity.ok(logs);
    }
    @PostMapping("/update-log")
    public ResponseEntity<String> updateLog(@ModelAttribute WorkLogRequest workLogRequest) {
        // 获取当前日志信息
        WorkLog existingLog = workLogService.getWorkLogById(workLogRequest.getLogId());

        // 判断是否有新文件
        String filePath = existingLog.getLogAttachment();
        if (workLogRequest.getFileLog() != null && !workLogRequest.getFileLog().isEmpty()) {
            workLogService.deleteFile(existingLog.getLogAttachment());
            filePath = workLogService.saveFile(workLogRequest.getFileLog());
        }

        // 更新工作日志记录
        WorkLog updatedLog = new WorkLog(workLogRequest.getLogId(), workLogRequest.getOrderId(),
                workLogRequest.getWorkerId(), workLogRequest.getWordLog(), filePath);
        workLogService.updateWorkLog(updatedLog);

        return ResponseEntity.status(HttpStatus.OK).body("工作日志更新成功");
    }

}
