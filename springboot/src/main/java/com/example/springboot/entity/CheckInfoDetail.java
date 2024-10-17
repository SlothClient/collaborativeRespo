package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2024-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Accessors(chain = true)
@TableName("check_info")
@AllArgsConstructor
@NoArgsConstructor
public class CheckInfoDetail implements Serializable {



    @TableId(type = ASSIGN_ID)
    private String checkId;

    private String equipId;

    private String checkName;

    @JsonFormat(pattern = "yyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;

    private String checkDesc;

    private String checkSite;

    private String checkContent;

    private Integer status;

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
       CheckInfoDetail other = (CheckInfoDetail) that;
       return (this.getCheckId() == null ? other.getCheckId() == null : this.getCheckId().equals(other.getCheckId()))
               &&(getEquipId() == null ? other.getEquipId() == null : this.getEquipId().equals(other.getEquipId()))
               &&(getCheckName() == null ? other.getCheckName() == null : this.getCheckName().equals(other.getCheckName()))
               &&(getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
               &&(getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
               &&(getCheckDesc() == null ? other.getCheckDesc() == null : this.getCheckDesc().equals(other.getCheckDesc()))
               &&(getCheckSite() == null ? other.getCheckSite() == null : this.getCheckSite().equals(other.getCheckSite()))
               &&(getCheckContent() == null ? other.getCheckContent() == null : this.getCheckContent().equals(other.getCheckContent()))
               &&(getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));

   }
   @Override
    public int hashCode() {
       final int prime = 31;
       int result = 1;
       result = prime * result + ((getCheckId() == null) ? 0 : getCheckId().hashCode());
       result = prime * result + ((getEquipId() == null) ? 0 : getEquipId().hashCode());
       result = prime * result + ((getCheckName() == null) ? 0 : getCheckName().hashCode());
       result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
       result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
       result = prime * result + ((getCheckDesc() == null) ? 0 : getCheckDesc().hashCode());
       result = prime * result + ((getCheckSite() == null) ? 0 : getCheckSite().hashCode());
       result = prime * result + ((getCheckContent() == null) ? 0 : getCheckContent().hashCode());
       result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
       return result;
   }
   @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append(getClass().getSimpleName());
       sb.append(" [");
       sb.append("Hash = ").append(hashCode());
       sb.append(", checkId=").append(checkId);
       sb.append(", equipId=").append(equipId);
       sb.append(", checkName=").append(checkName);
       sb.append(", startTime=").append(startTime);
       sb.append(", endTime=").append(endTime);
       sb.append(", checkDesc=").append(checkDesc);
       sb.append(", checkSite=").append(checkSite);
       sb.append(", checkContent=").append(checkContent);
       sb.append(", status=").append(status);
       sb.append(", serialVersionUID=").append(serialVersionUID);
       sb.append("]");
       return sb.toString();
   }

}
