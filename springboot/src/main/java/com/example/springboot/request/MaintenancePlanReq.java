package com.example.springboot.request;

import com.example.springboot.utils.Page;

import java.util.Date;

/**
 * @author ys
 * @date： 2024/9/24 16:44
 */
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
    private Date startTime;

    /**
     * 结束日期
     */
    private Date endTime ;

}
