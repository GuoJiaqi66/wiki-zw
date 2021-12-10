<template>
  <a-layout class="layout">
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
      <h3>点赞bug：传进去的id与数据库id不匹配</h3>
      <a-row type="flex" justify="space-around">
        <a-col :span="6">
          <a-tree
            class="draggable-tree"
            block-node
            @select="onSelect"
            :tree-data="level1"
            :replaceFields="{title: 'name', key: 'id', value: 'id'}"
            :defaultExpandAll="true"
          >
          </a-tree>
        </a-col>
        <a-col :span="18">
          <div>
            <h2>{{doc.name}}</h2>
            <div>
              <span>阅读数：{{doc.viewCount}}</span>
              <span>点赞数：{{doc.voteCount}}</span>
              <a-divider style="height: 2px; background-color: #9999cc" />
            </div>
          </div>
          <div class="wangEditor" :innerHTML="html"></div>
  
          <div class="vote-div">
            <a-button type="primary" shape="round" :size="'large'" @click="vote">
              <template #icon><LikeOutlined /> &nbsp;点赞：{{doc.voteCount}} </template>
            </a-button>
          </div>
        </a-col>
      </a-row>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
  import { ref, onMounted } from 'vue'
  import {useRoute} from "vue-router";
  import axios from "axios";
  import {Tool} from "@/util/tool";
  import E from 'wangeditor'
  import {message} from "ant-design-vue";
  
    export default {
        name: "doc",
        setup() {
            // 路由地址
            const router = useRoute()
            
            const onSelect = (selectedKeys : any, info : any) => {
                if (Tool.isNotEmpty(selectedKeys)) {
                    // 节点信息
                    doc.value = info.selectedNodes[0].props
                    // 节点content
                    handleQueryContent(selectedKeys[0])
                }
            }
            
            //
            const level1 = ref()
            level1.value = []
            const html = ref()
            const doc = ref()
            doc.value = {}
            
            // 查询doc 数据
            const handleQuery = () => {
                axios.get("/doc/all/" + router.query.ebookId).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        const docVal = data.content


                        level1.value = Tool.array2Tree(docVal, 0)


                        
                        if (Tool.isNotEmpty(level1)) {
                            handleQueryContent(level1.value[0].id)
                            doc.value = level1.value[0]
                        }
                    }
                })
            }
            
            // 查询content 数据
            const handleQueryContent = (id: any) => {
                axios.get("/doc/find-content/" + id).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        html.value = data.content
                    }
                })
            }
            
            onMounted(() => {
                handleQuery()
            })
            
            // 点赞
            const vote = () => {
                axios.get("/doc/vote/" + doc.value.id).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        doc.value.voteCount++
                    } else {
                        message.error(data.message)
                    }
                })
            }
            
            return {
                onSelect,
                vote,
                
                level1,
                html,
                doc
            }
        }
    }
</script>

<style scoped>
  
  .wangeditor table {
    border-top: 1px solid #ccc;
    border-left: 1px solid #ccc;
  }
  .wangeditor table td,
  .wangeditor table th {
    border-bottom: 1px solid #ccc;
    border-right: 1px solid #ccc;
    padding: 3px 5px;
  }
  .wangeditor table th {
    border-bottom: 2px solid #ccc;
    text-align: center;
  }
  
  /* blockquote 样式 */
  .wangeditor blockquote {
    display: block;
    border-left: 8px solid #d0e5f2;
    padding: 5px 10px;
    margin: 10px 0;
    line-height: 1.4;
    font-size: 100%;
    background-color: #f1f1f1;
  }
  
  /* code 样式 */
  .wangeditor code {
    display: inline-block;
    *display: inline;
    *zoom: 1;
    background-color: #f1f1f1;
    border-radius: 3px;
    padding: 3px 5px;
    margin: 0 3px;
  }
  .wangeditor pre code {
    display: block;
  }
  
  /* ul ol 样式 */
  .wangeditor ul, ol {
    margin: 10px 0 10px 20px;
  }

  /* 点赞 */
  .vote-div {
    padding: 15px;
    text-align: center;
  }
</style>