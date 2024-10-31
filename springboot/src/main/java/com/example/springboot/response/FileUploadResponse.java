package com.example.springboot.response;

public class FileUploadResponse {
    private String filePath; // 文件路径
    private String fileName; // 文件名

    public FileUploadResponse(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    // Getter 和 Setter
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
