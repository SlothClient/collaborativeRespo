// 创建history模式的路由
import { createRouter,createWebHistory } from "vue-router";
// 导入路由组件
import Home from "@/views/Home.vue";
import News from "@/views/News.vue";
import About from "@/views/About.vue";
import Data from "@/views/Data.vue";
import Login from "@/components/Login.vue";
import Layout from "@/components/Layout.vue";

// 配置路由
const router = createRouter({
  history: createWebHistory(),
  routes: [
    // {
    //     path: "/home/:page",
    //     component: Home,
    //     props:true

    // },
    // {
    //     path: "/news/:page",
    //     component: News,
    //     props:true

    // },
    // {
    //     path: "/about/:page",
    //     component: About,
    //     props:true
    // },
    // {
    //     path: "/data/:page",
    //     component: Data,
    //     props:true
    // },
    {
      path: "/",
      redirect: "/login",
    },
    {
      path: "/login",
      component: Login,
    },
    {
      path: "/layout",
      component:Layout,
      children:[
        {path:"/data/:page",component:Data,props:true},
      ]
    }
  ],
});

export default router;