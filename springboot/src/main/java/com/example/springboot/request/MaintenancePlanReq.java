package com.example.springboot.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ys
 * @date： 2024/9/24 16:44
 */

@Data
public class MaintenancePlanReq extends PageReq {

    /**
     * 计划状态
     */
    private List<Integer> status;

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
