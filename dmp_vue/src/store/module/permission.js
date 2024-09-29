import {defineStore} from "pinia";
import {constantRoutes, router} from "@/router/index.js";
import {getUserMenu} from "@/api/user/index.js";
import {ElNotification} from "element-plus";
import {ref} from 'vue'
const modules = import.meta.glob('@/views/**/*.vue');  // 递归加载 views 目录下的所有 Vue 文件

const filterAsyncRoutes = (routes) => {
    console.log(modules)
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
            const menuUrl = route.menuUrl.replace(/^\//, '');  // 去掉路径开头的 "/"

            // 找到匹配的组件路径
            const componentPath = `/src/views/${menuUrl}.vue`;

            // 使用 import.meta.glob 加载组件
            if (modules[componentPath]) {
                routeObj.component = modules[componentPath];  // 赋值为加载的组件函数
                console.log(modules[componentPath])
            } else {
                console.error(`Component not found for path: ${componentPath}`);
            }
        }

        // 递归处理子路由
        if (route.children && route.children.length > 0) {
            routeObj.children = filterAsyncRoutes(route.children);
        }
        return routeObj;
    });
}

export const usePermissionStore = defineStore("permission", () => {
    const routes = ref([...constantRoutes])
    const setRoutes = (newRoutes) => {
        console.log("setRoutes")
        const homeRoute = routes.value.find(route => route.name === 'dashboard')

        console.log(homeRoute)
        if (homeRoute && homeRoute.children) {
            console.log("找到了")
            // 过滤掉已存在的路由
            const uniqueNewRoutes = newRoutes.filter(newRoute =>
                !homeRoute.children.some(existingRoute => existingRoute.path === newRoute.path)
            )
            homeRoute.children.push(...uniqueNewRoutes)
            console.log(homeRoute.children)
            // 动态注册新路由
            uniqueNewRoutes.forEach(route => {
                router.addRoute('dashboard', route);
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
                console.log(accessedRoutes)
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