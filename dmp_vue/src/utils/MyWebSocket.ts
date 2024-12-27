/**
 * 接口参数定义
 */
interface SocketParams {
    url?: string; // WebSocket 服务器的URL，默认为配置中的默认URL

    reconnectLimit?: number; // 最大重连次数限制，默认为配置中的重连限制

    openCallback?: (event: Event) => void; // 连接成功打开时的回调函数

    messageCallback?: (event: MessageEvent) => void; // 接收到消息时的回调函数

    closeCallback?: (event: CloseEvent) => void; // 连接关闭时的回调函数

    errorCallback?: (event: Event) => void; // 发生错误时的回调函数

    enableLogging?: boolean; // 是否启用日志记录

    authToken?: string; // 用于身份验证的Token，通常通过URL参数传递

    heartbeatMessage?: string; // 心跳消息内容，默认值为'Ping'

    maxHeartbeatInterval?: number; // 心跳发送的最大间隔时间，单位为毫秒

    messageTimeout?: number; // 消息发送超时时间，单位为毫秒

    reconnectStrategy?: {
        initialDelay: number; // 初始重连延迟时间，单位为毫秒
        maxDelay: number; // 最大重连延迟时间，单位为毫秒
        factor: number; // 重连延迟的增长因子，用于指数退避策略
    };
}

/**
 * 默认配置对象，包含WebSocket连接的各种默认参数
 */
const config = {
    DEFAULT_URL: "wss://example.com/socket", // 默认的WebSocket URL

    RECONNECT_LIMIT: 5, // 默认的最大重连次数

    RECONNECT_INTERVAL: 1000, // 默认的初始重连延迟，单位为毫秒

    HEART_INTERVAL: 5000, // 默认的心跳发送间隔，单位为毫秒

    ERROR_CODE: {
        normal: 1000, // 正常关闭的状态码
    },

    DEFAULT_RECONNECT_STRATEGY: {
        initialDelay: 1000, // 默认的初始重连延迟，单位为毫秒
        maxDelay: 30000, // 默认的最大重连延迟，单位为毫秒
        factor: 2, // 默认的重连延迟增长因子
    },
};

/**
 * MyWebSocket 类封装了WebSocket的连接管理、重连策略、心跳机制、消息队列等功能
 */
export default class MyWebSocket {
    private url: string; // WebSocket服务器的URL
    private webSocket: WebSocket | null = null; // WebSocket实例
    private heartBeatTimer: number | null = null; // 心跳定时器ID
    private messageQueue: Array<() => void> = []; // 消息队列，存储待发送的消息

    // 回调函数
    private openCallback?: (event: Event) => void;
    private messageCallback?: (event: MessageEvent) => void;
    private closeCallback?: (event: CloseEvent) => void;
    private errorCallback?: (event: Event) => void;

    private reconnectLimit: number; // 最大重连次数
    private isInReconnect: boolean = false; // 是否正在进行重连
    private reconnectCount: number = 0; // 当前已重连次数
    private currentReconnectDelay: number = config.RECONNECT_INTERVAL; // 当前重连延迟
    private maxReconnectDelay: number = 30000; // 最大允许的重连延迟，单位为毫秒

    private authToken?: string; // 身份验证Token
    private heartBeatMessage: string; // 心跳消息内容
    private maxHeartBeatInterval: number; // 心跳发送间隔，单位为毫秒
    private messageTimeout: number; // 消息发送超时时间，单位为毫秒

    private reconnectStrategy: {
        initialDelay: number;
        maxDelay: number;
        factor: number;
    }; // 重连策略配置

    private enableLogging: boolean; // 是否启用日志记录

    /**
     * 构造函数，初始化MyWebSocket实例
     * @param params SocketParams 接口参数
     */
    constructor({
        url,
        reconnectLimit,
        openCallback,
        messageCallback,
        closeCallback,
        errorCallback,
        enableLogging = false,
        authToken,
        heartbeatMessage = "Ping",
        maxHeartbeatInterval = config.HEART_INTERVAL,
        messageTimeout = 5000,
        reconnectStrategy = config.DEFAULT_RECONNECT_STRATEGY,
    }: SocketParams) {
        // 初始化参数，使用传入的参数或默认配置
        this.url = url || config.DEFAULT_URL;
        this.reconnectLimit = reconnectLimit ?? config.RECONNECT_LIMIT;
        this.openCallback = openCallback;
        this.messageCallback = messageCallback;
        this.closeCallback = closeCallback;
        this.errorCallback = errorCallback;
        this.enableLogging = enableLogging;
        this.authToken = authToken;
        this.heartBeatMessage = heartbeatMessage;
        this.maxHeartBeatInterval = maxHeartbeatInterval;
        this.messageTimeout = messageTimeout;
        this.reconnectStrategy = reconnectStrategy;

        // 创建WebSocket实例并初始化网络监听器
        this.createWebSocket();
        this.initNetworkListener();
    }

