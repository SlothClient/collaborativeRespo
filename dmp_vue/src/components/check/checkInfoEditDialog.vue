<template>
  <div id="detailDialog">
    <el-dialog v-model="dialogVisible" @close="closeDialog" title="详情" width="800" align-center>
      <div id="separate"></div>
      <div id="orderInfo">
        <div class="formTitle">点巡检计划基本信息</div>
        <div id="orderForm">
          <el-form :inline="true" class="demo-form-inline" >
            <el-form-item label="计划编号">
              <el-input v-model="checkInfoDetail.checkId" clearable />
            </el-form-item>
            <el-form-item label="计划名称">
              <el-input v-model="checkInfoDetail.checkName" clearable />
            </el-form-item>
            <el-form-item label="开始时间">
              <el-date-picker v-model="checkInfoDetail.startTime" type="datetime" clearable />
            </el-form-item>
            <el-form-item label="结束时间">
              <el-date-picker v-model="checkInfoDetail.endTime" type="datetime" clearable />
            </el-form-item>
            <el-form-item label="点巡检描述">
              <el-input v-model="checkInfoDetail.checkDesc" type="textarea" style="width: 540px;" clearable />
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div id="equipTable">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="checkitemName" label="检查项名称" align="center">
            <template #default="scope">
              <el-input v-model="scope.row.checkitemName" clearable />
            </template>
          </el-table-column>
          <el-table-column prop="checkitemWay" label="检查项方法" align="center">
            <template #default="scope">
              <el-input v-model="scope.row.checkitemWay" clearable />
            </template>
          </el-table-column>
          <el-table-column prop="checkitemOut" label="检查项结果" align="center">
            <template #default="scope">
              <el-input v-model="scope.row.checkitemOut" clearable />
            </template>
          </el-table-column>
<!--          <el-table-column prop="checkitemTime" label="检查时间" align="center">-->
<!--            <template #default="scope">-->
<!--              <el-date-picker-->
<!--                  v-model="scope.row.checkitemTime"-->
<!--                  type="datetime"-->
<!--                  placeholder="选择日期时间"-->
<!--                  clearable-->
<!--              />-->
<!--            </template>-->
<!--          </el-table-column>-->
          <el-table-column label="操作" align="center">
            <template #header>
              <el-button link size="mini" @click="addRow">
                <el-icon><CirclePlus /></el-icon>添加
              </el-button>
            </template>
            <template #default="scope">
              <el-popconfirm title="确定删除吗?" @confirm="deleteRow(scope.row)">
                <template #reference>
                  <el-button link size="mini" color="rgb(159.5, 206.5, 255)" style="width: 50px;" type="danger">
                    <el-icon><Delete /></el-icon>删除
                  </el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="closeDialog">取消</el-button>
          <el-button type="primary" @click="confirmEdit">确认</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, watch, onMounted } from "vue";
import {createCheckitem, deleteCheckitem, getCheckitem, updateCheckitem} from "@/api/checkitem/index.js";
import axios from 'axios';
import moment from 'moment';

const props = defineProps({
  checkInfoEditVisible: Boolean,
  checkInfoDetail: Object
});

const dialogVisible = ref(props.checkInfoEditVisible);
const emit = defineEmits(['closeDialog', 'editCheckInfo']);
const tableData = ref([]);
const deletedItems = ref([]); // 用于记录被删除项的 ID
watch(
    () => props.checkInfoEditVisible,
    (newValue) => {
      dialogVisible.value = newValue;
    }
);

// 获取当前时间的方法
const getNowFormatDateTime = () => {
  return moment(new Date()).format('YYYY/MM/DD HH:mm:ss');
};

const addRow = () => {
  const newRow = {
    checkitemId: Date.now(),
    checkitemName: '',
    checkitemWay: '',
    checkitemOut: '',
    checkitemTime: '', // 初始化为空字符串或当前时间
    operation: 'new' // 新增操作
  };
  tableData.value.push(newRow);
};

const deleteRow = (row) => {
  const index = tableData.value.findIndex(item => item.checkitemId === row.checkitemId);
  if (index !== -1) {
    // 记录被删除项的 ID
    deletedItems.value.push(tableData.value[index].checkitemId);
    // 从数组中移除
    tableData.value.splice(index, 1);
  }
};

const getList = async (val) => {
  if (!val) return;
  const res = await getCheckitem(val);
  tableData.value = res.data.data.map(item => ({
    ...item,
    operation: 'update' // 初始加载时，所有项都标记为更新操作
  }));
};

watch(() => props.checkInfoDetail, async (newVal) => {
  if (newVal && newVal.checkId) {
    await getList(newVal.checkId);
  } else {
    tableData.value = [];
  }
});

const closeDialog = () => {
  dialogVisible.value = false;
  emit('closeDialog');
};

onMounted(async () => {
  if (props.checkInfoDetail && props.checkInfoDetail.checkId) {
    await getList(props.checkInfoDetail.checkId);
  }
});

const confirmEdit = async () => {
  const now = getNowFormatDateTime(); // 获取当前时间
  const checkPlanId = props.checkInfoDetail.checkId; // 获取点巡检计划的ID

  // 收集新增和更新的数据，并添加点巡检计划的ID和当前时间
  const newData = tableData.value
      .filter(item => item.operation === 'new')
      .map(item => ({
        ...item,
        checkId: checkPlanId,
        checkitemTime: now
      }));

  // 移除 operation 字段
  newData.forEach(item => delete item.operation);

  const updateData = tableData.value
      .filter(item => item.operation === 'update')
      .map(item => ({
        ...item,
        checkId: checkPlanId,
        checkitemTime: now // 假设更新操作需要更新时间
      }));

  // 移除 operation 字段
  updateData.forEach(item => delete item.operation);


  // 收集删除的数据，只收集检查项的 ID
  const deletedData = deletedItems.value;
  try {
    // 发送新增请求
    if (newData.length) {
      await createCheckitem(newData)
      //axios.post('/api/checkitem/create', newData);

    }

    // 发送更新请求
    if (updateData.length) {
      await updateCheckitem(updateData)
          //axios.post('/api/checkitem/update', updateData);
    }

    // 发送删除请求
    if (deletedData.length) {
      await deleteCheckitem(deletedData)
          //axios.post('/api/checkitem/delete', deletedData);
    }

    alert('操作成功');
    closeDialog();
  } catch (error) {
    console.error('操作失败:', error);
    alert('操作失败');
  }
};

watch(tableData, (newVal) => {
  newVal.forEach((row) => {
    if (row.checkitemId && row.operation !== 'new' && row.operation !== 'delete') {
      row.operation = 'update';
    }
  });
}, { deep: true });
</script>
<style scoped>
.demo-form-inline .el-input {
  --el-input-width: 220px;
}

.demo-form-inline .el-select {
  --el-select-width: 220px;
}

/* 样式调整 */

.formTitle {
  height: 26px;
  line-height: 26px;
  padding: 0 10px;
  border-left: 4px solid #409eff;
  margin-bottom: 15px;
}

.demo-form-inline {
  text-align: center;
}

/* 分割线 */
#separate {
  position: absolute;
  left: 0;
  top: 48px;
  width: 100%;
  border-bottom: 1px solid #ccc;
}

/* 调整表头为淡灰色并添加盒阴影 */
.el-table .el-table__header th {
  background-color: #000 !important;
  /* 调不到 */
}

#equipTable {
  box-shadow: 1px 3px 3px 1px #eee;
}
</style>