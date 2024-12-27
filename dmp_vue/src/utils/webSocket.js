
import { useMessageStore } from "@/store/module/messageStore.js";
import {h, nextTick} from "vue";
import {ElNotification} from "element-plus";

import '@/assets/message.css'
let socket;

export const initializeWebSocket = (userId,emit) => {
    const messageStore = useMessageStore();

    socket = new WebSocket(`ws://localhost:8080/chat/${userId}`);

    socket.onopen = () => {
        console.log('WebSocket连接建立');
    };

    socket.onmessage = async (event) => {
        const receivedMessage = JSON.parse(event.data);

        ElNotification({
            title: '消息提醒',
            message: h('div', {
                class: 'notification-content',
                style: 'display: flex; align-items: center; padding: 4px 0;'
            }, [
                h('span', {
                    style: 'color: #606266; font-size: 14px; font-weight: 500;'
                }, '您有一条新消息，请及时查看')
            ]),
            icon:'Message',
            position: 'top-right',
            duration: 3000,
            showClose: true,
            customClass: 'custom-notification',
            offset: 30
        })


        const senderId = receivedMessage.from

        // 获取当前聊天对象的 ID
        const currentChatId = messageStore.currentChat?.chatUserWithId;

        // 当前未打开此用户的聊天，更新该用户的未读消息数量
        if (currentChatId !== senderId ) {
            messageStore.incrementUnreadMessages(senderId);
        }

        // 将接收到的消息添加到对应的聊天记录中
        messageStore.addMessage(receivedMessage);

        // 确保在消息渲染完成后执行滚动
        await nextTick();
        // 使用 emit 触发滚动事件，交由组件控制
        emit('scrollToBottom');
    };

    socket.onclose = () => {
        console.log('WebSocket 连接关闭');
    };


    socket.onerror = (error) => {
        console.error('WebSocket 错误:', error);
    };
};

export const webSocketSendMessage = (message) => {
    if (socket && socket.readyState === WebSocket.OPEN) {
        socket.send(JSON.stringify(message));
    } else {
        console.error("WebSocket is not open.");
    }
};

export const closeWebSocket = () => {
    if (socket) {
        socket.close();
    }
};