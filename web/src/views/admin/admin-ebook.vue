<template>
    <a-layout>
        <a-layout-content style="margin-top: 10px; margin-bottom: -80px">
            <p style="margin-top: 10px">
            <a-form
                    layout="inline"
            >
                <a-form-item>
                    <a-button type="primary" @click="handleQuery({page: 1, size: pagination.pageSize})">
                        查询
                    </a-button>
                </a-form-item>
                <a-form-item>
                    <a-button type="primary" @click="add()">
                        新增
                    </a-button>
                </a-form-item>
            </a-form>
            </p>

            <a-table
                    :columns="columns"
                    :row-key="record => record.id"
                    :data-source="ebooks"
                    :pagination="false"
            >
                <template #cover="{ text: cover }">
                    <img :src="cover" alt="">
                </template>
                <template v-slot:category="{ text, record }">
                    <span> {{ getCategoryName(record.category1Id) }} / {{ getCategoryName(record.category2Id) }} </span>
                </template>
                <template #action="{ text, record }">
                    <a-space sixe="small">
                        <router-link :to="'/admin/doc?ebookId=' + record.id">
                            <a-button type="primary">
                                文档管理
                            </a-button>
                        </router-link>
                        <a-button type="primary" @click="edit(record)">
                            编辑
                        </a-button>


                        <a-popconfirm
                                title="请再次确定是否删除?"
                                ok-text="确定"
                                cancel-text="取消"
                                @confirm="deleteEbook(record.id)"
                                @cancel="cancel"
                        >
                            <a-button type="danger">
                                删除
                            </a-button>
                        </a-popconfirm>
                    </a-space>
                </template>
            </a-table>
        </a-layout-content>

    </a-layout>

  <p>存在bug：存没问题，删除存在问题，因为id</p>
  
    <a-modal v-model:visible="modalVisible" title="电子书表单" @ok="handleOk">
        <a-form v-model:model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
            <a-form-item label="封面">
                <a-input v-model:value="ebook.cover" />
            </a-form-item>
            <a-form-item label="名称">
                <a-input v-model:value="ebook.name" />
            </a-form-item>
            <a-form-item label="分类">
                <a-cascader
                  v-model:value="categoryIds"
                  :fieldNames="{ label: 'name', value: 'id', children: 'children' }"
                  :options="level1"/>
              
            </a-form-item>
            <a-form-item label="描述">
                <a-input v-model:value="ebook.description" />
            </a-form-item>
        </a-form>
    </a-modal>


</template>

<script lang="ts">
    import { ref, onMounted, } from 'vue'

    import axios from 'axios'

    import { message } from 'ant-design-vue';
    import {Tool} from "@/util/tool";
    

    export default {
        name: "admin-ebook",
        setup() {

            const columns =[
                {
                    title: '封面',
                    dataIndex: 'cover',
                    slots: { customRender: 'cover' }
                },
                {
                    title: '名称',
                    dataIndex: 'name',
                },
                {
                    title: '分类',
                    slots: { customRender: 'category' }
                },
                {
                    title: '文档数',
                    dataIndex: 'docCount'
                },
                {
                    title: '阅读数',
                    dataIndex: 'viewCount'
                },
                {
                    title: '点赞数',
                    dataIndex: 'voteCount'
                },
                {
                    title: 'Action',
                    key: 'action',
                    slots: { customRender: 'action' }
                }
            ]

            interface Option {
                value: number;
                label: string;
                children?: Option[];
            }
            
            // 新增
            const saveEbook = () => {
                console.log("ebook值(saveEbook)：", ebook.value.name);
                ebook.value.category1Id = categoryIds.value[0]
                ebook.value.category2Id = categoryIds.value[1]
                axios.post("/ebook/save", ebook.value).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        message.success("添加电子书成功")
                        handleQueryCategory()
                        ebook.value = {}
                    }
                })
                
            }
            
            // 删除
            const deleteEbook = (id: number) => {
                axios.delete("/ebook/delete/" + id).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        message.success("电子书删除完毕")
                        handleQueryCategory()
                    } else {
                        message.error("电子书删除出错")
                    }
                })
            }
            
            const pagination = ref({
                // 当前页
                current: 1,
                pageSize: 10,
                total: 0
            })
            
            const modalVisible = ref(false)

            const ebooks = ref()
            
            // 查询ebook
            const handleQuery = () => {
                axios.get("/ebook/list", {
                    params: {
                        page: pagination.value.current,
                        size: pagination.value.pageSize
                    }
                }).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        message.success("电子书查询成功")
                        ebooks.value = data.content.list
                    } else {
                        message.error(data.message)
                    }
                })
            }
            
            // 查询category
            let categorys: any
            const level1 = ref()
            const handleQueryCategory = () => {
                axios.get("/category/all").then(resp => {
                    const data = resp.data
                    if (data.success) {
                        message.success("分类查询成功")
                        handleQuery()
                        categorys = data.content
                        
                        level1.value = {}
                        level1.value = Tool.array2Tree(categorys, 0)
                        console.log("category:", categorys);
                    }
                })
            }
            
            const ebook = ref()
            
            // 新增
            const add = () => {
                ebook.value = {}
                modalVisible.value = true
                console.log("ebook值(add)：", ebook.value);
                saveEbook()
            }
           const categoryIds =ref()
            // 分类名处理
            const getCategoryName = (cid : number) => {
              let result = ""
                categorys.forEach((item: any) => {
                  if (item.id === cid) {
                      result = item.name
                  }
              })
                return result
            }
            
            // 编辑按钮
            const edit = (record: any) => {
                modalVisible.value = true
                ebook.value = Tool.copy(record)
                categoryIds.value = [ebook.value.category1Id, ebook.value.category2Id]
                console.log("ebook值：", ebook.value);
            }

            // action删除按钮的二次确定事件
            /*const confirm = (e: MouseEvent, record : any) => {
                console.log("确定删除按钮点击事件发生：", e);
                alert(record.id)
                console.log(record);

                deleteEbook(record.id)
            };*/

            const cancel = (e: MouseEvent) => {
                console.log("取消删除按钮点击事件发生", e);
                message.error('删除取消');
            };
            
            onMounted(() => {
                handleQueryCategory()
            })
            
            // 编辑，新增提交
            const handleOk =() => {
                modalVisible.value = false
                saveEbook()
            }
            
            
            
            
            return {
                modalVisible,
                ebooks,
                columns,
                categorys,
                ebook,
                level1,
                categoryIds,
                
                deleteEbook,

                handleQueryCategory,
                handleQuery,
                add,
                getCategoryName,
                edit,
                // confirm,
                cancel,
                handleOk
            }
        }
    }
</script>

<style scoped>

</style>