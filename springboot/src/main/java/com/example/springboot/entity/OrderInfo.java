package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @TableName order_info
 */
@TableName(value ="order_info")
@Data
@Builder
public class OrderInfo implements Serializable {
    /**
     * 
     */
    @TableId
    private String orderId;

    /**
     * 
     */
    private String equipId;

    /**
     * 
     */
    private String planId;

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
    private String workerId;

    /**
     * 
     */
    private String orderDesc;

    /**
     * 
     */
    private String orderRecord;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OrderInfo other = (OrderInfo) that;
        return (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getEquipId() == null ? other.getEquipId() == null : this.getEquipId().equals(other.getEquipId()))
            && (this.getPlanId() == null ? other.getPlanId() == null : this.getPlanId().equals(other.getPlanId()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getWorkerId() == null ? other.getWorkerId() == null : this.getWorkerId().equals(other.getWorkerId()))
            && (this.getOrderDesc() == null ? other.getOrderDesc() == null : this.getOrderDesc().equals(other.getOrderDesc()))
            && (this.getOrderRecord() == null ? other.getOrderRecord() == null : this.getOrderRecord().equals(other.getOrderRecord()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getEquipId() == null) ? 0 : getEquipId().hashCode());
        result = prime * result + ((getPlanId() == null) ? 0 : getPlanId().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getWorkerId() == null) ? 0 : getWorkerId().hashCode());
        result = prime * result + ((getOrderDesc() == null) ? 0 : getOrderDesc().hashCode());
        result = prime * result + ((getOrderRecord() == null) ? 0 : getOrderRecord().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", equipId=").append(equipId);
        sb.append(", planId=").append(planId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", workerId=").append(workerId);
        sb.append(", orderDesc=").append(orderDesc);
        sb.append(", orderRecord=").append(orderRecord);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}