package com.example.springboot.entity;

import lombok.Data;

@Data
public class Params {
    private Integer id;
    private String deviceName;
    private String typeName;
    private Integer currentPage;
    private Integer pageSize;
}
