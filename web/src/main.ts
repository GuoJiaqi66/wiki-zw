import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import './assets/css/normalize.css'

import axios from "axios";

axios.defaults.baseURL = process.env.VUE_APP_SERVER;

// ant design vue
// @ts-ignore
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
// icon图标
import * as Icons from '@ant-design/icons-vue';


const app = createApp(App)

app.use(Antd).use(store).use(router).mount('#app')

// 全局使用图标
const icons: any = Icons;
for (const i in icons) {
    app.component(i, icons[i]);
}



console.log('环境：', process.env.NODE_ENV);
console.log('服务端：', process.env.VUE_APP_SERVER);
