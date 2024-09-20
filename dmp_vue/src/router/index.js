import { createRouter, createWebHistory } from 'vue-router'
import Main from '@/views/Main.vue'
import Device from '@/views/Device.vue'
import Home from '@/views/Home.vue'
import Maintenance from '@/views/maintenancePlan/index.vue'
import WorkHours from '@/views/WorkHours.vue'
import WorkOrder from '@/views/WorkOrder.vue'
import Approve from '@/views/Approve.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: Main ,
      children:[
        {
          path:'/',
          component:Home
        },
        {
          path:'/views/Device',
        component:Device
        },
        {
          path:'/views/Maintenance',
          component:Maintenance
        },
        {
          path:'/views/WorkHours',
          component:WorkHours
        },
        {
          path:'/views/WorkOrder',
          component:WorkOrder
        },
        {
          path:'/views/Approve',
          component:Approve
        },

      ]
    }
  ]
})

export default router
