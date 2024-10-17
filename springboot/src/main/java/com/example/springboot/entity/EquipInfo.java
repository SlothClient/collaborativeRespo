package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@TableName("equip_info")
public class EquipInfo implements Serializable {

    private static final long serialVersionUID = 1L;

//    @TableId(value = "equip_id", type = IdType.AUTO)
    @TableId(type = ASSIGN_ID)
    private String equipId;

    private String equipName;

    private String equipPic;

    private String statusName;

    private Integer validTime;

    private LocalDateTime purchaseDate;

    private Integer fixBig;

    private Integer fixMedium;

    private Integer fixSmall;

    private Integer countBig;

    private Integer countMedium;

    private Integer countSmall;

    private LocalDateTime lastMaintance;

    private String remarks;

    private String departmentName;

    private String typeName;

    private String siteName;


    @JsonCreator
    public EquipInfo(
            @JsonProperty("equipId") String equipId,
            @JsonProperty("equipName") String equipName,
            @JsonProperty("equipPic") String equipPic,
            @JsonProperty("statusName") String statusName,
            @JsonProperty("validTime") Integer validTime,
            @JsonProperty("purchaseDate") LocalDateTime purchaseDate,
            @JsonProperty("fixBig") Integer fixBig,
            @JsonProperty("fixMedium") Integer fixMedium,
            @JsonProperty("fixSmall") Integer fixSmall,
            @JsonProperty("countBig") Integer countBig,
            @JsonProperty("countMedium") Integer countMedium,
            @JsonProperty("countSmall") Integer countSmall,
            @JsonProperty("lastMaintance") LocalDateTime lastMaintance,
            @JsonProperty("remarks") String remarks,
            @JsonProperty("departmentName") String departmentName,
            @JsonProperty("typeName") String typeName,
            @JsonProperty("siteName") String siteName
            ) {
        this.equipId = equipId;
        this.equipName = equipName;
        this.equipPic = equipPic;
        this.statusName = statusName;
        this.validTime = validTime;
        this.purchaseDate = purchaseDate;
        this.fixBig = fixBig;
        this.fixMedium = fixMedium;
        this.fixSmall = fixSmall;
        this.countBig = countBig;
        this.countMedium = countMedium;
        this.countSmall = countSmall;
        this.lastMaintance = lastMaintance;
        this.remarks = remarks;
        this.departmentName = departmentName;
        this.typeName = typeName;
        this.siteName = siteName;
    }
}