package com.example.springboot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ys
 * @date： 2024/9/27 16:39
 */

@AllArgsConstructor
@Getter
public enum maintenanceCodeEnum {
    /**
     * 初始某一级审批状态
     */
    PENDING_APPROVAL("待审批", 0),
    /**
     * 整个保养计划状态
     */
    APPROVING("审批中", 1),
    /**
     * 某一级的状态或是整个计划状态
     */
    APPROVAL_PASS("已通过", 2),
    /**
     * 某一级的状态或是整个计划状态
     */
    APPROVAL_REJECT("已驳回", 3),
    /**
     * 某一级的状态或是整个计划状态
     */
    APPROVAL_CANCEL("已撤销", 4);

    private final String msg;
    private final Integer code;
}
