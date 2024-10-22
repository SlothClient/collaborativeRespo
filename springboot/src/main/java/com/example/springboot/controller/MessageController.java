package com.example.springboot.controller;

import com.example.springboot.response.ChatHistory;
import com.example.springboot.service.MessageService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ys
 * @date： 2024/10/21 14:48
 */

@RestController
@RequestMapping("/message")
public class MessageController {


    @Autowired
    private MessageService messageService;

    @PostMapping("/getChatHistory")
    public Result<List<ChatHistory>> getChatHistory() {

        return messageService.getChatHistory();

    }
}
