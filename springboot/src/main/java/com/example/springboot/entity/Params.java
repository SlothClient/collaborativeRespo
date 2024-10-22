package com.example.springboot.entity;

import lombok.Data;

@Data
public class Params {
    private String equipId;
    private Integer id;
    private String deviceName;
    private String typeName;
    private String equipName;
    private Integer currentPage;
    private Integer pageSize;
}
