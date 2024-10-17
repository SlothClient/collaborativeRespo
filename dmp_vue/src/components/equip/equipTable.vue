<template>
    <el-table :data="tableData" style="width: 100%;text-align: center">
      <el-table-column type="selection" width="28"></el-table-column>
      <el-table-column prop="equipPic" label="设备照片">
        <template #default="scope">
          <el-image
              style="width: 70px; height: 100px"
              :src="scope.row.equipPic"
              fit="fill"
          />
        </template>
      </el-table-column>
      <el-table-column prop="equipName" label="设备名称" />
      <el-table-column prop="statusName" label="状态"  />
      <el-table-column prop="departmentName" label="部门"  />
      <el-table-column prop="typeName" label="类型"  />
      <el-table-column prop="siteName" label="位置"  /> -->
      <el-table-column prop="remarks" label="备注"  />
      <el-table-column prop="fixBig" label="大修周期"  />
      <el-table-column prop="fixMedium" label="中修周期"  />
      <el-table-column prop="fixSmall" label="小修周期"  />
      <el-table-column prop="lastMaintance" label="最后保养日期"  />
      <el-table-column prop="purchaseDate" label="购置日期"  />
      <el-table-column prop="validTime" label="有效期限"  />
      <el-table-column prop="countBig" label="大修次数"  />
      <el-table-column prop="countMedium" label="中修次数"  />
      <el-table-column prop="countSmall" label="小修次数"  />
      <el-table-column min-width="150" fixed="right" label="操作">
        <template #default="scope">
          <el-popconfirm title="确定删除吗?" @confirm="deleteRow(scope.row.equipId)" confirm-button-text="确定" cancel-button-text="取消">
            <template #reference>
              <el-button size="mini" color="rgb(248, 152.1, 152.1)" style="width: 50px;" type="danger"><el-icon><Delete /></el-icon>删除</el-button>
            </template>
          </el-popconfirm>
           <el-button size="mini" color="rgb(159.5, 206.5, 255)" style="width: 50px;" type="success" @click="handleEdit(scope.row)"><el-icon><Edit /></el-icon>编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </template>
  
  <script setup>
  import { Delete, Edit } from "@element-plus/icons-vue";
  import { ref } from 'vue';
  
  const props = defineProps({
    tableData: Array,
  });
  
  const emit = defineEmits(['edit', 'delete']);
  
  const handleEdit = (row) => {
    emit('edit', row);
  };
  
  const deleteRow = (id) => {
    emit('delete', id);
  };
  </script>
  
  <style scoped>
  /* 样式可以根据需要进行调整 */
  </style>