<template>
    <a-layout-header class="header">
        <div class="logo" ></div>
        <a-menu
                v-model:selectedKeys="selectedKeys1"
                theme="dark"
                mode="horizontal"
                :style="{ lineHeight: '64px' }"
        >
          <a-menu-item key="0">
            <router-link to="/doc">电子书首页</router-link>
          </a-menu-item>
            <a-menu-item key="1">
                <router-link to="/admin/ebook">电子书管理</router-link>
            </a-menu-item>
            <a-menu-item key="2">
                <router-link to="/admin/doc">电子书内容管理</router-link>
            </a-menu-item>
            <a-menu-item key="3">
                <router-link to="/admin/category">电子书分类管理</router-link>
            </a-menu-item>
          <a-menu-item key="4">
                <router-link to="/admin/user">用户管理</router-link>
            </a-menu-item>
        </a-menu>
    </a-layout-header>
</template>

<script>
    export default {
        name: "the-header"
    }
</script>

<style scoped>

</style>