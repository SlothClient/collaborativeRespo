package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName worker_info
 */
@TableName(value ="worker_info")
@Data
public class WorkerInfo implements Serializable {
    /**
     * 
     */
    @TableId
    private String workerId;

    /**
     * 
     */
    private String workerName;

    /**
     * 
     */
    private String dept;

    /**
     * 
     */
    private BigDecimal payHour;

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
        WorkerInfo other = (WorkerInfo) that;
        return (this.getWorkerId() == null ? other.getWorkerId() == null : this.getWorkerId().equals(other.getWorkerId()))
            && (this.getWorkerName() == null ? other.getWorkerName() == null : this.getWorkerName().equals(other.getWorkerName()))
            && (this.getDept() == null ? other.getDept() == null : this.getDept().equals(other.getDept()))
            && (this.getPayHour() == null ? other.getPayHour() == null : this.getPayHour().equals(other.getPayHour()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWorkerId() == null) ? 0 : getWorkerId().hashCode());
        result = prime * result + ((getWorkerName() == null) ? 0 : getWorkerName().hashCode());
        result = prime * result + ((getDept() == null) ? 0 : getDept().hashCode());
        result = prime * result + ((getPayHour() == null) ? 0 : getPayHour().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", workerId=").append(workerId);
        sb.append(", workerName=").append(workerName);
        sb.append(", dept=").append(dept);
        sb.append(", payHour=").append(payHour);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}