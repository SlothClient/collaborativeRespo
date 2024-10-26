import {defineStore} from 'pinia'
import {ref} from 'vue'
import {login, logout} from "@/api/login/index.js";
import {removeToken, setToken} from "@/utils/token.js";
import {ElMessageBox, ElNotification} from "element-plus";
import {getRole, getUserInfo} from "@/api/user/index.js";
import {closeWebSocket, initializeWebSocket} from "@/utils/webSocket.js";


export const useUserStore = defineStore('userStore', () => {
    // 状态
    const user = ref({
        id: null,
        username: '',
        roles: [],
        permissions: [], // 存储用户的权限
        token: '',
        avatar: null,
    })

    const clearUser = () => {
        user.value = {
            id: null,
            username: '',
            roles: [],
            permissions: [],
        }
    }
    const Login = async (loginForm) => {
        try {
            const {data} = await login(loginForm)
            if (data.flag) {
                setToken(data.data)
                return true
            }
            return false
        } catch (e) {
            return false
        }
    }
    const Logout = async () => {
        const res = await logout()
        if (res.data.flag) {
            removeToken()
            clearUser()
            closeWebSocket()
            return true;
        } else {
            ElNotification({
                title: "失败",
                message: res.msg,
                type: "error"
            });
            return false;
        }
    }

    const GetUserInfo = async () => {
        try {
            const res = await getUserInfo();
            if (res.data.flag) {
                user.value = {
                    id: res.data.data.id,
                    username: res.data.data.username,
                    roles: res.data.data.roles,
                    permissions: res.data.data.permissions,
                    avatar: `http://localhost:8080` + res.data.data.avatar // 添加头像字段
                }
                initializeWebSocket(user.value.id)
                console.log(user.value)
            } else {
                ElNotification({
                    title: "失败",
                    message: res.data.msg,
                    type: "error"
                });
            }
        } catch (e) {
            throw e;
        }
    }

    const role = ref([]);
    const getRoleList = async () => {
        if (role.value.length === 0) {
            const res = await getRole();
            role.value = res.data.data;
        }
    };

    return {
        Login,
        Logout,
        GetUserInfo,
        user,
        role,
        getRoleList
    }
})