package com.example.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String sender; // 发送者名称

    private String receiver; // 接收者名称


    private String text; // 消息内容

    private Date time; // 消息发送时间

}