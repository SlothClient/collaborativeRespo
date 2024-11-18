
/**
 * 接口参数
 */
interface SocketParams {
    url?: string; //地址

    reconnectLimit?: number; //重连次数限制

    openCallback?: (event: Event) => void;//连接打开回调

    messageCallback?: (event: Event) => void //接收消息回调

    closeCallback?: (event: Event) => void //连接关闭回调

    errorCallback?: (event: Event) => void //发生错误回调

    enableLogging?: boolean; //是否打印日志

    authToken?: string // token,省份验证

    heartbeatMessage?: string; //心跳消息，默认为'ping'

    maxHeartbeatInterval?: number; // 最大心跳间隔时间

    headers?: { [key: string]: string };

    messageTimeout?:number;//消息发送超时时间

    /**
     * 重连策略
     */
    reconnectStrategy?:{
        initialDelay:number; //初始重连延迟
        maxDelay:number;//最大重连延迟
    }



}



let config = {
    /** 默认的 WebSocket URL */
    DEFAULT_URL: "wss://example.com/socket",

    /** 默认的最大重连次数 */
    RECONNECT_LIMIT: 5,

    /** 默认的初始重连间隔时间，单位为毫秒 */
    RECONNECT_INTERVAL: 1000, // 初始重连间隔

    /** 默认的心跳间隔时间，单位为毫秒 */
    HEART_INTERVAL: 5000, // 心跳间隔

    /** WebSocket 错误状态码 */
    ERROR_CODE: {
        /** 正常关闭状态码 */
        normal: 1000,
        /** 其他错误码，可根据需要添加 */
        // ...
    },

    /** 默认的重连策略配置 */
    DEFAULT_RECONNECT_STRATEGY: {
        /** 初始重连延迟，单位为毫秒 */
        initialDelay: 1000,

        /** 最大重连延迟，单位为毫秒 */
        maxDelay: 30000,

        /** 重连延迟的增长因子 */
        factor: 2,
    },
}; 

