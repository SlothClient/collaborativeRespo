package com.example.springboot.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author ys
 * @date： 2024/10/6 22:28
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvancedSearchReq extends PageReq {

    private String planName;

    private String creator;

    private String maintenanceTypeId; // 维修类型ID

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;

    private String equipId;

   private List<Integer> status; // 状态
}
