package com.example.springboot.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author ys
 * @date： 2024/9/28 15:44
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDetailResp {

    private List<ApprovalNode> approvalInfoList;

    private String maintenanceDesc;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date endTime;

    private Integer planStatus;

    /**
     * 设备名
     */
    private String equipName;

}
