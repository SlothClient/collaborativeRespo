package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;
import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID;

/**
 * 
 * @TableName maintance_info
 */
@TableName(value ="maintance_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintanceInfoDetail implements Serializable {
    /**
     * 
     */
    @TableId(type = ASSIGN_ID)
    private String planId;

    /**
     * 
     */
    private String equipId;

    /**
     * 
     */
    private String planName;

    /**
     * 
     */
    @JsonFormat(pattern = "yyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    private String maintanceDesc;

    /**
     * 
     */
    private String maintanceType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;
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
        MaintanceInfoDetail other = (MaintanceInfoDetail) that;
        return (this.getPlanId() == null ? other.getPlanId() == null : this.getPlanId().equals(other.getPlanId()))
            && (this.getEquipId() == null ? other.getEquipId() == null : this.getEquipId().equals(other.getEquipId()))
            && (this.getPlanName() == null ? other.getPlanName() == null : this.getPlanName().equals(other.getPlanName()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getMaintanceDesc() == null ? other.getMaintanceDesc() == null : this.getMaintanceDesc().equals(other.getMaintanceDesc()))
            && (this.getMaintanceType() == null ? other.getMaintanceType() == null : this.getMaintanceType().equals(other.getMaintanceType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPlanId() == null) ? 0 : getPlanId().hashCode());
        result = prime * result + ((getEquipId() == null) ? 0 : getEquipId().hashCode());
        result = prime * result + ((getPlanName() == null) ? 0 : getPlanName().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getMaintanceDesc() == null) ? 0 : getMaintanceDesc().hashCode());
        result = prime * result + ((getMaintanceType() == null) ? 0 : getMaintanceType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", planId=").append(planId);
        sb.append(", equipId=").append(equipId);
        sb.append(", planName=").append(planName);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", status=").append(status);
        sb.append(", maintanceDesc=").append(maintanceDesc);
        sb.append(", maintanceType=").append(maintanceType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}