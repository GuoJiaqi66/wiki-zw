<template>
    <a-layout-header class="header">
        <div class="logo" >
          郭佳绮 wiki
        </div>
        <a-menu
                v-model:selectedKeys="selectedKeys1"
                theme="dark"
                mode="horizontal"
                :style="{ lineHeight: '64px' }"
        >
          <!--<a-menu-item key="0">
            <router-link to="/doc">电子书首页</router-link>
          </a-menu-item>-->
            <a-menu-item key="1" :style="user.id? {} : {display:'none'}">
                <router-link to="/admin/ebook">电子书管理</router-link>
            </a-menu-item>
            <a-menu-item key="2" :style="user.id? {} : {display:'none'}">
                <router-link to="/admin/doc">电子书内容管理</router-link>
            </a-menu-item>
            <a-menu-item key="3" :style="user.id? {} : {display:'none'}">
                <router-link to="/admin/category">电子书分类管理</router-link>
            </a-menu-item>
          <a-menu-item key="4" :style="user.id? {} : {display:'none'}">
                <router-link to="/admin/user">用户管理</router-link>
          </a-menu-item>
          
          <div class="position">
            <a-popconfirm
              title="确认退出登录?"
              ok-text="是"
              cancel-text="否"
              @confirm="logout()"
            >
              <a class="login-menu" v-show="user.id">
                <span>退出登录</span>
              </a>
            </a-popconfirm>
            <a class="login-menu" v-show="user.id">
              <span>您好：{{user.name}}</span>
            </a>
            <a class="login-menu" v-show="!user.id" @click="showLoginModal">
              <span>登录</span>
            </a>
          </div>
        </a-menu>
    </a-layout-header>
  
  <a-modal
    title="登录"
    v-model:visible="loginModalVisible"
    @ok="login"
  >
    <a-form :model="loginUser" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="登录名">
        <a-input v-model:value="loginUser.loginName" />
      </a-form-item>
      <a-form-item label="密码">
        <a-input v-model:value="loginUser.password" type="password" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
  import { ref, onMounted, computed } from 'vue'
  import axios from "axios";
  import {message} from "ant-design-vue";
  import store from '@/store'
  
  declare let hexMd5: any;
  declare let KEY: any;
  
    export default {
        name: "the-header",
        setup() {
            const loginModalVisible = ref(false)
            
            const logout = () => {
                axios.get("/user/logout/" + user.value.token).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        message.success("退出登录成功")
                        store.commit("setUser", {})
                    } else {
                        message.error(data.message)
                    }
                })
            }
            
            const showLoginModal = () => {
                //
                loginModalVisible.value = true
            }
            
            const loginUser = ref({
                loginName: "test",
                password: "test"
            })
            const login = () => {
                loginUser.value.password = hexMd5(loginUser.value.password + KEY)
                axios.post("/user/login", loginUser.value).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        message.success("登录请求成功")
                        loginModalVisible.value = false
                        store.commit("setUser", data.content)
                    } else {
                        message.error(data.message)
                    }
                })
                loginModalVisible.value = false
            }
            
            const user = computed(() => store.state.user)
            
            
            
            return {
                logout,
                showLoginModal,
                login,
                
                user,
                loginModalVisible,
                loginUser
              }
        }
    }
</script>

<style scoped>
  .logo {
    width: 120px;
    height: 31px;
    /*background: rgba(255, 255, 255, 0.2);*/
    /*margin: 16px 28px 16px 0;*/
    float: left;
    color: white;
    font-size: 18px;
  }
  .login-menu {
    float: right;
    color: white;
    padding-left: 40px;
  }
  /*.position {
    position: relative;
    right: -500px;
  }*/
  
</style>