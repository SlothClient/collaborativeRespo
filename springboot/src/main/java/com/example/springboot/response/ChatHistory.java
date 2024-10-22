package com.example.springboot.response;


import com.example.springboot.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistory {

    private String category; // 用户类别或其他分类信息


    private String chatUser; // 当前用户的名称

    private String chatWithRole; // 对方角色


    private String chatUserId; // 当前用户的ID

    private String chatUserWith; // 对话对象的名称


    private String chatUserWithId; // 对话对象的ID

    private List<Message> messages; // 消息列表

    private String chatUserWithAvatar;//用户头像
}