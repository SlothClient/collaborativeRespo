package com.example.springboot.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.springboot.entity.*;
import com.example.springboot.handler.ChatWebSocketServer;
import com.example.springboot.mapper.ChatRecordMapper;
import com.example.springboot.mapper.RoleInfoMapper;
import com.example.springboot.mapper.UserInfoMapper;
import com.example.springboot.mapper.UserRoleMapper;
import com.example.springboot.response.ChatHistory;
import com.example.springboot.service.MessageService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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

    @Autowired
    UserRoleMapper userRoleMapper;


    @Override
    public Result<List<ChatHistory>> getChatHistory() {

        Map<String, String> stringMap = Map.of(
                "R001", "Approval",
                "R002", "Approval",
                "R003", "Common",
                "R004", "System"
        );

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
                String roleId = userRoleMapper.selectOne(
                        new LambdaQueryWrapper<UserRole>()
                                .eq(UserRole::getUserId, otherUser.getUserId())
                ).getRoleId();

                // 获取与该用户的聊天记录
                List<ChatRecord> userChatRecords = chatRecordMapper.selectList(
                        new LambdaQueryWrapper<ChatRecord>()
                                .and(wrapper -> wrapper
                                        .and(w -> w
                                                .eq(ChatRecord::getReceiver, chatUser.getUserId())
                                                .eq(ChatRecord::getSender, chatWithId))
                                        .or(w -> w
                                                .eq(ChatRecord::getSender, chatUser.getUserId())
                                                .eq(ChatRecord::getReceiver, chatWithId)))
                                .orderByAsc(ChatRecord::getTime)
                );
                // 获取未读消息数量
                Long unreadCount = chatRecordMapper.selectCount(
                        new LambdaQueryWrapper<ChatRecord>()
                                .eq(ChatRecord::getReceiver, chatUser.getUserId())
                                .eq(ChatRecord::getSender, chatWithId)
                                .eq(ChatRecord::getIsRead, 0)
                );
                List<Message> messages = userChatRecords.stream().map(record -> {
                    Message message = new Message();
                    message.setSender(record.getSender());
                    message.setReceiver(record.getReceiver());
                    message.setText(record.getText());
                    message.setTime(record.getTime());
                    message.setIsRead(record.getIsRead()); // 添加是否已读标记
                    return message;
                }).collect(Collectors.toList());
                ChatHistory chatHistory = new ChatHistory();
                chatHistory.setChatUser(chatUser.getUsername());
                chatHistory.setChatUserWithAvatar(chatWithAvatar);
                chatHistory.setChatUserId(chatUser.getUserId());
                chatHistory.setChatUserWith(chatWith);
                chatHistory.setChatUserWithId(chatWithId);
                chatHistory.setMessages(messages);
                chatHistory.setCategory(stringMap.get(roleId));
                chatHistory.setUnreadCountTotal(unreadCount.intValue()); // 设置未读消息数
                chatHistories.add(chatHistory);
            }
        }

        return Result.success(chatHistories);
    }

    // 添加标记消息已读的方法
    public Result<Void> markAsRead(String otherUserId) {
        String currentLoginId = (String) StpUtil.getLoginId();
        // 将所有从otherUserId发送给当前用户的未读消息标记为已读
        chatRecordMapper.update(null,
                new LambdaUpdateWrapper<ChatRecord>()
                        .eq(ChatRecord::getReceiver, currentLoginId)
                        .eq(ChatRecord::getSender, otherUserId)
                        .eq(ChatRecord::getIsRead, 0)
                        .set(ChatRecord::getIsRead, 1)
        );

        return Result.success();
    }

    @Override
    public Result<Void> notifySuperior(String message, String roleName) throws ParseException {
        String roleId = getRoleId(roleName);
        List<UserRole> userRoles = userRoleMapper.selectList(
                new LambdaQueryWrapper<UserRole>()
                        .eq(UserRole::getRoleId, roleId)
        );
        for (UserRole userRole : userRoles) {
            ChatWebSocketServer.sendToUser(userRole.getUserId(), message);
        }
        return null;
    }

    public String getRoleId(String roleName) {
        return switch (roleName) {
            case "Admin" -> "R001";
            case "Manager" -> "R002";
            case "User" -> "R003";
            case "System" -> "R004";
            default -> "Null";
        };
    }

}
