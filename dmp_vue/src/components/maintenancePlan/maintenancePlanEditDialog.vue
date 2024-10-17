<template>
  <el-dialog v-model="dialogVisible" :before-close="closeDialog" title="修改计划" width="500px" center>
    <div class="dialog-content">
      <!-- 设备选择 -->
      <div class="form-item">
        <label class="form-label">请选择设备：</label>
        <el-select
            v-model="editPlan.equipId"
            placeholder="选择设备"
            size="default"
            class="form-select"
            filterable
        >
          <el-option
              v-for="item in props.equipmentInfoList"
              :key="item.equipId"
              :label="item.equipName"
              :value="item.equipId"
          />
        </el-select>
      </div>

      <!-- 计划名称 -->
      <div class="form-item">
        <label class="form-label">计划名称：</label>
        <el-input
            v-model="editPlan.planName"
            placeholder="请输入计划名称"
            size="default"
            class="form-input"
        />
      </div>

      <!-- 计划类型 -->
      <div class="form-item">
        <label class="form-label">计划类型：</label>
        <el-select
            v-model="editPlan.maintanceType"
            placeholder="选择计划类型"
            size="default"
            class="form-select"
            filterable
        >
          <el-option
              v-for="item in props.equipmentMaintenanceType"
              :key="item.typeId"
              :label="item.maintanceName"
              :value="item.maintanceName"
          />
        </el-select>
      </div>

      <!-- 计划时间 -->
      <div class="form-item">
        <label class="form-label">计划时间：</label>
        <el-date-picker
            v-model="planDate"
            type="datetimerange"
            start-placeholder="计划开始时间"
            end-placeholder="计划结束时间"
            format="YYYY/MM/DD HH:mm:ss"
            value-format="YYYY/MM/DD HH:mm:ss"
            class="form-date-picker"
        />
      </div>

      <!-- 计划描述 -->
      <div class="form-item">
        <label class="form-label">计划描述：</label>
        <el-input
            v-model="editPlan.maintanceDesc"
            type="textarea"
            placeholder="请输入计划描述"
            :autosize="{ minRows: 4, maxRows: 8 }"
            class="form-textarea"
        />
      </div>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="confirmEdit">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref, watch} from 'vue';

import {ElNotification} from 'element-plus';

const props = defineProps({
  maintenancePlanEditVisible: {
    type: Boolean,
    default: false,
  },
  currentRow: {
    type: Object,
    default: () => null,
  },
  equipmentInfoList: {
    type: Array,
    default: () => [],
  },
  equipmentMaintenanceType: {
    type: Array,
    default: () => [],
  },
});

const dialogVisible = ref(props.maintenancePlanEditVisible);
const emit = defineEmits(['closeDialog', 'editMaintenancePlan']);

const planDate = ref([]);

watch(
    () => props.maintenancePlanEditVisible,
    (newValue) => {
      dialogVisible.value = newValue;
      if (props.currentRow) {
        editPlan.value = {...props.currentRow};
        planDate.value = [editPlan.value.startTime, editPlan.value.endTime];
      }
    }
);

const closeDialog = () => {
  clearForm();
  dialogVisible.value = false;
  emit('closeDialog');
};

const confirmEdit = () => {
  // 检查 planDate 是否为数组并且不为空
  if (!Array.isArray(planDate.value) || planDate.value.length < 2 || !planDate.value[0] || !planDate.value[1]) {
    ElNotification({
      message: '开始或结束日期不能为空',
      type: 'error',
    });
    return;
  }

  // 更新日期
  editPlan.value.startTime = planDate.value[0];
  editPlan.value.endTime = planDate.value[1];

  // 日志输出以调试
  console.log(editPlan.value);

  // 简化的空值校验逻辑
  const fieldsToCheck = ['planName', 'equipId', 'maintanceDesc', 'maintanceType', 'startTime'];
  const hasEmptyField = fieldsToCheck.some(field => !editPlan.value[field] || editPlan.value[field].trim() === '');

  if (hasEmptyField) {
    ElNotification({
      message: '不能存在空值，请重试',
      type: 'error',
    });
    return;
  }

  // 如果没有空值，继续进行后续逻辑
  emit("editMaintenancePlan", editPlan.value);
  closeDialog(); // 关闭对话框
};

const editPlan = ref({
  equipId: '',
  planName: '',
  maintanceDesc: '',
  startTime: null,
  endTime: null,
  maintanceType: '',
});

const clearForm = () => {
  editPlan.value = {
    equipId: '',
    planName: '',
    maintanceDesc: '',
    startTime: null,
    endTime: null,
    maintanceType: '',
  };
  planDate.value = [];
};
</script>

<style scoped>
.dialog-content {
  display: flex;
  flex-direction: column;
  gap: 15px; /* 元素之间的间距 */
}

.form-item {
  display: flex;
  align-items: center; /* 垂直居中 */
  gap: 10px; /* label与输入框之间的间距 */
}

.form-label {
  width: 110px; /* label宽度一致 */
  font-weight: bold;
}

.form-select, .form-input, .form-date-picker, .form-textarea {
  width: 100%; /* 保证输入框和选择框占满可用空间 */
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px; /* 按钮之间的间距 */
}
</style>