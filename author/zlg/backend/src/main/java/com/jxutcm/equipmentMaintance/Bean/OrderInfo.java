package com.jxutcm.equipmentMaintance.Bean;

import java.sql.Date;

public class OrderInfo {
    private String 工单编号;
    private String 设备编号;
    private Date 派单时间;
    private Date 结束时间;
    private String 标准工时;
    private String 负责人编号;
    private String 工单备注;
    private String 工作记录;

    public String get工单编号() {
        return 工单编号;
    }

    public void set工单编号(String 工单编号) {
        this.工单编号 = 工单编号;
    }

    public String get设备编号() {
        return 设备编号;
    }

    public void set设备编号(String 设备编号) {
        this.设备编号 = 设备编号;
    }

    public Date get派单时间() {
        return 派单时间;
    }

    public void set派单时间(Date 派单时间) {
        this.派单时间 = 派单时间;
    }

    public Date get结束时间() {
        return 结束时间;
    }

    public void set结束时间(Date 结束时间) {
        this.结束时间 = 结束时间;
    }

    public String get标准工时() {
        return 标准工时;
    }

    public void set标准工时(String 标准工时) {
        this.标准工时 = 标准工时;
    }

    public String get负责人编号() {
        return 负责人编号;
    }

    public void set负责人编号(String 负责人编号) {
        this.负责人编号 = 负责人编号;
    }

    public String get工单备注() {
        return 工单备注;
    }

    public void set工单备注(String 工单备注) {
        this.工单备注 = 工单备注;
    }

    public String get工作记录() {
        return 工作记录;
    }

    public void set工作记录(String 工作记录) {
        this.工作记录 = 工作记录;
    }
}
