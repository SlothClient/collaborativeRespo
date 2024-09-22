package com.example.springboot.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Device {
    private Integer id;
    private String deviceName;
    private String status;
    private Integer supplierId;
    private String supplierName;
    private Integer departmentId;
    private String departmentName;
    private Integer typeId;
    private String typeName;
    private String position;
    private String photo;
    private String notes;
    private Integer bigRepairsNumber;
    private Integer middleRepairsNumber;
    private Integer smallRepairsNumber;
    private Integer bigRepairsCycle;
    private Integer middleRepairsCycle;
    private Integer smallRepairsCycle;
    private Date lastMaintenanceTime;
    private Date purchasingDate;
    private Integer validPeriod;
}
