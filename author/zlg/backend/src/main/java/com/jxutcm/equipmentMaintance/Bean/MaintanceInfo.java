package com.jxutcm.equipmentMaintance.Bean;


import java.util.Date;

public class MaintanceInfo {
    private String 计划编号;
    private String 设备编号;
    private String 计划名称;
    private String 计划描述;
    private String 保养类型;
    private Date 开始时间;
    private Date 结束时间;
    private String 保养内容;

    public String get计划编号() {
        return 计划编号;
    }

    public void set计划编号(String 计划编号) {
        this.计划编号 = 计划编号;
    }

    public String get设备编号() {
        return 设备编号;
    }

    public void set设备编号(String 设备编号) {
        this.设备编号 = 设备编号;
    }

    public String get计划名称() {
        return 计划名称;
    }

    public void set计划名称(String 计划名称) {
        this.计划名称 = 计划名称;
    }

    public String get计划描述() {
        return 计划描述;
    }

    public void set计划描述(String 计划描述) {
        this.计划描述 = 计划描述;
    }

    public String get保养类型() {
        return 保养类型;
    }

    public void set保养类型(String 保养类型) {
        this.保养类型 = 保养类型;
    }

    public Date get开始时间() {
        return 开始时间;
    }

    public void set开始时间(Date 开始时间) {
        this.开始时间 = 开始时间;
    }

    public Date get结束时间() {
        return 结束时间;
    }

    public void set结束时间(Date 结束时间) {
        this.结束时间 = 结束时间;
    }

    public String get保养内容() {
        return 保养内容;
    }

    public void set保养内容(String 保养内容) {
        this.保养内容 = 保养内容;
    }
}
