<template>
    <a-layout>
        <a-layout-content style="padding: 0 50px">
            <p>
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


            createVNode(() => {
                handleQuery
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