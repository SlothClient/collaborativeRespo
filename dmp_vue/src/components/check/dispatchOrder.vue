<template>
  <el-dialog
      v-model="dialogVisible"
      :before-close="closeDialog"
      title="计划派单"
      width="600px"
      center
  >
    <el-form label-width="120px">
      <el-form-item label="请选择工人">
        <el-select
            v-model="selectedWorker"
            placeholder="请选择工人"
            aria-label="工人"
            clearable
        >
          <el-option
              v-for="worker in workers"
              :key="worker.workerId"
              :label="worker.workerName"
              :value="worker.workerId"
          />
        </el-select>
      </el-form-item>
    </el-form>

    <el-table :data="tableData" border style="width: 100%; margin-bottom: 20px">
      <el-table-column prop="checkName" label="计划名" align="center" />
      <el-table-column prop="equipName" label="设备" align="center" />
      <el-table-column label="工单描述" align="center">
        <template #default="scope">
          <el-input
              v-model="scope.row.orderDesc"
              placeholder="请输入工单描述"
              clearable
          />
        </template>
      </el-table-column>
      <el-table-column align="center" label="移除">
        <template #default="scope">
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="deleteRow(scope.$index, scope.row.checkId)"
              icon="Delete"
          >
            Remove
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-button class="mt-4" style="width: 100%" @click="onAddItem">
      <el-icon>
        <CirclePlus />
      </el-icon>
      添加计划
    </el-button>

    <div class="dialog-footer">
      <el-button @click="closeDialog">取消</el-button>
      <el-button type="primary" @click="confirm">确定</el-button>
    </div>
  </el-dialog>
</template>
<script setup>
import {computed, ref, watch} from "vue";
import {useEquipmentInfoStore} from "@/store/module/equipmentInfo.js";
import {ElNotification} from "element-plus";
import moment from "moment";

const props = defineProps({
  visible: {
    type: Boolean,
    default: false,
  },
  selectedPlan: {
    type: Array,
    default: () => [],
  },
});
const emits = defineEmits(['closeDialog', 'dispatchOrder', 'removeSelectedPlan']);
const dialogVisible = ref(props.visible);
// 监听外部传入的 `visible`，控制 dialog 的显示状态
watch(() => props.visible, (newVal) => {
  dialogVisible.value = newVal;
});
const equipInfoStore = useEquipmentInfoStore();
const workers = equipInfoStore.workerInfo;
const equipmentInfo = equipInfoStore.equipmentInfo;

// 计算属性：当 props.selectedPlan 改变时，生成表格数据
const tableData = computed(() => {
  return props.selectedPlan.map((item) => ({
    ...item,
    equipName: equipmentInfo.find((equip) => equip.equipId === item.equipId)?.equipName || '',
    orderDesc: item.orderDesc || '', // 保证 orderDesc 为空时有默认值
  }));
});

const selectedWorker = ref(null);

const closeDialog = () => {
  dialogVisible.value = false;
  emits('closeDialog');
};
// 确认派单
const confirm = () => {
  // 检查是否有选择工人
  if (!selectedWorker.value) {
    ElNotification({
      type: 'error',
      message: '未选择工人',
    });
    return;
  }
  // 检查工单描述是否为空
  const hasEmptyOrderDesc = tableData.value.some((item) => !item.orderDesc.trim());
  if (hasEmptyOrderDesc) {
    ElNotification({
      type: 'error',
      message: '描述不能为空',
    });
    return;
  }
  // 构建请求参数
  const param = tableData.value.map((item) => ({
    checkId: item.checkId,
    equipId: item.equipId,
    workerId: selectedWorker.value,
    orderDesc: item.orderDesc,
    deliverTime: moment().format('YYYY/MM/DD HH:mm:ss'),
  }));
  if (param.length === 0) {
    ElNotification({
      type: 'error',
      message: '未选择保养计划',
    });
    return;
  }

  // 触发派单事件
  emits('dispatchOrder', param);
  closeDialog();
};
// 删除行
const deleteRow = (index, checkId) => {
  emits('removeSelectedPlan', index, checkId);
};
const onAddItem = () => {
  console.log('添加计划');
};
</script>
<style scoped>
.el-dialog__header {
  background-color: #f5f7fa;
  border-bottom: 1px solid #ebeef5;
  text-align: center;
  font-weight: bold;
  padding: 15px 0;
}

.el-form {
  padding: 10px 20px;
}

.el-table {
  margin-top: 20px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.el-input {
  width: 100%;
}

.dialog-footer {
  text-align: right;
  padding: 10px 20px;
  border-top: 1px solid #ebeef5;
}

.dialog-footer .el-button {
  margin-right: 10px;
}
</style>