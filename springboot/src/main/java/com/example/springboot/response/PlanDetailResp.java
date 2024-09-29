package com.example.springboot.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author ys
 * @date： 2024/9/26 14:06
 */

@Data
public class PlanDetailResp {

    /**
     * 计划编号
     */
    private String planId;

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
    private String planName;

    /**
     * 计划开始时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date startTime;

    /**
     * 计划结束时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date endTime;

    /**
     * 当前计划状态
     */
    private Integer status;

    /**
     * 计划描述
     */
    private String maintanceDesc;

    /**
     * 计划类型
     */
    private String maintanceType;

    /**
     * 计划创建人
     */
    private String creator;

    /**
     * 计划创建时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createTime;

    /**
     * 计划更新时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
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
