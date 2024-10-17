import {defineStore} from 'pinia'
import {ref} from 'vue'
import {login, logout} from "@/api/login/index.js";
import {removeToken, setToken} from "@/utils/token.js";
import {ElMessageBox, ElNotification} from "element-plus";
import {getUserInfo} from "@/api/user/index.js";
import {router} from "@/router/index.js";
import {getRole} from "@/api/user/index.js";

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
                localStorage.removeItem('dynamicRoutes')
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
            const res = await getUserInfo();
            if (res.data.flag) {
                user.value = {
                    id: res.data.data.id,
                    username:res.data.data.username,
                    roles: res.data.data.roles,
                    permissions: res.data.data.permissions,
                }
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
    const getRoleList = async ()=> {
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