package com.example.springboot.handler;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.springboot.entity.ChatRecord;
import com.example.springboot.mapper.ChatRecordMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint(value = "/chat/{userId}")
@Component
public class WebSocketServer {

    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();


    private static ChatRecordMapper chatRecordMapper;

    @Autowired
    public void setChatRecordMapper(ChatRecordMapper chatRecordMapper) {
        this.chatRecordMapper = chatRecordMapper;
    }



    // 当用户建立连接时触发
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        sessionMap.put(userId, session);
        log.info("新聊天用户={}，当前在线人数：{}", userId, sessionMap.size());
    }

    // 当用户关闭连接时触发
    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        sessionMap.remove(userId);
        log.info("有一连接关闭，移除聊天用户={}，当前在线人数为：{}", userId, sessionMap.size());
    }

    // 当用户发送消息时触发
    @OnMessage
    public void onMessage(String params, Session session, @PathParam("userId") String userId) throws ParseException {
        log.info("服务器收到聊天用户={}的消息：{}", userId, params);

        // 解析收到的消息
        JSONObject object = JSONUtil.parseObj(params);
        String chatId = object.getStr("chatId");
        String toUserId = object.getStr("to");
        String text = object.getStr("text");
        String time = object.getStr("time");

        // 封装发送的数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("from", userId);
        jsonObject.set("to", toUserId);
        jsonObject.set("text", text);
        jsonObject.set("time", time);

        // 查询目标用户是否在线
        Session toSession = sessionMap.get(toUserId);

        if (toSession != null) {
            // 如果对方在线，则发送消息并存储聊天记录
            sendMessage(jsonObject.toString(), toSession);
            log.info("发送给用户username={}，消息：{}", toUserId, jsonObject.toString());
        }

        // 不管对方是否在线，都存储聊天记录
        insertChatData(jsonObject, chatId);
    }

    // 当发生错误时触发
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误", error);
    }

    // 服务器发送消息给客户端
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务器给用户【{}】发送消息：{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务器发送消息给客户端失败", e);
        }
    }


    /**
     * 将时间字符串转换为 Date 对象
     *
     * @param timeStr 时间字符串
     * @return 转换后的 Date 对象
     * @throws ParseException 如果时间字符串格式不匹配，则抛出异常
     */
    public static Date parseDate(String timeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(timeStr);
    }

    // MySQL 存储聊天记录
    private void insertChatData(JSONObject chatData, String chatId) throws ParseException {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setChatId(chatId);
        chatRecord.setSender(chatData.getStr("from"));
        chatRecord.setReceiver(chatData.getStr("to"));
        chatRecord.setText(chatData.getStr("text"));
        chatRecord.setTime(parseDate(chatData.getStr("time")));

        // 使用 MyBatis-Plus 保存聊天记录
        int result = chatRecordMapper.insert(chatRecord);

        if (result > 0) {
            log.info("聊天记录已存储到数据库, chatId={}", chatId);
        } else {
            log.error("存储聊天记录失败, chatId={}", chatId);
        }
    }
}