<template>
  <el-drawer
      v-model="drawer"
      :open-delay="200"
      :close-delay="200"
      :before-close="closeDrawer"
      :show-close="true"
      size="40%"
      direction="ltr"
      :title="'计划'+title+'——审批流程'"
  >
    <el-timeline hide-timestamp class="timeline" style="max-width: 600px;">
      <el-timeline-item
          v-for="(item, index) in props.currentRow.approvalInfoList"
          :key="index"
          class="timeline-item"
          placement="top"
          :type="getTagType(item.nodeStatus)"
          :timestamp="item.actionTime ? item.actionTime : '还未处理'"
      >
        <el-card class="timeline-card">
          <el-row :gutter="10">
            <el-col :span="20">
              <el-descriptions :column="1" size="small" class="description-box">
                <div v-if="item.stepOrder === 0">
                  <el-descriptions-item label="发起人">{{ item.username }}</el-descriptions-item>
                  <el-descriptions-item label="计划开始时间">{{ props.currentRow.startTime }}</el-descriptions-item>
                  <el-descriptions-item label="计划结束时间">{{ props.currentRow.endTime }}</el-descriptions-item>
                  <el-descriptions-item label="设备名称">{{ props.currentRow.equipName }}</el-descriptions-item>
                  <el-descriptions-item label="当前状态">
                    <el-tag size="small" :type="getTagType(props.currentRow.planStatus)">
                      {{ getTagText(props.currentRow.planStatus) }}
                    </el-tag>
                  </el-descriptions-item>
                  <el-descriptions-item label="计划描述" :span="2">
                    {{ props.currentRow.maintenanceDesc }}
                  </el-descriptions-item>
                </div>
                <div v-else>
                  <el-descriptions-item label="审批人">{{
                      item.username === null ? "还未审批" : item.username
                    }}
                  </el-descriptions-item>
                  <el-descriptions-item label="操作时间">{{
                      item.actionTime === null ? "还未审批" : item.actionTime
                    }}
                  </el-descriptions-item>
                  <el-descriptions-item label="审批状态">
                    <el-tag size="small" :type="getTagType(item.nodeStatus)">
                      {{ getTagText(item.nodeStatus) }}
                    </el-tag>
                  </el-descriptions-item>
                  <el-descriptions-item label="审批意见">{{
                      item.remark === null ? "还未审批" : item.remark
                    }}
                  </el-descriptions-item>
                </div>
              </el-descriptions>
            </el-col>
          </el-row>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </el-drawer>
</template>

<script setup>
import {computed} from 'vue';

const props = defineProps({
  approvalDetailVisible: {
    type: Boolean,
    default: false,
  },
  currentRow: {
    type: Object,
    default: () => null,
  },
  title: {
    type: String,
    default: ''
  }
});

const emit = defineEmits(['closeApprovalDetail']);

const drawer = computed({
  get: () => props.approvalDetailVisible,
  set: (value) => emit('closeApprovalDetail', value)
});

const closeDrawer = () => {
  drawer.value = false;
};

const getTagType = (status) => {
  switch (status) {
    case 0:
      return 'warning';
    case 1:
      return 'primary';
    case 2:
      return 'success';
    case 3:
      return 'danger';
    default:
      return 'info';
  }
};

const getTagText = (status) => {
  switch (status) {
    case 0:
      return '待审批';
    case 1:
      return '审批中';
    case 2:
      return '已通过';
    case 3:
      return '已驳回';
    default:
      return '已撤销';
  }
};
</script>

<style scoped>

/* 添加过渡效果 */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */
{
  opacity: 0;
}
.drawer-content {
  padding: 20px;
  background-color: #ffffff;
  overflow: hidden;
}

.timeline-card {
  padding: 20px;
  border-radius: 8px;
  background-color: #ffffff;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.description-box {
  margin-top: 10px;
}

.timeline-item {
  align-items: flex-start;
}

.el-timeline-item__timestamp {
  font-size: 12px;
  color: #888;
}

.el-tag {
  margin-top: 5px;
}

.el-drawer {
  transition: all 0.3s ease;
}

.el-drawer__wrapper {
  backdrop-filter: blur(5px);
}

.card-text {
  font-size: 16px;
  font-weight: bold;
  line-height: 1.5;
  color: #333;
}

/* Timeline Styles to Ensure Continuous Connection */
.el-timeline {
  padding-left: 0;
}

.el-timeline-item {
  position: relative;
}

.el-timeline-item__tail {
  position: absolute;
  left: 14px;
  top: 0; /* Ensure it starts at the top of the timeline */
  height: calc(100% + 20px); /* Extend beyond the bottom to connect to next */
  border-left: 2px solid #e4e7ed; /* Ensures continuity */
}

.el-timeline-item__node {
  position: absolute;
  left: 8px;
  top: 12px; /* Position node properly in line */
  width: 12px;
  height: 12px;
  background-color: #e4e7ed;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.el-timeline-item__wrapper {
  padding-left: 28px;
  position: relative;
}
</style>