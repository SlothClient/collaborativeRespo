import {createRouter, createWebHistory} from 'vue-router'
import Common from '@/views/Common.vue'
import Device from '@/views/Device.vue'
import Home from '@/views/Home.vue'
import component from 'element-plus/es/components/tree-select/src/tree-select-option.mjs'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            component: Common,
            children: [
                {
                    path: '/',
                    component: Home
                },
                {
                    path: '/views/Device',
                    component: Device
                }

            ]
        }

    ]
})

export default router
