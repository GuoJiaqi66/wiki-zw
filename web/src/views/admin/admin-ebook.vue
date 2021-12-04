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
                    <a-button type="primary" @click="handleQuery({page: 1, size: pagination.pageSize})">查询</a-button>
                </a-form-item>
                <a-form-item>
                    <a-button type="primary">新增</a-button>
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
                    <span> {{}} </span>
                </template>


            </a-table>

        </a-layout-content>
    </a-layout>
</template>

<script>
    import { ref, onMounted, createVNode } from 'vue'

    import axios from 'axios'

    import { message } from 'ant-design-vue';

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


            const ebooks = ref()
            ebooks.value = []

            // 数据查询
            const handleQuery = (params) => {
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


            const sel = ref()
            sel.value = {
                page: 1,
                size: 10
            }

            onMounted(() => {
                handleQuery(sel)
            })


            return {
                param,
                pagination,
                ebooks,
                handleQuery,
                columns
            }
        }
    }
</script>

<style scoped>

</style>