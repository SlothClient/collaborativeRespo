import axios from "axios";
import {ElMessage, ElNotification} from "element-plus";
import {getToken} from "@/utils/token.js";

export const request = axios.create({
    baseURL: "/api",
    timeout: 10000,
    headers: {
        "Content-Type": "application/json;charset=UTF-8",
    },
});
// 添加请求拦截器
axios.interceptors.request.use(
    function (config) {
        //请求头携带token
        if(getToken()){
            config.headers["Authorization"] = getToken();

        }
        return config;
    },
    function (error) {
        // 对请求错误做些什么
        return Promise.reject(error);
    }
);

// 配置响应拦截器
request.interceptors.response.use(
    (response) => {
        switch (response.data.code) {
            case 400:
                ElNotification({
                    title: "失败",
                    message: response.data.msg,
                    type: "error",
                });
                break;
            case 500:
                ElNotification({
                    title: "失败",
                    message: response.data.msg,
                    type: "error",
                });
                break;
        }
        return response;
    },
    (error) => {
        let { message } = error;
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
