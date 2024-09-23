<template>
  <div class="page-container">
    <!-- 顶部搜索和过滤功能区域 -->
    <el-row class="filter-container">
      <el-col :span="24">
        <div class="filter-content">
          <div class="status-filter">
            <span class="status-label">计划状态：</span>
            <el-checkbox-group v-model="checkboxGroup1" size="small" class="status-checkbox-group">
              <el-checkbox-button v-for="city in cities" :key="city" :value="city" class="status-filter-btn">
                {{ city }}
              </el-checkbox-button>
            </el-checkbox-group>
          </div>
          <el-input v-model="planName" placeholder="输入计划名称" size="small" class="input-search">
            <template #prepend>计划名称:</template>
          </el-input>
          <el-date-picker
              v-model="dateRange"
              type="daterange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              size="small"
              class="date-picker"
          ></el-date-picker>
          <div class="button-group">
            <el-button type="primary" size="small" icon="Search" @click="searchPlans">查询</el-button>
            <el-button size="small" icon="Refresh" @click="resetFilters">重置</el-button>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row class="add-button-row">
      <el-button type="primary" size="small" icon="Plus"
                 @click="isMaintenancePlanAddVisible = !isMaintenancePlanAddVisible" class="add-btn">新增
      </el-button>
      <el-button type="primary" size="small" icon="User" class="add-btn">分派人员</el-button>
      <el-button type="primary" size="small" icon="Filter" class="add-btn">高级查询</el-button>
    </el-row>

    <!-- 卡片区域 -->
    <div class="card-container">
      <div class="card-header">
        <span class="card-title">保养计划信息</span>
        <span class="card-title">操作</span>
      </div>
      <el-card v-for="(item, index) in data" :key="index" class="card">
        <div class="card-content">
          <!-- 计划信息展示 -->
          <el-row :gutter="10">
            <el-col :span="20">
              <el-descriptions :column="2" size="small" class="description-box">
                <el-descriptions-item label="计划名称">{{ item.planName }}</el-descriptions-item>
                <el-descriptions-item label="计划编号">{{ item.planId }}
                  <el-tag size="small" type="success">{{ item.status }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="开始时间">{{ item.startTime }}</el-descriptions-item>
                <el-descriptions-item label="结束时间">{{ item.endTime }}</el-descriptions-item>
                <el-descriptions-item label="计划描述" :span="2">
                  <el-tag size="small" type="info">{{ item.description }}</el-tag>
                </el-descriptions-item>
              </el-descriptions>
            </el-col>
            <!-- 操作按钮在最右侧 -->
            <el-col :span="4">
              <div class="card-actions">
                <el-link type="primary" class="action-link"
                         @click="openEditDialog()">编辑
                </el-link>
                <el-link type="primary" class="action-link"
                         @click="openDetailDialog()">详情
                </el-link>
                <el-link type="primary" class="action-link">更多</el-link>
              </div>
            </el-col>
          </el-row>

          <!-- 创建和更新时间 -->
          <el-row class="info-row" :gutter="10">
            <el-col :span="6">
              <span class="info-item"><label>创建时间：</label>{{ item.createTime }}</span>
            </el-col>
            <el-col :span="6">
              <span class="info-item"><label>创建人：</label>{{ item.creator }}</span>
            </el-col>
            <el-col :span="6">
              <span class="info-item"><label>更新时间：</label>{{ item.updateTime }}</span>
            </el-col>
            <el-col :span="6">
              <span class="info-item"><label>更新人：</label>{{ item.updater }}</span>
            </el-col>
          </el-row>
        </div>
      </el-card>
    </div>
    <!--  分页-->
    <div style="margin: 10px 0">
      <el-pagination
          :background="true"
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[1, 10, 20, 100]"
          large
          :disabled="false"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>

    <!--    详情框-->
    <maintenance-plan-detail-dialog
        :maintenancePlanDetailVisible="isMaintenancePlanDetailVisible"
        @close-dialog="closeDetailDialog()"
    ></maintenance-plan-detail-dialog>

    <!--    增加框-->
    <maintenance-plan-add-dialog
        :maintenancePlanAddVisible="isMaintenancePlanAddVisible"
        @close-dialog="closeAddDialog()"
    ></maintenance-plan-add-dialog>

    <!--    修改框-->
    <maintenance-plan-edit-dialog
        :maintenancePlanEditVisible="isMaintenancePlanEditVisible"
        @close-dialog="closeEditDialog()"
    ></maintenance-plan-edit-dialog>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import MaintenancePlanDetailDialog from "@/components/maintenancePlan/maintenancePlanDetailDialog.vue";
import MaintenancePlanAddDialog from "@/components/maintenancePlan/maintenancePlanAddDialog.vue";
import MaintenancePlanEditDialog from "@/components/maintenancePlan/maintenancePlanEditDialog.vue";

const checkboxGroup1 = ref(['全部']);
const cities = ['全部', '待开始', '已派单', '执行中', '已完成'];
const planName = ref("");
const dateRange = ref('');

//详情框
const isMaintenancePlanDetailVisible = ref(false)
const closeDetailDialog = () => {
  isMaintenancePlanDetailVisible.value = false
}
const openDetailDialog =() =>{
  isMaintenancePlanDetailVisible.value = true;
}
//添加
const isMaintenancePlanAddVisible = ref(false)
const closeAddDialog = () => {
  isMaintenancePlanAddVisible.value = false
}
//修改
const isMaintenancePlanEditVisible = ref(false)
const closeEditDialog = () => {
  isMaintenancePlanEditVisible.value = false
}
const openEditDialog = ()=>{
  isMaintenancePlanEditVisible.value = true;
}

const data = ref([
  {
    planName: '每日设备保养',
    planId: 'PL17096183',
    startTime: '2024-03-05 15:34:51',
    endTime: '2024-03-31 15:34:51',
    description: '每日设备保养',
    createTime: '2024-03-05 15:37:37',
    creator: 'aaa',
    updateTime: '2024-03-05 17:06:39',
    updater: 'aaa',
    status: '已完成'
  },
  {
    planName: '压片机保养计划',
    planId: 'PL16475564908',
    startTime: '2023-09-15 16:12:44',
    endTime: '2023-09-16 16:12:44',
    description: '压片机保养',
    createTime: '2023-09-15 16:13:54',
    creator: '管理员',
    updateTime: '2023-09-15 16:14:20',
    updater: '开发管理员',
    status: '已派单'
  },
  {
    planName: '测试计划',
    planId: 'PL16886054764',
    startTime: '2023-07-06 09:04:35',
    endTime: '2023-07-31 09:04:35',
    description: '是',
    createTime: '2023-07-06 09:05:30',
    creator: '管理员',
    updateTime: '2023-03-06 21:26:23',
    updater: '李铭',
    status: '已完成'
  },
]);

//当前页面
const currentPage = ref(0)

//总记录数
const total = ref(10)

//页面大小
const pageSize = ref(10);

//页面变化
const handleCurrentChange = () => {

}

/**
 * 页面大小变化
 */
const handleSizeChange = () => {

}
//搜索
const searchPlans = () => {
  // 实现搜索逻辑
  console.log('搜索计划');
};

const resetFilters = () => {
  // 实现重置逻辑
  checkboxGroup1.value = ['全部'];
  planName.value = '';
  dateRange.value = '';
  console.log('重置过滤器');
};

</script>

<style scoped>
.page-container {
  padding: 20px;
  background-color: white;
  line-height: 10%;
  overflow: hidden;
}


.filter-container {
  padding: 20px;
  background-color: #ffffff;
  margin-bottom: 0;
  border-radius: 8px;
  //box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.filter-content {
  display: flex;
  align-items: center;
  flex-wrap: nowrap;
  gap: 15px;
  margin-bottom: 0;
}

.status-filter {
  display: flex;
  align-items: center;
  flex-shrink: 0;
}

.status-label {
  white-space: nowrap;
  margin-right: 10px;
}

.status-checkbox-group {
  display: flex;
  flex-wrap: nowrap;
}

.status-filter-btn {
  margin-right: 0;
}

.input-search {
  width: 200px;
  flex-shrink: 0;
}

.date-picker {
  width: 300px;
  flex-shrink: 0;
}

.button-group {
  display: flex;
  gap: 10px;
  flex-shrink: 0;
}

/* 新增按钮样式 */
.add-button-row {
  margin-bottom: 20px;
  margin-left: 1%;
}

.add-btn {
  float: right;
}

/* 卡片容器 */
.card-container {
  display: flex;
  flex-direction: column;
  //gap: 10px;
  width: 100%;
}

/* 卡片样式 */
.card {
  width: 100%;
  //border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
  //transition: box-shadow 0.3s ease;
}

.card:hover {
  box-shadow: 4px 8px 20px rgba(0, 0, 0, 0.1);
}

.card-content {
  padding: 10px 15px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 15px;
  margin-bottom: 15px;
}

.card-title {
  font-size: 14px;
  font-weight: normal;
  color: #606266;
}

.description-box {
  margin-bottom: 10px;
}

.info-row {
  font-size: 12px;
  line-height: 1.5;
  color: #606266;
}

.info-item label {
  font-weight: 500;
  color: #909399;
}

.card-actions {
  padding: 0;
  display: flex;
  justify-content: flex-end;
  align-items: center; /* 垂直居中 */
  height: 100%; /* 设置容器高度为 100% */
  gap: 5px; /* 调整按钮之间的间距 */
  margin: auto;
}

.action-link {
  font-size: 12px;
  line-height: 1.5;
}


@media (max-width: 1200px) {
  .filter-content {
    flex-wrap: wrap;
  }

  .status-filter, .input-search, .date-picker, .button-group {
    width: 100%;
    margin-bottom: 10px;
  }
}
</style>