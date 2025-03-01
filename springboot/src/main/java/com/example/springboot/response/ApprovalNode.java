package com.example.springboot.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ys
 * @date： 2024/9/28 16:14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalNode {

    /**
     * 该节点用户
     */
    private String username;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private Date actionTime;

    /**
     * 审核意见
     */
    private String remark;

    /**
     * 顺序
     */
    private Integer stepOrder;


    /**
     * 该节点状态
     */
    private Integer nodeStatus;
}
