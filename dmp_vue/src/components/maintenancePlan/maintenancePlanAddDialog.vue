<template>
  <el-dialog v-model="dialogVisible" :before-close="closeDialog" title="添加计划" width="500px" center>
    <div class="dialog-content">
      <!-- 设备选择 -->
      <div class="form-item">
        <label class="form-label">请选择设备：</label>
        <el-select
            v-model="addPlan.equipId"
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
            v-model="addPlan.planName"
            placeholder="请输入计划名称"
            size="default"
            class="form-input"
        />
      </div>

      <!-- 计划类型 -->
      <div class="form-item">
        <label class="form-label">计划类型：</label>
        <el-select
            v-model="addPlan.maintanceType"
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
            size="large"
            style="width: 450px"
        />
      </div>

      <!-- 计划描述 -->
      <div class="form-item">
        <label class="form-label">计划描述：</label>
        <el-input
            v-model="addPlan.maintanceDesc"
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
        <el-button type="primary" @click="confirm">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref, watch} from 'vue';
import moment from 'moment'
import {ElNotification} from "element-plus";

// 接收组件的props
const props = defineProps({
  maintenancePlanAddVisible: {
    type: Boolean,
    default: false
  },
  equipmentInfoList: {
    type: Array,
    default: () => []
  },
  equipmentMaintenanceType: {
    type: Array,
    default: () => []
  }
});

const dialogVisible = ref(props.maintenancePlanAddVisible);
const emit = defineEmits(["closeDialog", "addMaintenancePlan"]);

watch(() => props.maintenancePlanAddVisible, (newValue) => {
  dialogVisible.value = newValue;
});

const closeDialog = () => {
  clear()
  dialogVisible.value = false;
  emit("closeDialog");
};

const confirm = () => {

  addPlan.value.startTime = planDate.value[0];
  addPlan.value.endTime = planDate.value[1];
  addPlan.value.createTime = moment(new Date()).format('YYYY/MM/DD HH:mm:ss');

  // 空值校验逻辑
  const isEmpty = Object.values(addPlan.value).some(value => {
    if (typeof value === 'string') {
      return value.trim() === '';
    }
    return value === null || value === undefined;
  });
  if (isEmpty) {
    ElNotification({
      message: "不能存在空值,请重试",
      type: 'error'
    });
    return;
  }
  emit("addMaintenancePlan", addPlan.value);
  closeDialog();
};
const addPlan = ref({
  equipId: '',
  planName: '',
  maintanceDesc: "",
  startTime: null,
  endTime: null,
  maintanceType: '',
  createTime: undefined
})
const planDate = ref([])
// 表单数据
// const equipId = ref('');
// const planName = ref('');
// const maintenanceType = ref('');
// const planDate = ref([]);
// const maintenanceDesc = ref('');

//清空
const clear = () => {
  addPlan.value.planName = '';
  addPlan.value.equipId = '';
  addPlan.value.maintanceDesc = '';
  addPlan.value.maintanceType = '';
  addPlan.value.startTime = null;
  addPlan.value.endTime = null;
  addPlan.value.createTime = null;
  planDate.value = [];  // 这里清空日期选择器
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