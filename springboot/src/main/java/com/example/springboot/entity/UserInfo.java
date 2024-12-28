package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

/**
 * 
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = ASSIGN_ID)
    private String userId;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String userpwd;

    /**
     *
     */
    private String avatarUrl;

    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}