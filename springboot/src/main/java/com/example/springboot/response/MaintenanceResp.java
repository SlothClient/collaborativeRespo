package com.example.springboot.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author ys
 * @date： 2024/9/24 10:31
 */

@Data
@Builder
public class MaintenanceResp {

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
    private Date startTime;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
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
    private Date updateTime;
}
