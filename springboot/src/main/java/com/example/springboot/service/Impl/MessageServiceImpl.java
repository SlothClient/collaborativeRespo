package com.example.springboot.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springboot.entity.ChatRecord;
import com.example.springboot.entity.Message;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.mapper.ChatRecordMapper;
import com.example.springboot.mapper.UserInfoMapper;
import com.example.springboot.response.ChatHistory;
import com.example.springboot.service.MessageService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ys
 * @date： 2024/10/21 14:51
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    ChatRecordMapper chatRecordMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public Result<List<ChatHistory>> getChatHistory() {

        String currentLoginId = (String) StpUtil.getLoginId();

        UserInfo chatUser = userInfoMapper.selectById(currentLoginId);


        // 获取所有用户
        List<UserInfo> allUsers = userInfoMapper.selectList(null);

        // 构建ChatHistory列表
        List<ChatHistory> chatHistories = new ArrayList<>();
        for (UserInfo otherUser : allUsers) {
            if (!otherUser.getUserId().equals(chatUser.getUserId())) {
                String chatWithId = otherUser.getUserId();
                String chatWith = otherUser.getUsername();
                String chatWithAvatar = otherUser.getAvatarUrl();

                // 获取与该用户的聊天记录
                List<ChatRecord> userChatRecords = chatRecordMapper.selectList(
                        new LambdaQueryWrapper<ChatRecord>()
                                .eq(ChatRecord::getReceiver, chatUser.getUserId())
                                .and(wrapper -> wrapper.eq(ChatRecord::getSender, chatWithId))
                                .or()
                                .eq(ChatRecord::getSender, chatUser.getUserId())
                                .and(wrapper -> wrapper.eq(ChatRecord::getReceiver, chatWithId))
                );

                List<Message> messages = userChatRecords.stream().map(record -> {
                    Message message = new Message();
                    message.setSender(record.getSender());
                    message.setReceiver(record.getReceiver());
                    message.setText(record.getText());
                    message.setTime(record.getTime()); // 假设record.getTime()返回的是Date类型
                    return message;
                }).collect(Collectors.toList());

                ChatHistory chatHistory = new ChatHistory();
                chatHistory.setChatUser(chatUser.getUsername());
                chatHistory.setChatUserWithAvatar(chatWithAvatar);
                chatHistory.setChatUserId(chatUser.getUserId());
                chatHistory.setChatUserWith(chatWith);
                chatHistory.setChatUserWithId(chatWithId);
                chatHistory.setMessages(messages);
                chatHistories.add(chatHistory);
            }
        }

        return Result.success(chatHistories);


    }
}
