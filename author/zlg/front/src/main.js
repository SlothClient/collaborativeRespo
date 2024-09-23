// 创建容器（应用）
import { createApp } from 'vue'
// 创建根组件
import App from './App.vue'
// 引入路由
import router from './router'
// 引入饿了么UI
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// 创建Vue实例对象
const app = createApp(App)
// 使用路由
app.use(router)
// 使用饿了么UI
app.use(ElementPlus)
// 挂载实例
app.mount('#app')