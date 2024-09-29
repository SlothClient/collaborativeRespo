package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;
import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID;

/**
 * 
 * @TableName equip_info
 */
@TableName(value ="equip_info")
@Data
public class EquipInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = ASSIGN_ID)
    private String equipId;

    /**
     * 
     */
    private String equipName;

    /**
     * 
     */
    private String equipPic;

    /**
     * 
     */
    private Integer equipStatus;

    /**
     * 
     */
    private Integer validTime;

    /**
     * 
     */
    private Date purchaseDate;

    /**
     * 
     */
    private Integer fixBig;

    /**
     * 
     */
    private Integer fixMedium;

    /**
     * 
     */
    private Integer fixSmall;

    /**
     * 
     */
    private Integer countBig;

    /**
     * 
     */
    private Integer countMedium;

    /**
     * 
     */
    private Integer countSmall;

    /**
     * 
     */
    private Date lastMaintance;

    /**
     * 
     */
    private String remarks;

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
        EquipInfo other = (EquipInfo) that;
        return (this.getEquipId() == null ? other.getEquipId() == null : this.getEquipId().equals(other.getEquipId()))
            && (this.getEquipName() == null ? other.getEquipName() == null : this.getEquipName().equals(other.getEquipName()))
            && (this.getEquipPic() == null ? other.getEquipPic() == null : this.getEquipPic().equals(other.getEquipPic()))
            && (this.getEquipStatus() == null ? other.getEquipStatus() == null : this.getEquipStatus().equals(other.getEquipStatus()))
            && (this.getValidTime() == null ? other.getValidTime() == null : this.getValidTime().equals(other.getValidTime()))
            && (this.getPurchaseDate() == null ? other.getPurchaseDate() == null : this.getPurchaseDate().equals(other.getPurchaseDate()))
            && (this.getFixBig() == null ? other.getFixBig() == null : this.getFixBig().equals(other.getFixBig()))
            && (this.getFixMedium() == null ? other.getFixMedium() == null : this.getFixMedium().equals(other.getFixMedium()))
            && (this.getFixSmall() == null ? other.getFixSmall() == null : this.getFixSmall().equals(other.getFixSmall()))
            && (this.getCountBig() == null ? other.getCountBig() == null : this.getCountBig().equals(other.getCountBig()))
            && (this.getCountMedium() == null ? other.getCountMedium() == null : this.getCountMedium().equals(other.getCountMedium()))
            && (this.getCountSmall() == null ? other.getCountSmall() == null : this.getCountSmall().equals(other.getCountSmall()))
            && (this.getLastMaintance() == null ? other.getLastMaintance() == null : this.getLastMaintance().equals(other.getLastMaintance()))
            && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEquipId() == null) ? 0 : getEquipId().hashCode());
        result = prime * result + ((getEquipName() == null) ? 0 : getEquipName().hashCode());
        result = prime * result + ((getEquipPic() == null) ? 0 : getEquipPic().hashCode());
        result = prime * result + ((getEquipStatus() == null) ? 0 : getEquipStatus().hashCode());
        result = prime * result + ((getValidTime() == null) ? 0 : getValidTime().hashCode());
        result = prime * result + ((getPurchaseDate() == null) ? 0 : getPurchaseDate().hashCode());
        result = prime * result + ((getFixBig() == null) ? 0 : getFixBig().hashCode());
        result = prime * result + ((getFixMedium() == null) ? 0 : getFixMedium().hashCode());
        result = prime * result + ((getFixSmall() == null) ? 0 : getFixSmall().hashCode());
        result = prime * result + ((getCountBig() == null) ? 0 : getCountBig().hashCode());
        result = prime * result + ((getCountMedium() == null) ? 0 : getCountMedium().hashCode());
        result = prime * result + ((getCountSmall() == null) ? 0 : getCountSmall().hashCode());
        result = prime * result + ((getLastMaintance() == null) ? 0 : getLastMaintance().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", equipId=").append(equipId);
        sb.append(", equipName=").append(equipName);
        sb.append(", equipPic=").append(equipPic);
        sb.append(", equipStatus=").append(equipStatus);
        sb.append(", validTime=").append(validTime);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append(", fixBig=").append(fixBig);
        sb.append(", fixMedium=").append(fixMedium);
        sb.append(", fixSmall=").append(fixSmall);
        sb.append(", countBig=").append(countBig);
        sb.append(", countMedium=").append(countMedium);
        sb.append(", countSmall=").append(countSmall);
        sb.append(", lastMaintance=").append(lastMaintance);
        sb.append(", remarks=").append(remarks);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}