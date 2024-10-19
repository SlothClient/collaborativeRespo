package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2024-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("equip_status")
public class EquipStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "status_id", type = IdType.AUTO)
    private Integer statusId;

    private String statusName;


}
