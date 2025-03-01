package com.example.springboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.springboot.response.ChatHistory;
import com.example.springboot.service.MessageService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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

    @PutMapping("/read/{otherUserId}")
    public Result<Void> markAsRead(@PathVariable String otherUserId) {
        return messageService.markAsRead(otherUserId);
    }


    /**
     * 通知
     * @param message
     * @return
     */
    @SaCheckLogin
    @ResponseBody
    @GetMapping("/notify")
    public Result<Void> notifySuperior(@RequestParam("message") String message,@RequestParam("roleName") String roleName) throws ParseException {
        return messageService.notifySuperior(message,roleName);
    }

}
