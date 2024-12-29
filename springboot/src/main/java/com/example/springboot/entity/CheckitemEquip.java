package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2024-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("checkitem_equip")
public class CheckitemEquip implements Serializable {



    @TableId(type = ASSIGN_ID)
    private String equipitemId;

    private String equipId;

    private String checkitemName;

    private String checkitemWay;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;


    }
