<template>
    <div style="display: flex;flex-direction: column;gap: 16px">
    <div style="display: flex;gap: 16px">
      <el-input  style="width: 240px" placeholder="请输入您要搜索的内容">
        <template #prefix>
          <el-icon class="el-input__icon"><search /></el-icon>
        </template>
      </el-input>
      <el-button type="primary" color="#a0cfff" >搜索</el-button>
      <el-button type="success">新增</el-button>

    </div>
  <el-table :data="tableData" style="width: 100%;text-align: center">
    <el-table-column fixed prop="date" label="Date" />
    <el-table-column prop="name" label="Name"/>
    <el-table-column prop="state" label="State" />
    <el-table-column prop="address" label="Address"  />
    <el-table-column prop="zip" label="Zip"  />
    <el-table-column fixed="right" label="Operations">
      <template #default>
        <el-button style="width: 50px;" type="danger" >删除</el-button>
        <el-button style="width: 50px;" type="success">编辑</el-button>
        <el-button style="width: 50px;" type="warning" @click="openAddPanel()">详情</el-button>
      </template>
    </el-table-column>
  </el-table>
  <!--    详情弹窗-->
  <el-dialog v-model="addVisible" title="设备详情" width="500">
      
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addVisible = false">取消</el-button>
          <el-button type="primary" >
            Confirm
          </el-button>
        </div>
      </template>
    </el-dialog>
</div>
</template>

<script lang="ts" setup>
import {Delete, Edit} from "@element-plus/icons-vue";
import {ref} from 'vue'
const addVisible = ref(false)
// 打开新增面板

const openAddPanel = () =>{
  addVisible.value=true
}
const handleClick = () => {
  console.log('click')
}

const tableData = [
  {
    date: '2016-05-03',
    name: 'Tom',
    state: 'California',
    city: 'Los Angeles',
    address: 'No. 189, Grove St, Los Angeles',
    zip: 'CA 90036',
    tag: 'Home',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    state: 'California',
    city: 'Los Angeles',
    address: 'No. 189, Grove St, Los Angeles',
    zip: 'CA 90036',
    tag: 'Office',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    state: 'California',
    city: 'Los Angeles',
    address: 'No. 189, Grove St, Los Angeles',
    zip: 'CA 90036',
    tag: 'Home',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    state: 'California',
    city: 'Los Angeles',
    address: 'No. 189, Grove St, Los Angeles',
    zip: 'CA 90036',
    tag: 'Office',
  },
]
</script>
