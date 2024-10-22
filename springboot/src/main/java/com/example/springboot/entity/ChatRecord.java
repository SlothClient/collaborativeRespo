package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @TableName chat_record
 */
@TableName(value ="chat_record")
@Data
public class ChatRecord implements Serializable {
    /**
     * 
     */
    @TableId
    private String chatId;

    /**
     * 
     */
    private String sender;

    /**
     * 
     */
    private String receiver;

    /**
     * 
     */
    private String text;

    /**
     * 
     */
    private Date time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}