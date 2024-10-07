<template>
  <el-dialog v-model="dialogVisible" :before-close="closeDialog" title="保养计划详情" width="700px" class="custom-dialog">
    <div class="dialog-content">
      <!-- 信息展示 -->
      <el-row :gutter="20" class="info-section">
        <el-col :span="12">
          <div class="info-card plan-info">
            <h4>计划信息</h4>
            <p><strong>计划编号:</strong> {{ maintenancePlanDetail.planId }}</p>
            <p><strong>计划名称:</strong> {{ maintenancePlanDetail.planName }}</p>
            <p><strong>维修类型:</strong> {{ maintenancePlanDetail.maintanceType }}</p>
            <p><strong>计划描述:</strong> {{ maintenancePlanDetail.maintanceDesc }}</p>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-card equip-info">
            <h4>设备信息</h4>
            <p><strong>设备编号:</strong> {{ maintenancePlanDetail.equipId }}</p>
            <p><strong>设备名称:</strong> {{ maintenancePlanDetail.equipName }}</p>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="20" class="info-section">
        <el-col :span="12">
          <div class="info-card time-info">
            <h4>时间信息</h4>
            <p><strong>开始时间:</strong> {{ (maintenancePlanDetail.startTime) }}</p>
            <p><strong>结束时间:</strong> {{ (maintenancePlanDetail.endTime) }}</p>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-card status-info">
            <h4>状态信息</h4>
            <p><strong>状态:</strong> {{ formatStatus(maintenancePlanDetail.status) }}</p>
            <p><strong>备注:</strong> {{ maintenancePlanDetail.remark }}</p>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="20" class="info-section">
        <el-col :span="12">
          <div class="info-card create-info">
            <h4>创建信息</h4>
            <p><strong>创建者:</strong> {{ maintenancePlanDetail.creator }}</p>
            <p><strong>创建时间:</strong> {{ (maintenancePlanDetail.createTime) }}</p>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-card update-info">
            <h4>更新信息</h4>
            <p><strong>更新者:</strong> {{ maintenancePlanDetail.updatePerson }}</p>
            <p><strong>更新时间:</strong> {{ maintenancePlanDetail.updateTime ? (maintenancePlanDetail.updateTime) : '暂无' }}</p>
          </div>
        </el-col>
      </el-row>
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="closeDialog">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, watch } from 'vue';

const props = defineProps({
  maintenancePlanDetailVisible: {
    type: Boolean,
    default: false,
  },
  maintenancePlanDetail: {
    type: Object,
    default: () => null,
  },
});

const dialogVisible = ref(props.maintenancePlanDetailVisible);
const emit = defineEmits(['closeDialog']);

watch(
    () => props.maintenancePlanDetailVisible,
    (newValue) => {
      dialogVisible.value = newValue;
    }
);

const closeDialog = () => {
  dialogVisible.value = false;
  emit('closeDialog');
};



const formatStatus = (status) => {
  switch (status) {
    case 0:
      return '未开始';
    case 1:
      return '进行中';
    case 2:
      return '已完成';
    default:
      return '未知';
  }
};
</script>

<style scoped>
.custom-dialog .el-dialog {
  background: linear-gradient(135deg, #f0f4f7, #fff);
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
}

.dialog-content {
  padding: 20px 0;
  max-height: 600px; /* 限制对话框的最大高度，取消滚动条 */

}

.info-section {
  margin-bottom: 20px;
}

.info-card {
  padding: 15px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  height: 160px; /* 保持每块区域大小一致 */
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.info-card h4 {
  font-size: 16px;
  margin-bottom: 10px;
  color: #333;
}

.info-card p {
  font-size: 14px;
  margin: 5px 0;
  color: #666;
}

/* 为每个信息块添加不同的背景色 */
.plan-info {
  background: #e3f2fd;
}

.equip-info {
  background: #e8f5e9;
}

.time-info {
  background: #fff3e0;
}

.status-info {
  background: #fce4ec;
}

.create-info {
  background: #e1f5fe;
}

.update-info {
  background: #f3e5f5;
}

.dialog-footer {
  text-align: right;
  padding: 10px 0;
}

.el-button {
  min-width: 80px;
  margin-left: 10px;
}
</style>