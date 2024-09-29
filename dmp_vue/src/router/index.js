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
        children: [
            // {
            //     path: 'home',
            //     name: 'home',
            //     component: () => import('@/views/home.vue')
            // },
            // {
            //     path: 'Device',
            //     name: 'device',
            //     component: () => import('@/views/Device.vue')
            // },
            // {
            //     path: 'maintenance',
            //     name: 'maintenance',
            //     component: () => import('@/views/maintenancePlan/index.vue')
            // },
            // {
            //     path: 'WorkHours',
            //     name: 'workHours',
            //     component: () => import('@/views/WorkHours.vue')
            // },
            // {
            //     path: 'WorkOrder',
            //     name: 'workOrder',
            //     component: () => import('@/views/WorkOrder.vue')
            // },
            // {
            //     path: 'approve',
            //     name: 'approve',
            //     component: () => import('@/views/approval/index.vue'),
            //     meta: {requiresAuth: true, roles: ['admin']} // 示例权限设置
            // }
        ]
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: () => import('@/views/NotFound.vue')
    }
]

export const router = createRouter({
    history: createWebHistory(),
    routes: constantRoutes
})