    /**
     * 创建WebSocket实例并初始化事件监听
     */
    private createWebSocket(): void {
        try {
            let webSocketUrl = this.url;

            // 如果存在authToken，则通过URL参数传递Token进行身份验证
            if (this.authToken) {
                const separator = webSocketUrl.includes('?') ? '&' : '?';
                webSocketUrl += `${separator}token=${encodeURIComponent(this.authToken)}`;
            }

            // 创建WebSocket实例
            this.webSocket = new WebSocket(webSocketUrl);
            this.log(`正在连接到: ${webSocketUrl}`);

            // 初始化WebSocket事件监听
            this.initEventMonitor();
        } catch (e) {
            this.log('WebSocket 创建连接失败', e);
            this.handleReconnect();
        }
    }

    /**
     * 关闭WebSocket连接并清理资源
     */
    public close(): void {
        this.log("关闭WebSocket连接");
        this.closeWebSocket();
        this.stopHeartBeat();
    }

    /**
     * 移除WebSocket事件监听器并关闭连接
     */
    private closeWebSocket(): void {
        if (this.webSocket) {
            // 移除所有事件监听器
            this.webSocket.removeEventListener('open', this.onOpen);
            this.webSocket.removeEventListener('message', this.onMessage);
            this.webSocket.removeEventListener('close', this.onClose);
            this.webSocket.removeEventListener('error', this.onError);

            // 关闭WebSocket连接
            this.webSocket.close();
            this.webSocket = null;
        }
    }

    /**
     * 初始化WebSocket事件监听器，绑定open、message、close、error事件
     */
    private initEventMonitor(): void {
        if (!this.webSocket) return;

        // 绑定事件处理函数，并确保`this`上下文正确
        this.webSocket.addEventListener('open', this.onOpen.bind(this));
        this.webSocket.addEventListener('message', this.onMessage.bind(this));
        this.webSocket.addEventListener('close', this.onClose.bind(this));
        this.webSocket.addEventListener('error', this.onError.bind(this));
    }

    /**
     * WebSocket连接打开时的处理函数
     * @param event Event 事件对象
     */
    private onOpen(event: Event): void {
        this.log('WebSocket连接已打开');

        // 重置重连计数和延迟
        this.resetReconnect();

        // 停止现有的心跳机制，以防重复启动
        this.stopHeartBeat();

        // 执行用户提供的连接打开回调函数
        if (this.openCallback) {
            this.openCallback(event);
        }

        // 启动心跳机制，定期发送心跳消息
        this.startHeartBeat();

        // 发送消息队列中的所有待发送消息
        this.flushMessageQueue();
    }

    /**
     * 接收到WebSocket消息时的处理函数
     * @param event MessageEvent 消息事件对象
     */
    private onMessage(event: MessageEvent): void {
        this.log('收到的消息:', event.data);

        // 执行用户提供的消息回调函数
        if (this.messageCallback) {
            this.messageCallback(event);
        }
    }

    /**
     * WebSocket连接关闭时的处理函数
     * @param event CloseEvent 关闭事件对象
     */
    private onClose(event: CloseEvent): void {
        this.log(`WebSocket连接已关闭，状态码：${event.code}`);

        // 执行用户提供的连接关闭回调函数
        if (this.closeCallback) {
            this.closeCallback(event);
        }

        // 停止心跳机制
        this.stopHeartBeat();

        // 如果连接不是正常关闭且未达到重连次数限制，尝试重连
        if (event.code !== config.ERROR_CODE.normal && this.reconnectCount < this.reconnectLimit) {
            this.handleReconnect();
        }
    }

    /**
     * WebSocket发生错误时的处理函数
     * @param event Event 错误事件对象
     */
    private onError(event: Event): void {
        this.log('WebSocket发生错误', event);

        // 执行用户提供的错误回调函数
        if (this.errorCallback) {
            this.errorCallback(event);
        }

        // 停止心跳机制
        this.stopHeartBeat();

        // 如果未达到重连次数限制，尝试重连
        if (this.reconnectCount < this.reconnectLimit) {
            this.handleReconnect();
        }
    }

    /**
     * 处理重连逻辑，使用指数退避策略
     */
    private handleReconnect(): void {
        // 如果已经在重连中或已达到最大重连次数，则停止重连
        if (this.isInReconnect || this.reconnectCount >= this.reconnectLimit) {
            this.log('已达到最大重连次数，停止重连');
            return;
        }

        this.isInReconnect = true; // 标记为正在重连

        // 计算下次重连的延迟时间，使用指数退避策略
        this.currentReconnectDelay = Math.min(
            this.reconnectStrategy.initialDelay * Math.pow(this.reconnectStrategy.factor, this.reconnectCount),
            this.reconnectStrategy.maxDelay
        );

        this.reconnectCount++; // 增加已重连次数

        this.log(`尝试第 ${this.reconnectCount} 次重连，等待 ${this.currentReconnectDelay} 毫秒`);

        // 延迟后执行重连
        setTimeout(() => {
            this.isInReconnect = false; // 重连完成，重置重连标志
            this.createWebSocket(); // 尝试重新创建WebSocket连接
        }, this.currentReconnectDelay);
    }

