package com.example.springboot.entity;

import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class SelectedEquipInfo {
    private String equipName;
    private String equipId;
    private Date lastMaintance;
    private String maintanceDesc;
    private String orderDesc;
    private String orderRecord;

    public SelectedEquipInfo() {
    }

    public SelectedEquipInfo(String equipName, String equipId, Date lastMaintance, String maintanceDesc, String orderDesc, String orderRecord) {
        this.equipName = equipName;
        this.equipId = equipId;
        this.lastMaintance = lastMaintance;
        this.maintanceDesc = maintanceDesc;
        this.orderDesc = orderDesc;
        this.orderRecord = orderRecord;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getEquipId() {
        return equipId;
    }

    public void setEquipId(String equipId) {
        this.equipId = equipId;
    }

    public Date getLastMaintance() {
        return lastMaintance;
    }

    public void setLastMaintance(Date lastMaintance) {
        this.lastMaintance = lastMaintance;
    }

    public String getMaintanceDesc() {
        return maintanceDesc;
    }

    public void setMaintanceDesc(String maintanceDesc) {
        this.maintanceDesc = maintanceDesc;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOrderRecord() {
        return orderRecord;
    }

    public void setOrderRecord(String orderRecord) {
        this.orderRecord = orderRecord;
    }
}
