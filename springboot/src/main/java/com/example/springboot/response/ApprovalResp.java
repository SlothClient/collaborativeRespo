package com.example.springboot.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author ys
 * @date： 2024/9/26 19:34
 */

@Data
@Builder
public class ApprovalResp {

    /**
     * 计划Id
     */
    private String planId;

    /**
     * 计划名称
     */
    private String maintenanceName;

    /**
     * 当前计划状态
     */
    private Integer planStatus;

    /**
     * 当前登入者对审批状态
     */
    private Integer myStatus;

    /**
     * 设备名
     */
    private String equipName;


    /**
     * 计划类型
     */
    private String maintenanceType;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;
}
