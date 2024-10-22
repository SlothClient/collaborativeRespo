package com.example.springboot.service;

import com.example.springboot.response.ChatHistory;
import com.example.springboot.utils.Result;

import java.util.List;

/**
 * @author ys
 * @date： 2024/10/21 14:51
 */
public interface MessageService {
    Result<List<ChatHistory>> getChatHistory();
}
