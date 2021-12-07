<template>
  <a-layout>
    <a-layout-content
      :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-input v-model:value="param.loginName" placeholder="登陆名">
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="handleQueryQuest({page: 1, size: pagination.pageSize})">
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
  
      <a-table :columns="columns" :data-source="users" :row-key="record => record.id" :pagination="pagination">
        <template #action="{ text, record }">
          <a-button type="primary" @click="resetPassword(record)">
            重置密码
          </a-button>
          <a-button type="primary" @click="edit(record)">
            编辑
          </a-button>
          <a-popconfirm
            title="删除后不可恢复，确认删除?"
            ok-text="是"
            cancel-text="否"
            @confirm="handleDeleteReq(record.id)"
          >
            <a-button type="danger" >
              删除
            </a-button>
          </a-popconfirm>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
  
  <a-modal
    title="用户表单"
    v-model:visible="modalVisible"
    @ok="handleModalOk"
  >
    <a-form :model="user" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="登陆名">
        <a-input v-model:value="user.loginName" :disabled="!!user.id"/>
      </a-form-item>
      <a-form-item label="昵称">
        <a-input v-model:value="user.name" />
      </a-form-item>
      <a-form-item label="密码" v-show="!user.id">
        <a-input v-model:value="user.password"/>
      </a-form-item>
    </a-form>
  </a-modal>
  
  <a-modal
    title="重置密码"
    v-model:visible="resetModalVisible"
    @ok="handleResetModalOk"
  >
    <a-form :model="user" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="新密码">
        <a-input v-model:value="user.password"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
  import { ref, onMounted } from 'vue'
  import axios from "axios"
  import {message} from "ant-design-vue";
  import {Tool} from "@/util/tool";
  

  declare let hexMd5: any;
  declare let KEY: any;
  
    export default {
        name: "admin-user",
        setup: function () {
            const columns = [
                {
                    title: '登陆名',
                    dataIndex: 'loginName'
                },
                {
                    title: '名称',
                    dataIndex: 'name'
                },
                {
                    title: '密码',
                    dataIndex: 'password'
                },
                {
                    title: 'Action',
                    key: 'action',
                    slots: {customRender: 'action'}
                }
            ]
            const pagination = ref({
                current: 1,
                pageSize: 10,
                total: 0
            })
            
            const modalVisible = ref(false)
            const resetModalVisible = ref(false)
            const param = ref({});
            param.value = {
                loginName: ""
            }
            
            const user = ref()

            const users = ref();

            const handleQuery = (param: any) => {
                axios.get("/user/list", {
                    params: {
                        page: param.page,
                        size: param.size,
                        // loginName: param.value.loginName
                    }
                }).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        users.value = data.content.list
                        console.log("users:", users);

                        pagination.value.current = param.page;
                        pagination.value.total = data.content.total;
                    }

                })
            }

            onMounted(() => {
                handleQuery({
                    page: 1,
                    size: pagination.value.pageSize,
                })
            })

            const add = () => {
                user.value = {}
                modalVisible.value = true
            }

            const handleDelete = () => {
                alert("执行删除")
            }

            // 查询
            const handleQueryQuest = (req: any) => {
                handleQuery(req)
            }
            
            const handleModalOk = () => {
                user.value.password = hexMd5(user.value.password + KEY);
                axios.post("/user/save", user.value).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        message.success("添加用户成功")
                        handleQuery(pagination)
                        user.value = {}
                    } else {
                        message.success(data.message)
                    }
                })
                modalVisible.value = false
            }

            //
            const edit = (record:any) => {
                user.value = Tool.copy(record)
                modalVisible.value = true
            }
            
            const resetPassword = (record: any) => {
                user.value = Tool.copy(record)
                resetModalVisible.value = true
            }

            const handleResetModalOk = () => {
                user.value.password = hexMd5(user.value.password, KEY)
                axios.post("/user/reset-password", user.value).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        user.value = {}
                        resetModalVisible.value = false;

                        // 重新加载列表
                        handleQuery({
                            page: pagination.value.current,
                            size: pagination.value.pageSize,
                        });
                    } else {
                        message.error(data.message)
                    }
                })
            }
            
            const handleDeleteReq = (id : any) => {
                axios.delete("/user/delete/" + id).then(resp => {
                    const data = resp.data
                    if (data.success) {
                        message.success("用户删除成功")
                        handleQuery({
                            page: pagination.value.current,
                            size: pagination.value.pageSize,
                        });
                    } else {
                        message.error(data.message)
                    }
                })
            }

            return {
                columns,
                users,
                param,
                pagination,
                user,
                modalVisible,
                resetModalVisible,

                add,
                handleDelete,
                handleQueryQuest,
                handleModalOk,
                edit,
                resetPassword,
                handleResetModalOk,
                handleDeleteReq
            }
        }
    }
</script>

<style scoped>

</style>