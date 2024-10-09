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
    APPROVAL_CANCEL("已撤销", 4),
    /**
     *某个保养计划审核通过后，待开始
     */
    PLAN_PENDING("待开始",2),
    /**
     * 已派单
     */
    PLAN_DISPATCHED("已派单",6),
    /**
     * 进行中
     */
    PLAN_EXECUTE("执行中",7),
    /**
     * 计划完成
     */
    PLAN_FINISH("已完成",8);

    private final String msg;
    private final Integer code;
}
