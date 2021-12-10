<template>
    <a-layout>
        <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
            <p>
                <a-form layout="inline" :model="param">
                    <a-form-item>
                        <a-button type="primary" @click="handleQuery()">
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

            <p>
                <a-alert
                        class="tip"
                        message="小提示：这里的分类会显示到首页的侧边菜单"
                        type="info"
                        closable
                />
            </p>

            <a-table defaultExpandAllRows="true" :columns="columns" :data-source="level1" :pagination="false">
                <template #action="{ record, text }">
                    <a-space size="small">
                        <a-button type="primary" @click="edit(record)">
                            编辑
                        </a-button>
                        <a-popconfirm
                                title="删除后不可恢复，确认删除?"
                                ok-text="是"
                                cancel-text="否"
                                @confirm="handleDelete(record.id)"
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


    <a-modal :visible="visibleVal" @cancel="handleCancel()" title="分类表单" @ok="handleOk()">
        <a-form :model="category" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }">
            <a-form-item label="名称">
                <a-input v-model:value="category.name" />
            </a-form-item>
            <a-form-item label="父分类">
                <a-select
                        v-model:value="category.parent"
                        size="default"
                        style="width: 275.33px"
                >
                    <a-select-option :value="0" >
                        无
                    </a-select-option>
                    <a-select-option v-for="c in level1" :key="c.id" :value="c.id" :disabled="category.id === c.id">
                        {{c.name}}
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="排序">
                <a-input v-model:value="category.sort" />
            </a-form-item>
        </a-form>
    </a-modal>

</template>

<script lang="ts">
    import { ref, onMounted } from 'vue'
    import axios from "axios";
    import {message} from "ant-design-vue";
    import {Tool} from "@/util/tool";

    export default {
        name: "admin-category",

        setup() {
            const columns = [
                {
                    title: "名称",
                    dataIndex: "name"
                },
                {
                    title: "顺序",
                    dataIndex: "sort"
                },
                {
                    title: "Action",
                    key: 'action',
                    slots: { customRender: 'action' }
                }
            ]

            const level1 = ref();
            const categoryVal = ref();

            const visibleVal = ref(false);
            const handleOk = (e: MouseEvent) => {
                visibleVal.value = false
                add()
            };
            const handleCancel = () => {
                visibleVal.value = false
            }

            // 表单数据
            const category = ref({})


            // 查询所有数据
            const handleQuery = () => {
                level1.value = []
                axios.get("/category/all").then(resp => {
                    const data = resp.data
                    if (data.success) {
                        categoryVal.value = data.content
                        level1.value = []
                        level1.value = Tool.array2Tree(categoryVal.value, 0)

                    }
                })
            }

            // 添加数据
            const add = () => {
                visibleVal.value = true
                axios.post("/category/save", category.value).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        message.success("分类添加成功")
                        category.value = {}
                        handleQuery()
                    } else {
                        message.error(data.message)
                    }
                })
            }

            // 删除行数据
            const handleDelete = (id : number) => {
                axios.delete("category/delete/" + id).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        message.success("分类删除成功")
                        handleQuery()
                    } else {
                        message.error(data.message)
                    }
                })
            }

            // 编辑按钮
            const edit = (record: any) => {
                visibleVal.value = true
                category.value = Tool.copy(record)
            }



            onMounted(() => {
                handleQuery()
            })

            return {
                categoryVal,
                level1,
                columns,
                visibleVal,
                category,


                handleQuery,
                handleDelete,
                add,
                edit,
                handleOk,
                handleCancel
            }
        }
    }
</script>

<style scoped>

</style>