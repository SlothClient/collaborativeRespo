<template>
  <div id="detailDialog">
    <el-dialog v-model="dialogVisible" @close="closeDialog" width="650px" align-center>
      <div id="planInfo">
        <div class="formTitle">保养计划基本信息</div>
        <el-form :inline="true" class="compact-form" disabled>

          <!-- 第一行：计划编号和计划名称 -->
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="计划编号">
                <el-input v-model="maintenancePlanDetail.planId" clearable/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="计划名称">
                <el-input v-model="maintenancePlanDetail.planName" clearable/>
              </el-form-item>
            </el-col>
          </el-row>

          <!-- 第二行：开始时间和结束时间 -->
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="开始时间">
                <el-date-picker v-model="maintenancePlanDetail.startTime" type="datetime" clearable/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="结束时间">
                <el-date-picker v-model="maintenancePlanDetail.endTime" type="datetime" clearable/>
              </el-form-item>
            </el-col>
          </el-row>

          <!-- 第三行：维修类型和计划描述 -->
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="维修类型">
                <el-input v-model="maintenancePlanDetail.maintanceType" clearable/>
              </el-form-item>
            </el-col>
            <el-col :span="16">
              <el-form-item label="计划描述" class="full-width">
                <el-input v-model="maintenancePlanDetail.maintanceDesc" type="textarea" clearable/>
              </el-form-item>
            </el-col>
          </el-row>

        </el-form>
      </div>

      <div id="equipInfo">
        <div class="formTitle">保养设备信息</div>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="设备名称">
              <el-input v-model="maintenancePlanDetail.equipName" disabled/>
            </el-form-item>
            <el-form-item label="设备编号">
              <el-input v-model="maintenancePlanDetail.equipId" disabled/>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="设备图片" class="equip-image-item">
              <img :src="maintenancePlanDetail.equipPic" alt="设备图片" class="equip-image"
                   @click="handlePictureCardPreview"/>
            </el-form-item>
          </el-col>
        </el-row>
      </div>

      <div id="timeInfo">
        <div class="formTitle">创建与更新时间信息</div>
        <el-form :inline="true" class="compact-form" disabled>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="创建者">
                <el-input v-model="maintenancePlanDetail.creator" clearable/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="创建时间">
                <el-date-picker v-model="maintenancePlanDetail.createTime" type="datetime" clearable/>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="更新者">
                <el-input v-model="maintenancePlanDetail.updatePerson" clearable/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="更新时间">
                <el-date-picker v-model="maintenancePlanDetail.updateTime" type="datetime" clearable/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="closeDialog" type="primary" plain>关闭</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 设备图片查看模态框 -->
    <el-dialog v-model="dialogVisibleImage" append-to-body width="50%">
      <img width="100%" :src="dialogImageUrl" alt="设备大图预览">
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, watch} from 'vue';

const props = defineProps({
  maintenancePlanDetailVisible: {
    type: Boolean,
    default: false,
  },
  maintenancePlanDetail: {
    type: Object,
    default: () => ({}),
  }
});

const dialogVisible = ref(props.maintenancePlanDetailVisible);
const emit = defineEmits(['closeDialog']);

// 图片预览相关
const dialogVisibleImage = ref(false);
const dialogImageUrl = ref('');

watch(() => props.maintenancePlanDetailVisible, (newValue) => {
  dialogVisible.value = newValue;
});

const closeDialog = () => {
  dialogVisible.value = false;
  emit('closeDialog');
};

// 图片预览
const handlePictureCardPreview = () => {
  dialogImageUrl.value = props.maintenancePlanDetail.equipPic;
  dialogVisibleImage.value = true;
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
}

#planInfo, #equipInfo, #timeInfo {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.full-width {
  width: 100%;
}

.equip-image-item {
  display: flex;
  align-items: center;
  justify-content: center;
}

.equip-image {
  width: 100px;
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