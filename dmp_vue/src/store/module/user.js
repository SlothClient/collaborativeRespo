import {defineStore} from 'pinia'
import {ref} from 'vue'
import {login, logout} from "@/api/login/index.js";
import {removeToken, setToken} from "@/utils/token.js";
import {ElMessageBox, ElNotification} from "element-plus";
import {getUserInfo} from "@/api/user/index.js";
import {router} from "@/router/index.js";

export const useUserStore = defineStore('userStore', () => {
    // 状态
    const user = ref({
        id: null,
        username: '',
        roles: [],
        permissions: [], // 存储用户的权限
        token: '',
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
            console.log(data)
            if (data.flag) {
                setToken(data.data)
                console.log(data)
                return true
            }
            return false
        } catch (e) {
            return false
        }
    }
    const Logout = async () => {
        try {
            const {data} = await logout()
            if (data.flag) {
                removeToken()
                clearUser()
                await router.push('/login')
                await ElMessageBox({
                    type: 'success',
                    message: "退出成功"
                })

            } else {
                ElNotification({
                    title: "失败",
                    message: res.msg,
                    type: "error"
                });
            }

        } catch (e) {
            throw e
        }
        location.reload();
    }

    const GetUserInfo = async () => {
        try {
            const {data} = await getUserInfo();
            if (data.flag) {
                user.value = {
                    id: data.data.id,
                    username: data.data.username,
                    roles: data.data.roles,
                    permissions: data.data.permissions,
                }
                console.log(user.value)
            } else {
                ElNotification({
                    title: "失败",
                    message: data.msg,
                    type: "error"
                });
            }
        } catch (e) {
            throw e;
        }
    }

    return {
        Login,
        Logout,
        GetUserInfo,
        user
    }
})