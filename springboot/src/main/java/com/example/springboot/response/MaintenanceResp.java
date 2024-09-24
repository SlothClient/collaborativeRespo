package com.example.springboot.response;

import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId
    private String planId;

    /**
     *
     */
    private String equipId;

    /**
     *
     */
    private String planName;

    /**
     *
     */
    private String maintanceDesc;

    /**
     *
     */
    private Date startTime;

    /**
     *
     */
    private Date endTime;

    /**
     *
     */
    private String maintanceType;

    /**
     *
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creator ;

    /**
     *
     */
    private Integer status;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新时间
     */
    private String updatePerson;
}
