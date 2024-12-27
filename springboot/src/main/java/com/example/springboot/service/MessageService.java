package com.example.springboot.service;

import com.example.springboot.response.ChatHistory;
import com.example.springboot.utils.Result;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author ys
 * @date： 2024/10/21 14:51
 */
public interface MessageService {
    Result<List<ChatHistory>> getChatHistory();

    Result<Void> markAsRead(String otherUserId);

    Result<Void> notifySuperior(String message,String roleName) throws ParseException;
}
