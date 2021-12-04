<template>
    <a-layout>
        <a-layout-content style="margin-top: 10px; margin-bottom: -80px">
            <p style="margin-top: 10px">
            <a-form
                    layout="inline"
                    :model="param"
            >
                <a-form-item>
                    <a-input v-model:value="param.name" placeholder="名称" />
                </a-form-item>
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
                    :pagination="pagination"
                    :loading="loading"
                    @change="handleTableChange"
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
                                @confirm="confirm"
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

    <a-modal v-model:visible="modalVisible" title="电子书表单" @ok="handleOk">
        <a-form :model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
            <a-form-item label="封面">
                <a-input v-model:value="ebook.cover" />
            </a-form-item>
            <a-form-item label="名称">
                <a-input v-model:value="ebook.name" />
            </a-form-item>
            <a-form-item label="分类">
                <a-cascader :value="categoryIds"
                            :field-name="{ label: 'name', value: 'id', children: 'children'}"
                            :options="level1">

                </a-cascader>
            </a-form-item>
            <a-form-item label="描述">
                <a-input v-model:value="ebook.description" />
            </a-form-item>
        </a-form>
    </a-modal>


    <p>
        {{level1.value}}
    </p>

</template>

<script lang="ts">
    import { ref, onMounted, } from 'vue'

    import axios from 'axios'

    import { message } from 'ant-design-vue';
    import {Tool} from "@/util/tool";

    export default {
        name: "admin-ebook",
        setup() {
            const param = ref();
            param.value = {}
            const pagination = ref({
                current: 1,
                pageSize: 10,
                total: 0
            })

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


            /*
            * 电子书表单
            * */
            const ebook = ref();

            const categoryIds = ref();

            const ebooks = ref()
            ebooks.value = []


            /*
            * 新增 编辑 对话驱动
            * */
            const modalVisible= ref(true);



            // 新增
            const add = () => {
                modalVisible.value = true
                ebook.value = []
            }




            // 数据查询
            const handleQuery = (params: any) => {
                ebooks.value = []
                axios.get("ebook/list", {
                    params: {
                        page: params.page,
                        size: params.size,
                        name: params.name
                    }
                }).then(resp => {
                    const data = resp.data

                    if (data.success) {
                        console.log("数据查询成功：");
                        ebooks.value = data.content.list
                        console.log(ebooks.value);
                        console.log(data)

                        // 重置分组按钮
                        pagination.value.current = params.page
                        pagination.value.total = data.content.total
                    } else {
                        message.error(data.message)
                    }

                }).catch(err => {
                    console.log("查询数据：error");
                    console.log(err);
                })
            }

            /*
            * 查询所有分类
            * */
            const level1 = ref()
            level1.value = []
            let categorys: any;

            const handleQueryCategory = () => {
                handleQuery(sel)
                axios.get("/category/all").then(resp => {
                    const data = resp.data
                    if (data.success) {
                        categorys = data.content
                        console.log("原始数据", categorys);

                        level1.value = []
                        level1.value = Tool.array2Tree(categorys, 0);
                        console.log("树形结构", level1);

                        handleQuery({
                            page: 1,
                            size: pagination.value.pageSize
                        })
                    }
                })
            }

            // 拿到分类
            const getCategoryName = (childrenId : number) => {
                let result = ""
                categorys.forEach((item: any) => {
                    if (item.id === childrenId) {
                        result = item.name;
                    }
                })
                return result;
            }

            const sel = ref()
            sel.value = {
                page: 1,
                size: 100
            }

            onMounted(() => {
                handleQueryCategory()
                console.log("modalVisible", modalVisible.value);
            })

            const handleOk = () => {
                modalVisible.value = false
                ebook.value.category1Id = categoryIds.value[0]
                ebook.value.category2Id = categoryIds.value[1]
                axios.post("/ebook/save", ebook.value).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        // 重新加载列表
                        handleQuery({
                            page: pagination.value.current,
                            size: pagination.value.pageSize
                        })
                    }
                })
            }

            // action编辑
            const edit = (record: any) => {
                console.log(record);
                modalVisible.value = true;
                alert(modalVisible.value)
                categoryIds.value = [ebook.value.category1Id, ebook.value.category2Id]
            }

            // action删除按钮的二次确定事件
            const confirm = (e: MouseEvent) => {
                console.log("确定删除按钮点击事件发生：", e);
                message.success('删除完毕');
            };

            const cancel = (e: MouseEvent) => {
                console.log("取消删除按钮点击事件发生", e);
                message.error('删除取消');
            };


            return {
                param,
                pagination,
                ebooks,
                handleQuery,
                columns,
                getCategoryName,
                categorys,
                confirm,
                cancel,
                edit,
                modalVisible,
                handleOk,
                ebook,
                level1,
                add,
                categoryIds
            }
        }
    }
</script>

<style scoped>

</style>