package com.example.springboot.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckInfo {
    /**
     *
     */
    private String checkId;


    /**
     *
     */
    private String checkName;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createTime;

    /**
     *
     */
    private String checkDesc;
    /**
     *
     */
    private String checkSite;
    /**
     *
     */
    private String checkContent;

    /**
     * 创建人
     */
    private String creator ;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date startTime;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date endTime;

    /**
     *
     */
    private String equipId;

    /**
     *
     */
    private Integer status;

    /**
     * 更新时间
     */
    private String updatePerson;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date updateTime;
}
