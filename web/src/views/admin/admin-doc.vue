<template>
    <a-layout class="layout">
        <a-layout-content>
            <a-row :gutter="24">
                <a-col :span="8">
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
                    <a-table :defaultExpandAllRows="true" :columns="columns" :data-source="level1" bordered pagination="false">
                        <template #action="{ text, record}">
                            <a-space size="small">
                                <a-button type="primary" @click="edit(record)" size="small">
                                    编辑
                                </a-button>
                                <a-popconfirm
                                        title="请再次确定是否删除?"
                                        ok-text="确定"
                                        cancel-text="取消"
                                        @confirm="confirm(text,record)"
                                        @cancel="cancel"
                                >
                                    <a-button type="danger" size="small">
                                        删除
                                    </a-button>
                                </a-popconfirm>
                            </a-space>
                        </template>
                    </a-table>

                </a-col>


                <a-col :span="16">
                    <p>
                        <a-form layout="inline" :model="param">
                            <a-form-item>
                                <a-button type="primary" @click="handleSave()">
                                    保存
                                </a-button>
                            </a-form-item>
                        </a-form>
                    </p>
                    <a-form :model="doc" layout="vertical">
                        <a-form-item label="名称">
                            <a-input v-model:value="doc.name" placeholder="名称"/>
                        </a-form-item>
                        <a-form-item label="父文档">
                            <a-tree-select
                                    v-model:value="doc.parent"
                                    show-search
                                    style="width: 100%; z-index: 5"
                                    :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                                    placeholder="父文档"
                                    allow-clear
                                    tree-default-expand-all
                                    :tree-data="treeSelectData"
                                    :replaceFields="{ title : 'name', key : 'id', value : 'id'}"
                            >
                            </a-tree-select>
                        </a-form-item>
                        <a-form-item label="顺序">
                            <a-input v-model:value="doc.sort" placeholder="顺序"/>
                        </a-form-item>
                        <a-form-item>
                            <a-button type="primary">
                                内容预览
                            </a-button>
                        </a-form-item>
                        <a-form-item label="内容">
                            <div id="content"></div>
                        </a-form-item>
                    </a-form>

                    <p>

                    </p>



                </a-col>
            </a-row>
        </a-layout-content>
    </a-layout>
</template>

<script lang="ts">
    import axios from 'axios'

    import { useRoute } from 'vue-router'

    import  { ref, onMounted, createVNode } from 'vue'
    import {Tool} from "@/util/tool";
    import {message} from "ant-design-vue";

    import E from 'wangeditor'



    export default {
        name: "admin-doc",
        setup: function () {

            const columns = [
                {
                    title: '名称',
                    dataIndex: 'name',
                    slots: {customRender: 'name'},
                    width: "220px"
                },
                {
                    title: 'Action',
                    key: 'action',
                    slots: {customRender: 'action'}
                }
            ]

            /**
             * 将某节点及其子孙节点全部置为disabled
             */
            const setDisable = (treeSelectData: any, id: any) => {
                // console.log(treeSelectData, id);
                // 遍历数组，即遍历某一层节点
                for (let i = 0; i < treeSelectData.length; i++) {
                    const node = treeSelectData[i];
                    if (node.id === id) {
                        // 如果当前节点就是目标节点
                        console.log("disabled", node);
                        // 将目标节点设置为disabled
                        node.disabled = true;

                        // 遍历所有子节点，将所有子节点全部都加上disabled
                        const children = node.children;
                        if (Tool.isNotEmpty(children)) {
                            for (let j = 0; j < children.length; j++) {
                                setDisable(children, children[j].id)
                            }
                        }
                    } else {
                        // 如果当前节点不是目标节点，则到其子节点再找找看。
                        const children = node.children;
                        if (Tool.isNotEmpty(children)) {
                            setDisable(children, id);
                        }
                    }
                }
            };


            const router = useRoute()

            const level1 = ref();


            // 原始数据
            const docs = ref();

            // 编辑，新增 按钮或者
            const doc = ref();
            doc.value = {
                ebookId: router.query.ebookId
            }


            // 父选项数据
            const treeSelectData = ref();
            treeSelectData.value = []


            const handleQuery = () => {
                axios.get("/doc/all/" + router.query.ebookId).then(resp => {
                    console.log("/doc/all请求结果", resp);
                    docs.value = resp.data.content

                    level1.value = Tool.array2Tree(docs.value, 0)

                    console.log("level1", level1.value);
                    console.log("doc", docs.value);

                    // 父文档下拉框初始化，相当于点击新增
                    treeSelectData.value = Tool.copy(level1.value) || [];
                    // 为选择树添加一个"无"
                    treeSelectData.value.unshift({id: 0, name: '无'});

                })
            }

            // 新增
            const add = () => {
                editor.txt.clear()

                doc.value = {
                    ebookId: router.query.ebookId
                }
            }

            // 编辑
            const edit = (record: any) => {
                editor.txt.clear()

                console.log("编辑", record);

                doc.value = Tool.copy(record)

                // 否则将全部被禁用
                treeSelectData.value = Tool.copy(level1.value);
                setDisable(treeSelectData.value, record.id);

                setDisable(treeSelectData.value, record.id)
            }

            const removeDoc = (id:number) => {
                axios.delete("/doc/delete/"+id).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        message.success("删除完毕")

                        handleQuery();

                    } else {
                        message.error(data.message)
                    }
                })
            }

            // action删除按钮的二次确定事件
            const confirm = (e: MouseEvent, record : any) => {
                console.log("确定删除按钮点击事件发生：", e);
                message.success('删除完毕');
                alert(record.id)
                removeDoc(record.id)
            };

            const cancel = (e: MouseEvent) => {
                console.log("取消删除按钮点击事件发生", e);
                message.error('删除取消');
            };


            const handleSave = () => {
                doc.value.content = editor.txt.html()
                axios.post("/doc/save", doc.value).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        message.success("保存成功")
                        doc.value = []
                        handleQuery()
                        doc.value.sort = ""
                        editor.txt.clear()
                    } else {
                        message.error(data.message)
                    }
                })
            }


            let editor: E

            onMounted(() => {
                handleQuery()
                editor = new E("#content")
                editor.config.zIndex = 0
                editor.create()
            })

            createVNode(() => {
                console.log("createVNode", createVNode);
            })

            return {
                level1,
                docs,
                columns,

                edit,
                confirm,
                cancel,

                handleSave,

                doc,
                treeSelectData,

                add,
            }
        }
    }
</script>

<style scoped>

</style>