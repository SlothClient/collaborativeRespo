package com.example.springboot.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;


/**
 * 
 * @TableName approval_info
 */
@TableName(value ="approval_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = ASSIGN_ID)
    private String approvalId;

    /**
     * 
     */
    private String planId;

    /**
     * 
     */
    private String fatherId;

    /**
     * 
     */
    private String applicantId;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private Date manipTime;

    /**
     * 
     */
    private Integer approvalStatus;

    /**
     * 
     */
    private Integer stepOrder;

    /**
     * 
     */
    private String approvalRemark;

    private Integer isDeleted;

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
        ApprovalInfo other = (ApprovalInfo) that;
        return (this.getApprovalId() == null ? other.getApprovalId() == null : this.getApprovalId().equals(other.getApprovalId()))
            && (this.getPlanId() == null ? other.getPlanId() == null : this.getPlanId().equals(other.getPlanId()))
            && (this.getFatherId() == null ? other.getFatherId() == null : this.getFatherId().equals(other.getFatherId()))
            && (this.getApplicantId() == null ? other.getApplicantId() == null : this.getApplicantId().equals(other.getApplicantId()))
            && (this.getManipTime() == null ? other.getManipTime() == null : this.getManipTime().equals(other.getManipTime()))
            && (this.getApprovalStatus() == null ? other.getApprovalStatus() == null : this.getApprovalStatus().equals(other.getApprovalStatus()))
            && (this.getStepOrder() == null ? other.getStepOrder() == null : this.getStepOrder().equals(other.getStepOrder()))
            && (this.getApprovalRemark() == null ? other.getApprovalRemark() == null : this.getApprovalRemark().equals(other.getApprovalRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getApprovalId() == null) ? 0 : getApprovalId().hashCode());
        result = prime * result + ((getPlanId() == null) ? 0 : getPlanId().hashCode());
        result = prime * result + ((getFatherId() == null) ? 0 : getFatherId().hashCode());
        result = prime * result + ((getApplicantId() == null) ? 0 : getApplicantId().hashCode());
        result = prime * result + ((getManipTime() == null) ? 0 : getManipTime().hashCode());
        result = prime * result + ((getApprovalStatus() == null) ? 0 : getApprovalStatus().hashCode());
        result = prime * result + ((getStepOrder() == null) ? 0 : getStepOrder().hashCode());
        result = prime * result + ((getApprovalRemark() == null) ? 0 : getApprovalRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", approvalId=").append(approvalId);
        sb.append(", planId=").append(planId);
        sb.append(", fatherId=").append(fatherId);
        sb.append(", applicantId=").append(applicantId);
        sb.append(", manipTime=").append(manipTime);
        sb.append(", approvalStatus=").append(approvalStatus);
        sb.append(", stepOrder=").append(stepOrder);
        sb.append(", approvalRemark=").append(approvalRemark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}