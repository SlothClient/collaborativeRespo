package com.example.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射 /avatars/ 路径到本地的 avatars 文件夹
        registry.addResourceHandler("/avatar/**")
                .addResourceLocations("file:E:/scientific_research_practice/code/uploadFile/avatar/");
    }
}