    /**
     * 重置重连计数和当前重连延迟
     */
    private resetReconnect(): void {
        this.reconnectCount = 0; // 重置已重连次数
        this.currentReconnectDelay = this.reconnectStrategy.initialDelay; // 重置重连延迟
    }

    /**
     * 初始化网络状态监听器，监听浏览器的在线和离线事件
     */
    private initNetworkListener(): void {
        // 监听网络恢复事件，当网络重新连接时尝试重新连接WebSocket
        window.addEventListener('online', this.handleOnline.bind(this));

        // 监听网络断开事件，当网络断开时关闭WebSocket连接
        window.addEventListener('offline', this.handleOffline.bind(this));
    }

    /**
     * 处理网络恢复事件
     */
    private handleOnline(): void {
        this.log('网络已连接');

        // 如果WebSocket未连接，则尝试重新创建连接
        if (!this.isSocketOpen()) {
            this.createWebSocket();
        }
    }

    /**
     * 处理网络断开事件
     */
    private handleOffline(): void {
        this.log('网络已断开');
        this.close(); // 关闭WebSocket连接
    }

    /**
     * 检查WebSocket是否正在连接
     * @returns boolean 如果WebSocket正在连接，则返回true
     */
    private isSocketConnecting(): boolean {
        return this.webSocket !== null && this.webSocket.readyState === WebSocket.CONNECTING;
    }

    /**
     * 检查WebSocket是否已连接
     * @returns boolean 如果WebSocket已打开，则返回true
     */
    private isSocketOpen(): boolean {
        return this.webSocket !== null && this.webSocket.readyState === WebSocket.OPEN;
    }

    /**
     * 发送消息队列中的所有待发送消息
     */
    private flushMessageQueue(): void {
        // 遍历消息队列，逐一发送消息
        while (this.messageQueue.length > 0 && this.isSocketOpen()) {
            const send = this.messageQueue.shift(); // 取出队列中的第一个消息发送函数
            if (typeof send === 'function') {
                send(); // 执行发送函数
            }
        }
    }

    /**
     * 启动心跳机制，定期发送心跳消息以保持连接
     */
    private startHeartBeat(): void {
        this.log('心跳机制启动');

        // 使用setInterval定期发送心跳消息
        this.heartBeatTimer = window.setInterval(() => {
            this.sendPing();
        }, this.maxHeartBeatInterval);
    }

    /**
     * 停止心跳机制，清除心跳定时器
     */
    private stopHeartBeat(): void {
        if (this.heartBeatTimer) {
            clearInterval(this.heartBeatTimer); // 清除定时器
            this.heartBeatTimer = null;
            this.log('心跳机制已停止');
        }
    }

    /**
     * 发送心跳消息，确保WebSocket连接处于活跃状态
     */
    private sendPing(): void {
        if (this.webSocket && this.webSocket.readyState === WebSocket.OPEN) {
            this.log(`发送心跳消息: ${this.heartBeatMessage}`);
            this.webSocket.send(this.heartBeatMessage); // 发送心跳消息
        }
    }

    /**
    * 发送消息到WebSocket服务器
    * 如果WebSocket已打开，则直接发送；否则，将消息加入消息队列等待发送
    * @param message 要发送的消息内容，可以是字符串或二进制数据
    */
    public send(message: any): void {
        if (this.isSocketOpen()) {
            try {
                this.webSocket?.send(message); // 发送消息
                this.log('发送消息:', message);
            } catch (error) {
                this.log('发送消息失败，将消息加入队列', error);
                this.messageQueue.push(() => this.send(message)); // 发送失败，将消息加入队列
            }
        } else {
            this.log('WebSocket未连接，将消息加入队列');
            this.messageQueue.push(() => this.send(message)); // WebSocket未连接，将消息加入队列
        }
    }

    /**
     * 手动触发重连，允许外部调用以重新建立WebSocket连接
     */
    public reconnect(): void {
        if (!this.isInReconnect && this.reconnectCount < this.reconnectLimit) {
            this.handleReconnect(); // 执行重连逻辑
        }
    }

    /**
     * 打印日志信息，只有在启用了日志记录时才会输出
     * @param args 任意数量的参数
     */
    private log(...args: any[]): void {
        if (this.enableLogging) {
            console.log('[MyWebSocket]', ...args); // 使用统一的日志前缀
        }
    }
}