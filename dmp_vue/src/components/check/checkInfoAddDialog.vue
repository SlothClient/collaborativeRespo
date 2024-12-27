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
            v-model="addPlan.checkName"
            placeholder="请输入计划名称"
            size="default"
            class="form-input"
        />
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

      <!-- 点巡检位置 -->
      <div class="form-item">
        <label class="form-label">点巡检位置：</label>
        <el-input
            v-model="addPlan.checkSite"
            type="textarea"
            placeholder="请输入点巡检位置"
            :autosize="{ minRows: 4, maxRows: 8 }"
            class="form-textarea"
        />
      </div>
      <!-- 点巡检内容 -->
      <div class="form-item">
        <label class="form-label">点巡检内容：</label>
        <el-input
            v-model="addPlan.checkContent"
            type="textarea"
            placeholder="请输入点巡检内容"
            :autosize="{ minRows: 4, maxRows: 8 }"
            class="form-textarea"
        />
      </div>
      <!-- 点巡检描述 -->
      <div class="form-item">
        <label class="form-label">点巡检描述：</label>
        <el-input
            v-model="addPlan.checkDesc"
            type="textarea"
            placeholder="请输入点巡检描述"
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
import {ref, watch} from "vue";
import moment from 'moment'
import {ElNotification} from "element-plus";

const props = defineProps({
  checkInfoAddVisible:{
    type:Boolean,
    default:false,
  },
  equipmentInfoList: {
    type: Array,
    default: () => [],
  },
});
const dialogVisible = ref(props.checkInfoAddVisible);
const emit = defineEmits(['closeDialog', 'addCheckInfo']);

const planDate = ref([]);

watch(
    () => props.checkInfoAddVisible,
    (newValue) => {
      dialogVisible.value = newValue;
    }
);

const closeDialog = () => {
  clear();
  dialogVisible.value = false;
  emit('closeDialog');
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
    emit("addCheckInfo", addPlan.value);
    closeDialog();
  };

const addPlan = ref({
  equipId: '',
  checkName: '',
  checkDesc: '',
  checkSite:'',
  checkContent:'',
  startTime: null,
  endTime: null,
  createTime: undefined
});
const clear = () => {
  addPlan.value = {
    equipId: '',
    planName: '',
    checkDesc: '',
    checkSite:'',
    checkContent:'',
    startTime: null,
    endTime: null,
    createTime: null,
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
<script setup lang="ts">
</script>