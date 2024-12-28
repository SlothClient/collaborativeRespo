package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.example.springboot.utils.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";  //文件存储目录

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename(); //文件原始名称
        String mainName = FileUtil.mainName(originalFilename);
        String extName = FileUtil.extName("文件的后缀");
//        String projectPath = System.getProperty("user.dir");//文件根目录
//        String filesPath = projectPath + "\\files"; //文件存储目录
//        File parentFile = new File(ROOT_PATH);
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);//如果当前文件的父级目录不存在，就创建
        }
        if (FileUtil.exist(ROOT_PATH + File.separator+ originalFilename)) {//如果当前上传的文件已经存在了，那么这个时候我就要重命名
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;

        }
        File saveFile = new File(ROOT_PATH +File.separator + originalFilename);
        file.transferTo(saveFile);//存储文件到本地磁盘里面去
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        return Result.success(url);
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName , HttpServletResponse response) throws IOException {

      String filePath =  ROOT_PATH + File.separator + fileName;
      if(!FileUtil.exist(filePath)){
          return;
      }
      byte[] bytes = FileUtil.readBytes(filePath);
      ServletOutputStream outputStream = response.getOutputStream();
      outputStream.write(bytes);
      outputStream.flush();
      outputStream.close();
    }

}
