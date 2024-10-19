<template>
  <el-dialog v-model="dialogVisible" :before-close="closeDialog" title="添加保养计划" width="700px" center>
    <div id="planInfo">
      <div class="formTitle">添加保养计划</div>
      <el-form :inline="true" class="compact-form">
        <el-row :gutter="20">
          <!-- 左侧：设备选择和计划类型 -->
          <el-col :span="12">
            <el-row :gutter="20">
              <el-col :span="20">
                <el-form-item label="请选择设备">
                  <el-select
                      v-model="addPlan.equipId"
                      placeholder="选择设备"
                      filterable
                      @change="handleEquipChange"
                      class="form-select"
                  >
                    <el-option
                        v-for="item in props.equipmentInfoList"
                        :key="item.equipId"
                        :label="item.equipName"
                        :value="item.equipId"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="20">
                <el-form-item label="计划类型">
                  <el-select
                      v-model="addPlan.maintanceType"
                      placeholder="选择计划类型"
                      filterable
                      class="form-select"
                  >
                    <el-option
                        v-for="item in props.equipmentMaintenanceType"
                        :key="item.typeId"
                        :label="item.maintanceName"
                        :value="item.maintanceName"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="20">
                <el-form-item label="设备编号">
                  <el-input
                      v-model="addPlan.equipId"
                      class="form-input"
                      disabled
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>

          <!-- 右侧：图片展示 -->
          <el-col :span="5">
            <el-form-item label="设备图片" class="equip-image-item">
              <img :src="selectedEquipPic" alt="尚未选择设备" class="equip-image" @click="handlePictureCardPreview" />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 计划名称 -->
        <el-form-item label="计划名称" class="full-width">
          <el-input
              v-model="addPlan.planName"
              placeholder="请输入计划名称"
              class="form-input"
              clearable
          />
        </el-form-item>

        <!-- 计划时间 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="开始时间">
              <el-date-picker
                  v-model="planDate[0]"
                  type="datetime"
                  start-placeholder="开始时间"
                  class="form-date-picker"
                  format="YYYY/MM/DD HH:mm:ss"
                  value-format="YYYY/MM/DD HH:mm:ss"
                  clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束时间">
              <el-date-picker
                  v-model="planDate[1]"
                  type="datetime"
                  end-placeholder="结束时间"
                  class="form-date-picker"
                  format="YYYY/MM/DD HH:mm:ss"
                  value-format="YYYY/MM/DD HH:mm:ss"
                  clearable
              />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 计划描述 -->
        <el-form-item label="计划描述" class="full-width">
          <el-input
              v-model="addPlan.maintanceDesc"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8 }"
              placeholder="请输入计划描述"
              class="form-textarea"
              clearable
          />
        </el-form-item>
      </el-form>
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="closeDialog" type="primary" plain>取消</el-button>
        <el-button type="primary" @click="confirm">确认</el-button>
      </div>
    </template>

    <!-- 图片预览模态框 -->
    <el-dialog v-model="dialogVisibleImage" append-to-body width="50%">
      <img width="100%" :src="dialogImageUrl" alt="设备大图预览" />
    </el-dialog>
  </el-dialog>
</template>

<script setup>
import { ref, watch } from 'vue';
import moment from 'moment';
import { ElNotification } from 'element-plus';

const props = defineProps({
  maintenancePlanAddVisible: {
    type: Boolean,
    default: false,
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

const dialogVisible = ref(props.maintenancePlanAddVisible);
const emit = defineEmits(['closeDialog', 'addMaintenancePlan']);

watch(() => props.maintenancePlanAddVisible, (newValue) => {
  dialogVisible.value = newValue;
});

const selectedEquipPic = ref('');
const dialogVisibleImage = ref(false);
const dialogImageUrl = ref('');

const handleEquipChange = (equipId) => {
  const selectedEquip = props.equipmentInfoList.find(item => item.equipId === equipId);
  if (selectedEquip && selectedEquip.equipPic) {
    selectedEquipPic.value = selectedEquip.equipPic;
  } else {
    selectedEquipPic.value = '';
  }
};

const handlePictureCardPreview = () => {
  dialogImageUrl.value = selectedEquipPic.value;
  dialogVisibleImage.value = true;
};

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
  emit("addMaintenancePlan", addPlan.value);
  closeDialog();
};

const addPlan = ref({
  equipId: '',
  planName: '',
  maintanceDesc: '',
  startTime: null,
  endTime: null,
  maintanceType: '',
  createTime: undefined,
});

const planDate = ref([]);

const clear = () => {
  addPlan.value = {
    equipId: '',
    planName: '',
    maintanceDesc: '',
    startTime: null,
    endTime: null,
    maintanceType: '',
    createTime: null,
  };
  planDate.value = [];
  selectedEquipPic.value = '';
};
</script>

<style scoped>
.dialog-footer {
  text-align: right;
  padding: 10px 0;
}

.formTitle {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 12px;
  color: #444;
  border-bottom: 1px solid #dcdfe6;
  padding-bottom: 8px;
}

.compact-form .el-form-item {
  margin-right: 12px;
  margin-bottom: 12px;
  width: 100%;
}

#planInfo {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.full-width {
  width: 100%;
}

.form-select,
.form-input,
.form-date-picker,
.form-textarea {
  width: 100%;
}

.equip-image-item {
  display: flex;
  align-items: center;
  justify-content: center;
}

.equip-image {
  width: 200px;
  height: 100px;
  object-fit: cover;
  cursor: pointer;
  border-radius: 8px;
  border: 1px solid #dcdfe6;
  transition: box-shadow 0.3s ease;
}

.equip-image:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}
</style>