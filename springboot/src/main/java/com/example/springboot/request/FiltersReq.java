package com.example.springboot.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ys
 * @date： 2024/9/27 19:49
 */



@Data
public class FiltersReq extends PageReq{
    private List<String> equipId; // 设备ID
    private List<String> maintenanceTypeId; // 维修类型ID
    private List<Integer> status; // 状态
    private String searchQuery; // 搜索关键字（设备名称或描述）

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date startDate; // 开始日期

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date endDate; // 结束日期
}
