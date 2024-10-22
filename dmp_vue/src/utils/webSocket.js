// src/utils/websocket.js

let ws = null;
let reconnectTimer = null;
let isReconnecting = false;
const url = 'ws://localhost:8080/chat/';

const websocket = {
    // WebSocket 初始化
    Init(username) {
        if (!('WebSocket' in window)) {
            console.log('浏览器不支持 WebSocket');
            return;
        }

        ws = new WebSocket(url + username);

        ws.onopen = () => {
            console.log('WebSocket 连接成功');
        };

        ws.onerror = (e) => {
            console.log('WebSocket 连接错误', e);
            if (!isReconnecting) {
                reconnect();
            }
        };

        ws.onmessage = (e) => {
            console.log('接收到的 WebSocket 消息:', e.data);
            if (e.data === 'ok') return;

            if (websocket.onMessageCallback) {
                websocket.onMessageCallback(e.data);
            }
        };

        ws.onclose = () => {
            console.log('WebSocket 连接关闭');
            if (!isReconnecting) {
                reconnect();
            }
        };
    },

    // 手动关闭 WebSocket 连接
    Close() {
        isReconnecting = true;
        if (ws) {
            ws.close();
        }
    },

    // 发送消息
    Send(data) {
        const msg = JSON.stringify(data);
        if (ws && ws.readyState === WebSocket.OPEN) {
            ws.send(msg);
        } else {
            console.error('WebSocket 尚未连接或连接已关闭');
        }
    },

    // 设置消息回调
    setMessageCallback(callback) {
        this.onMessageCallback = callback;
    },

    onMessageCallback: null
};

// 自动重连机制
function reconnect() {
    if (isReconnecting) return;

    clearTimeout(reconnectTimer);
    reconnectTimer = setTimeout(() => {
        console.log('尝试重新连接 WebSocket...');
        websocket.Init('');
        isReconnecting = false;
    }, 4000); // 4 秒后重新连接
}

// 页面关闭事件，关闭 WebSocket 连接
window.onbeforeunload = () => {
    websocket.Close();
};

export default websocket;