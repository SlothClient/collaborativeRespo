package com.example.springboot.request;

import lombok.Data;

@Data
public class EquipInfoReq extends PageReq{
    private String equipId;
    private String equipName;
    private String departmentName;
    private String typeName;

}
