import {createRouter, createWebHistory} from 'vue-router'

// 定义常量路由
export const constantRoutes = [
    {
        path: "/login",
        name: "login",
        component: () => import("@/views/login/login.vue")
    },
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/dashboard',
        name: 'dashboard',
        component: () => import('@/layout/container.vue'),
        children: []
    },
    {
        path: '/NotFound',
        name: 'NotFound',
        component: () => import('@/views/NotFound.vue')
    },
    {
        path: '/*',
        redirect: '/NotFound'
    }
]

export const router = createRouter({
    history: createWebHistory(),
    routes: constantRoutes
})