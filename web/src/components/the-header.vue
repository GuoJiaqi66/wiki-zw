<template>
    <a-layout-header class="header">
        <div class="logo" ></div>
        <a-menu
                v-model:selectedKeys="selectedKeys1"
                theme="dark"
                mode="horizontal"
                :style="{ lineHeight: '64px' }"
        >
            <a-menu-item key="1">
                <router-link to="/admin/ebook">电子书管理</router-link>
            </a-menu-item>

            <a-menu-item key="2">nav 2</a-menu-item>
            <a-menu-item key="3">nav 3</a-menu-item>
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