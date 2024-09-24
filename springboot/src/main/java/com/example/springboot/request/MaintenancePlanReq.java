package com.example.springboot.request;

import com.example.springboot.utils.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author ys
 * @date： 2024/9/24 16:44
 */

@Data
public class MaintenancePlanReq extends Page {

    /**
     * 计划状态
     */
    private Integer status;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date endTime ;

}
