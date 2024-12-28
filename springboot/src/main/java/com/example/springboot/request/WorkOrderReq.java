package com.example.springboot.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ys
 * @date： 2024/10/7 20:33
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkOrderReq {

    private String planId;

    private String checkId;

    private String equipId;

    private String workerId;

    private String orderDesc;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private Date deliverTime;
}
