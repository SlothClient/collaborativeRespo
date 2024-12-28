package com.example.springboot.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CheckInfoReq extends PageReq{
    /**
     * 计划状态
     */
    private List<Integer> status;

    /**
     * 计划名称
     */
    private String checkName;

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

    private String equipId;


    private String creator;

}
