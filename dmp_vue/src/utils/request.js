import axios from "axios";
import {ElMessage, ElNotification} from "element-plus";
import {getToken} from "@/utils/token.js";
import {useLoadingStore} from "@/store/module/loadingStore.js";

// 创建 axios 实例
export const request = axios.create({
    baseURL: "/api",
    timeout: 10000,
    headers: {
        "Content-Type": "application/json;charset=UTF-8",
    },
});


// 请求拦截器
request.interceptors.request.use(
    function (config) {
        const loadingStore = useLoadingStore();
        loadingStore.startLoading(); // 开始加载动画
        return config;
    },
    function (error) {
        const loadingStore = useLoadingStore();
        loadingStore.stopLoading(); // 请求失败时隐藏加载动画
        return Promise.reject(error);
    }
);

request.interceptors.response.use(
    (response) => {
        const loadingStore = useLoadingStore();


        loadingStore.stopLoading(); // 结束加载动画

        return response;
    },
    (error) => {
        const loadingStore = useLoadingStore();


        loadingStore.stopLoading()
        // 处理错误
        let {message} = error;
        if (message === "Network Error") {
            message = "后端接口连接异常";
        } else if (message.includes("timeout")) {
            message = "系统接口请求超时";
        } else if (message.includes("Request failed with status code")) {
            message = "系统接口" + message.substring(message.length - 3) + "异常";
        }
        ElMessage({
            message: message,
            type: "error",
            duration: 5 * 1000,
        });
        return Promise.reject(error);
    }
);