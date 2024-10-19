package com.example.springboot.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CheckDetailResp {
    /**
     * 计划编号
     */
    private String checkId;

    /**
     * 设备编号
     */
    private String equipId;

    /**
     * 设备名称
     */
    private String equipName;

    /**
     * 计划名称
     */
    private String checkName;

    /**
     * 计划开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;

    /**
     * 计划结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;

    /**
     * 当前计划状态
     */
    private Integer status;

    /**
     * 点巡检描述
     */
    private String checkDesc;

    /**
     * 点巡检位置
     */
    private String checkSite;
    /**
     * 点巡检内容
     */
    private String checkContent;
    /**
     * 计划创建人
     */
    private String creator;

    /**
     * 计划创建时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    /**
     * 计划更新时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    /**
     * 计划更新人
     */
    private String updatePerson;

    /**
     * 更新人意见
     */
    private String remark;
}
