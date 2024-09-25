package com.example.springboot.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author ys
 * @date： 2024/9/25 16:24
 */

@Data
@Builder
public class EquipmentResp {

    /**
     * 设备Id
     */
    private String equipId;

    /**
     *设备名
     */
    private String equipName;
}
