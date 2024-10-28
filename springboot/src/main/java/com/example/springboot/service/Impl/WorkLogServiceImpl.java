package com.example.springboot.service.Impl;

import com.example.springboot.entity.WorkLog;
import com.example.springboot.mapper.WorkLogMapper;
import com.example.springboot.response.FileUploadResponse;
import com.example.springboot.service.WorkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class WorkLogServiceImpl implements WorkLogService {
    @Autowired
    private WorkLogMapper workLogMapper;
    @Override
    public FileUploadResponse saveFile(MultipartFile file) {
        try {
            // 文件上传目录
            String uploadDir = "D:/gitTestPkg/code/springboot/uploads/logFiles";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            /* 计算文件内容的 MD5 哈希值，用于生成文件名 */
            // 提取扩展名
            String fileExtension = "";
            String originalFileName = file.getOriginalFilename();
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            // 获取文件内容的 MD5 哈希值
            String fileMd5;
            try (InputStream inputStream = file.getInputStream()) {
                fileMd5 = DigestUtils.md5DigestAsHex(inputStream);
            }

            // 使用 MD5 作为文件名，保留文件扩展名
            String newFileName = fileMd5 + fileExtension;
            String filePath = uploadDir + "/" + newFileName;
            file.transferTo(new File(filePath));

            // 生成可访问的 URL
            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/logFiles/")
                    .path(newFileName)
                    .toUriString();

            System.out.println("文件已上传到：" + fileUrl);

            // 获取原始文件名并处理中文字符
            if (originalFileName == null) {
                throw new RuntimeException("文件名不能为空");
            }
            //System.out.println("originalFileName:"+originalFileName);
            //String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.toString());
            //System.out.println("encodedFileName"+encodedFileName);
            // 返回文件路径和文件名
            return new FileUploadResponse(fileUrl, originalFileName); // 返回资源路径和文件名

        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    public void saveWorkLog(String orderId, String workerId, String wordLog, String filePath,String fileName) {
        String logId = String.valueOf(System.currentTimeMillis()); // 利用时间戳生成唯一 ID
        System.out.println("logId:"+logId);
        WorkLog workLog = new WorkLog(logId,orderId,workerId,wordLog,filePath,fileName);
        workLogMapper.insertWorkLog(workLog);
    }
    @Override
    public List<WorkLog> getLogsByOrderId(String orderId) {
        return workLogMapper.findLogsByOrderId(orderId);
    }
    @Override
    public void deleteFile(String filePath) {
        try{
            /* 是否只有当前日志记录关联了此文件 */
            if(workLogMapper.fileRelated(filePath)==1){
                /* 转化为本地地址查询文件是否存在 */
                File file = new File(filePath.replace("http://localhost:8080/uploads/", "D:/gitTestPkg/code/springboot/uploads/"));
                if (file.exists() && file.isFile()) {
                    System.out.println("旧文件"+file.toString()+"已删除");
                    file.delete(); // 删除文件
                }
            }
            /* 除了当前日志记录还有其他记录关联该文件，不删除 */
            else {
                System.out.println("被关联文件存在其他关联，不予删除。");
            }
        }
        catch (Exception e){
            throw new RuntimeException("删除错误：",e);
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
