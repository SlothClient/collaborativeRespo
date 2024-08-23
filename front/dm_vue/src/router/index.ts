import { createRouter, createWebHistory } from 'vue-router'
import Common from '@/Common.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/',
      component:Common
     }
  ]
})

export default router
