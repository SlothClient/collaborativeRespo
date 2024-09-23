<template>
  <el-drawer
      v-model="drawer"
      :open-delay="200"
      :close-delay="200"
      :before-close="closeDrawer"
      :show-close="true"
      size="40%"
      direction="ltr"
  >
    <div class="drawer-content" v-for="(item, index) in timelineItems" :key="index">
      <el-timeline hide-timestamp style="max-width: 600px;">
        <el-timeline-item
            class="timeline-item"
            :type="getTagType(item.status)"
        >
          <el-card class="timeline-card">
            <el-row :gutter="10">
              <el-col :span="20">
                <el-descriptions :column="1" size="small" class="description-box">
                  <el-descriptions-item label="发起人">{{ item.name }}</el-descriptions-item>
                  <el-descriptions-item label="发起时间">{{ item.date }}</el-descriptions-item>
                  <el-descriptions-item label="设备名称">{{ item.deviceName }}</el-descriptions-item>
                  <el-descriptions-item label="当前状态">
                    <el-tag size="small" :type="getTagType(item.status)">
                      {{ item.status }}
                    </el-tag>
                  </el-descriptions-item>
                  <el-descriptions-item label="计划描述" :span="2">
                    {{ item.description }}
                  </el-descriptions-item>
                </el-descriptions>
              </el-col>
            </el-row>
          </el-card>
        </el-timeline-item>
        <el-timeline-item
            v-for="(reviewer, rIndex) in item.reviewers"
            :key="rIndex"
            class="timeline-item"
            :type="getTagType(reviewer.status)"
        >
          <el-card class="timeline-card">
            <el-row :gutter="10">
              <el-col :span="24">
                <el-descriptions :column="1" size="small" class="description-box">
                  <el-descriptions-item label="审核人">{{ reviewer.name }}</el-descriptions-item>
                  <el-descriptions-item label="审核时间" :span="2">
                    {{ reviewer.timestamp }}
                  </el-descriptions-item>
                  <el-descriptions-item label="审核状态">
                    <el-tag size="small" :type="getTagType(reviewer.status)">
                      {{ reviewer.status }}
                    </el-tag>
                  </el-descriptions-item>
                  <el-descriptions-item label="审核意见" :span="2">
                    {{ reviewer.comments }}
                  </el-descriptions-item>
                </el-descriptions>
              </el-col>
            </el-row>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
  </el-drawer>
</template>

<script setup>
import {ref, computed} from 'vue';

const props = defineProps({
  approvalDetailVisible: {
    type: Boolean,
    default: false,
  },
  currentRow: {
    type: Object,
    default: () => null,
  },
});

const emit = defineEmits(['closeApprovalDetail']);

const drawer = computed({
  get: () => props.approvalDetailVisible,
  set: (value) => emit('closeApprovalDetail', value)
});

const timelineItems = ref([
  {
    deviceName: '设备A',
    name: '用户1',
    description: '这是设备A的计划描述内容。',
    status: '待审核',
    date: '2023-05-15 10:00:00',
    reviewers: [
      {
        name: '审核人1',
        status: '已通过',
        comments: '审核意见A：设备状态良好，可以通过。',
        timestamp: '2023-05-15 11:30:00',
      },
      {
        name: '审核人2',
        status: '待审核',
        comments: '审核意见B：需要进一步检查。',
        timestamp: '2023-05-15 14:00:00',
      },
    ],
  },
]);

const closeDrawer = () => {
  drawer.value = false;
};

// 设置标签类型
const getTagType = (status) => {
  switch (status) {
    case '待审核':
      return 'warning';
    case '已通过':
      return 'success';
    case '已拒绝':
      return 'danger';
    case '进行中':
      return 'info';
    default:
      return 'info';
  }
};
</script>

<style scoped>
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
  /* 移除 display: flex; */
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

/* 添加以下样式 */
.el-timeline {
  padding-left: 0;
}

.el-timeline-item {
  position: relative;
}

.el-timeline-item__tail {
  position: absolute;
  left: 4px;
  height: 100%;
  border-left: 2px solid #e4e7ed;
}

.el-timeline-item__node {
  position: absolute;
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