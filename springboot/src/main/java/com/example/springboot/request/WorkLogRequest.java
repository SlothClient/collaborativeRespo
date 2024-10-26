package com.example.springboot.request;

import org.springframework.web.multipart.MultipartFile;

public class WorkLogRequest {
    private String logId;
    private String orderId;
    private String workerId;
    private String wordLog;
    private MultipartFile fileLog;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getWordLog() {
        return wordLog;
    }

    public void setWordLog(String wordLog) {
        this.wordLog = wordLog;
    }

    public MultipartFile getFileLog() {
        return fileLog;
    }

    public void setFileLog(MultipartFile fileLog) {
        this.fileLog = fileLog;
    }
}
