import './style.css'
import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import pinia from "@/store/index.js";
import App from './App.vue'
import {router} from './router'
import './permission.js'
import emitter from './utils/emitter'
import ECharts from 'vue-echarts'
import "echarts";


const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(router)
app.use(ElementPlus, {
    locale: zhCn,
})


app.use(pinia)
app.component('v-chart', ECharts)

app.mount('#app')