package com.example.springboot.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ys
 * @date： 2024/9/25 18:35
 */

@Data
@Builder
public class MaintenanceInfoResp {

    /**
     * 数据
     */
    private  List<MaintenanceInfo> maintenanceInfoList;

    /**
     * 记录数
     */
    private Integer total;
}
