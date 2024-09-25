import {defineStore} from "pinia";
import {constantRoutes, router} from "@/router/index.js";
import {getUserMenu} from "@/api/user/index.js";
import {ElNotification} from "element-plus";
import {ref} from 'vue'


const componentMap = {
    // 'system': () => import('@/views/system/index.vue'),  // 系统管理的组件（假设为首页）
    // 'manage': () => import('@/views/system/manage.vue'),  // 用户管理的组件
    // 'approval': () => import('@/views/approval/index.vue'),  // 审批的父级组件
    // 'MyApproval': () => import('@/views/approval/MyApproval.vue'),  // 我的审批组件
    // 'AdminApprovalManage': () => import('@/views/approval/admin/AdminApprovalManage.vue'),  // 审批管理组件
    // 'ManagerApprovalManage': () => import('@/views/approval/manage/ManagerApprovalManage.vue')  // 其他审批组件
};

const filterAsyncRoutes = (routes) => {
    return routes.map(route => {
        const routeObj = {
            path: route.menuUrl || '',
            name: route.menuName,
            meta: {
                title: route.menuName,
                icon: route.menuIcon,
                permission: route.permission
            },
            component: null
        }

        // 处理组件
        if (route.menuUrl) {
            const componentKey = route.menuUrl.replace(/^\//, '');
            routeObj.component = componentMap[componentKey]
            console.log(routeObj.component)
        } else {
            console.log("走了这里")
            // routeObj.component = () => import('@/components/layout/dashboard.vue')
        }
        // 递归处理子路由
        if (route.children && route.children.length > 0) {
            routeObj.children = filterAsyncRoutes(route.children)
        }
        console.log(routeObj)
        return routeObj
    })
}

export const usePermissionStore = defineStore("permission", () => {
    const routes = ref([...constantRoutes])
    const setRoutes = (newRoutes) => {
        const homeRoute = routes.value.find(route => route.path === '/home')
        if (homeRoute && homeRoute.children) {
            // 过滤掉已存在的路由
            const uniqueNewRoutes = newRoutes.filter(newRoute =>
                !homeRoute.children.some(existingRoute => existingRoute.path === newRoute.path)
            )
            homeRoute.children.push(...uniqueNewRoutes)
            // 动态注册新路由
            uniqueNewRoutes.forEach(route => {
                router.addRoute('home', route);
            });
        }

    }

    const generateRoutes = async () => {
        try {
            const res = await getUserMenu()
            if (res.data.flag) {
                const asyncRoutes = res.data.data
                const accessedRoutes = filterAsyncRoutes(asyncRoutes)
                setRoutes(accessedRoutes)
                console.log(router.getRoutes())
                return accessedRoutes
            } else {
                ElNotification({
                    title: "失败",
                    message: res.data.msg,
                    type: "error"
                });
                return []
            }
        } catch (e) {
            console.error(e)
            ElNotification({
                title: "错误",
                message: "获取菜单失败",
                type: "error"
            });
            return []
        }
    }

    return {
        routes,
        generateRoutes,
        setRoutes
    }
})