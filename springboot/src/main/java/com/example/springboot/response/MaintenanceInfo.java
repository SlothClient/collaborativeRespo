package com.example.springboot.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author ys
 * @date： 2024/9/24 10:31
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintenanceInfo {

    /**
     *
     */
    private String planId;

    /**
     *
     */
    private String maintanceType;


    /**
     *
     */
    private String planName;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createTime;

    /**
     *
     */
    private String maintanceDesc;

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
