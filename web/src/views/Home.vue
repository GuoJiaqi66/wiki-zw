<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
        :style="{ height: '100%', borderRight: 0 }"
        mode="inline"
        @click="handleClick"
        :openKeys="openKeys"
      >
        <a-menu-item key="welcome">
          <MailOutlined /> <!-- 图标 -->
          <span>欢迎</span>
        </a-menu-item>
  
        <a-sub-menu v-for="item in level1" :key="item.id" > <!--:disabled="true"-->
          <template v-slot:title>
            <span><user-outlined />{{item.name}}</span>
          </template>
          <a-menu-item v-for="child in item.children" :key="child.id">
            <MailOutlined /><span>{{child.name}}</span>
          </a-menu-item>
        </a-sub-menu>
        <a-menu-item key="tip" :disabled="true">
          <span>以上菜单在分类管理配置</span>
        </a-menu-item>
      
      </a-menu>
    </a-layout-sider>


    <a-layout-content
            :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >



      <a-list item-layout="vertical" size="large" :grid="{ gutter: 20, column: 3 }" :pagination="pagination" :data-source="ebooks">
        <template #renderItem="{ item }">
          <a-list-item key="item.name">
            <template #actions>
              <span>
                <!-- 小图标 例:收藏数图标-->
                <component v-bind:is="'FileOutlined'" style="margin-right: 8px" />
                {{ item.docCount }}
              </span>
              <span>
                <component v-bind:is="'UserOutlined'" style="margin-right: 8px" />
                {{ item.viewCount }}
              </span>
              <span>
                <component v-bind:is="'LikeOutlined'" style="margin-right: 8px" />
                {{ item.voteCount }}
              </span>
            </template>
            <a-list-item-meta :description="item.description">
              <template #title>
                <router-link :to="'/doc?ebookId=' + item.id">
                  {{ item.name }}
                </router-link>
              </template>
              <!-- 封面 -->
              <template #avatar><a-avatar :src="item.cover"/></template>
            </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>





    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted, createVNode } from 'vue';
import axios from "axios";
import {Tool} from "@/util/tool";

export default defineComponent({
  name: 'Home',
  components: {

  },
  setup() {

    const ebooks = ref();
    ebooks.value = []
    
    const openKeys = ref()
    let categorys: any
    const level1 = ref();
    
    const handleClick = (value: any) => {
        category2Id = value.key
        handleQueryEbook()
    }
    
    const handleQueryCategory = () => {
        axios.get("/category/all").then(resp => {
            const data = resp.data
            if (data.success) {
                categorys = data.content
                
                openKeys.value = []
                for(let i = 0; i < categorys.length; ++i ) {
                    openKeys.value.push = categorys[i]
                }
                
                level1.value = []
                level1.value = Tool.array2Tree(categorys, 0)
            }
        })
    }
    
    let category2Id:number

    const handleQueryEbook = () => {
      axios.get("/ebook/list", {
        params: {
          page: 1,
          size: 1000,
          category2Id: category2Id
        }
      }).then((response) => {
        const data = response.data;
        ebooks.value = data.content.list;
        // ebooks1.books = data.content;
      });
    };

    onMounted(() => {
      handleQueryEbook()
        handleQueryCategory()
    });

    createVNode(() => {
      handleQueryEbook()
    })

    return {
      ebooks,
        openKeys,
      handleQueryEbook,
        handleClick,
        level1
    };
  },
});
</script>
