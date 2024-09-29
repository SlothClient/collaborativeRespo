<template>
  <div class="page-container">
    <!-- 顶部搜索和过滤功能区域 -->
    <el-row class="filter-container">
      <el-col :span="24">
        <div class="filter-content">
          <div class="status-filter">
            <span class="status-label">计划状态：</span>
            <el-checkbox-group v-model="checkboxGroup1" size="small" class="status-checkbox-group">
              <el-checkbox-button v-for="(status, index) in statusOptions" :key="index" :value="status.value"
                                  :label="status.label" class="status-filter-btn">
                {{ status.label }}
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
              format="YYYY/MM/DD HH:mm:ss"
              value-format="YYYY/MM/DD HH:mm:ss"
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
        <span class="card-title"><el-checkbox
            v-model="checkAll"
            :indeterminate="isIndeterminate"
            @change="handleCheckAllChange">
            保养计划信息
          </el-checkbox>
        </span>
        <span class="card-title">操作</span>
      </div>
      <el-card v-for="(item, index) in data" :key="index" class="card">
        <div class="card-content">
          <!-- 计划信息展示 -->
          <el-row :gutter="10">
            <el-col :span="1">
              <el-checkbox v-model="checkedItems[index]" @change="handleItemChange"></el-checkbox>
            </el-col>
            <el-col :span="19">
              <el-descriptions :column="2" size="small" class="description-box">
                <el-descriptions-item label="计划名称">{{ item.planName }}</el-descriptions-item>
                <el-descriptions-item label="计划编号">{{ item.planId }}
                  <el-tag size="small" type="success">{{ getStatusLabel(item.status) }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="开始时间">{{ item.startTime }}</el-descriptions-item>
                <el-descriptions-item label="结束时间">{{ item.endTime }}</el-descriptions-item>
                <el-descriptions-item label="计划类型" :span="2">
                  <el-tag size="small" type="info">{{ item.maintanceType }}</el-tag>
                </el-descriptions-item>
              </el-descriptions>
            </el-col>
            <!-- 操作按钮在最右侧 -->
            <el-col :span="4">
              <div class="card-actions">
                <el-link type="primary" class="action-link" @click="openEditDialog(item)">编辑</el-link>
                <el-link type="primary" class="action-link" @click="openDetailDialog(item.planId)">详情</el-link>
                <el-link type="primary" @click.prevent="handleDelete(item)" class="action-link">撤销</el-link>

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
              <span class="info-item"><label>更新人：</label>{{ item.updatePerson }}</span>
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
          :page-sizes="[3, 5, 10, 100]"
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
        :maintenancePlanDetail="currentRowDetail"
    ></maintenance-plan-detail-dialog>

    <!--    增加框-->
    <maintenance-plan-add-dialog
        :maintenancePlanAddVisible="isMaintenancePlanAddVisible"
        @close-dialog="closeAddDialog()"
        @add-maintenance-plan="addMaintenancePlan"
        :equipmentInfoList="equipmentInfo"
        :equipmentMaintenanceType="equipmentMaintenanceType"

    ></maintenance-plan-add-dialog>

    <!--    修改框-->
    <maintenance-plan-edit-dialog
        :maintenancePlanEditVisible="isMaintenancePlanEditVisible"
        @close-dialog="closeEditDialog()"
        :currentRow="currentRowEdit"
        :equipmentInfoList="equipmentInfo"
        :equipmentMaintenanceType="equipmentMaintenanceType"
        @edit-maintenance-plan="editMaintenancePlan"
    ></maintenance-plan-edit-dialog>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import MaintenancePlanDetailDialog from "@/components/maintenancePlan/maintenancePlanDetailDialog.vue";
import MaintenancePlanAddDialog from "@/components/maintenancePlan/maintenancePlanAddDialog.vue";
import MaintenancePlanEditDialog from "@/components/maintenancePlan/maintenancePlanEditDialog.vue";
import {
  addPlan,
  getMaintenancePlan, getPlanDetail, undoPlan, updateMaintenance,
} from "@/api/maintenancePlan/index.js";
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";
import {useEquipmentInfoStore} from "@/store/module/equipmentInfo.js";

// 状态映射
const statusOptions = [
  {label: '全部', value: -1},
  {label: '待开始', value: 0},
  {label: '已派单', value: 1},
  {label: '执行中', value: 2},
  {label: '已完成', value: 3}
];

const currentRowEdit = ref(null)
const currentRowDetail = ref(null)

//删除
const handleDelete = (plan) => {
  ElMessageBox.confirm(
      '你确定要撤销' + plan.planName,
      '删除计划',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(async () => {
        const res = await undoPlan(plan.planId)

        if (res.data.flag) {
          ElNotification({
            message: res.data.data,
            type: "success"
          })
          await getMaintenance(maintenancePlanReq.value);
        } else {
          ElNotification({
            message: res.data.data,
            type: "error"
          })
        }
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '取消删除',
        })
      })
}

const getStatusLabel = (statusValue) => {
  // 根据statusValue返回相应的label
  const option = statusOptions.find(option => option.value === statusValue);
  return option ? option.label : '未知状态';
}

const checkboxGroup1 = ref([-1]); // 默认选择“全部”
const planName = ref("");
const dateRange = ref([]);

// 详情框
const isMaintenancePlanDetailVisible = ref(false);
const closeDetailDialog = () => {
  isMaintenancePlanDetailVisible.value = false;
};


const openDetailDialog = async (planId) => {
  const res = await getPlanDetail(planId)
  if (!res.data.flag) {
    ElNotification({
      message: res.data.data,
      type: "error"
    })
    return
  }
  currentRowDetail.value = res.data.data
  isMaintenancePlanDetailVisible.value = true;
};
// 添加

const isMaintenancePlanAddVisible = ref(false);
const closeAddDialog = () => {
  isMaintenancePlanAddVisible.value = false;
};
/**
 * 添加保养计划
 * @param Plan
 * @returns {Promise<void>}
 */
const addMaintenancePlan = async (Plan) => {
  const res = await addPlan(Plan);
  if (!res.data.flag) {
    ElNotification({
      message: res.data.msg,
      type: "error"
    })
  } else {
    ElNotification({
      message: res.data.data,
      type: "success"
    })
    await getMaintenance(maintenancePlanReq.value);
  }

}

// 修改
const isMaintenancePlanEditVisible = ref(false);
const closeEditDialog = () => {
  isMaintenancePlanEditVisible.value = false;
};
const openEditDialog = (row) => {
  currentRowEdit.value = row
  console.log(row)
  isMaintenancePlanEditVisible.value = true;
};

const editMaintenancePlan = async (val) => {
  console.log(val)
  const editPlan = {
    equipId: val.equipId,
    planName: val.planName,
    startTime: val.startTime,
    endTime: val.endTime,
    maintanceDesc: val.maintanceDesc,
    maintanceType: val.maintanceType,
    planId: val.planId
  }
  const res = await updateMaintenance(editPlan)
  if (!res.data.flag) {
    ElNotification({
      message: res.data.msg,
      type: "error"
    })
  } else {
    ElNotification({
      message: res.data.data,
      type: "success"
    })
    clear()
    await getMaintenance(maintenancePlanReq.value)
  }

}

const data = ref([
  // 示例数据
]);


//搜索请求
const maintenancePlanReq = ref({
  currentPage: 1,
  pageSize: 3,
  planName: '',
  startTime: null,
  endTime: null,
  status: []
});

//分页
const currentPage = ref(1);
const pageSize = ref(3);
const total = ref(data.value.length);

const handleCurrentChange = (newPage) => {
  maintenancePlanReq.value.currentPage = newPage;
  getMaintenance(maintenancePlanReq.value);
};

const handleSizeChange = (newPageSize) => {
  maintenancePlanReq.value.pageSize = newPageSize;
  getMaintenance(maintenancePlanReq.value);
};


//搜索
const searchPlans = () => {
  maintenancePlanReq.value.planName = planName.value;
  maintenancePlanReq.value.startTime = dateRange.value[0];
  maintenancePlanReq.value.endTime = dateRange.value[1];
  maintenancePlanReq.value.currentPage = 1;
  if (checkboxGroup1.value.includes(-1)) {
    // 当包含 '-1' 时，将 checkboxGroup1 设置为 [0, 1, 2, 3]
    maintenancePlanReq.value.status = statusOptions.slice(1).map(option => option.value);
  } else {
    maintenancePlanReq.value.status = checkboxGroup1.value;
  }
  getMaintenance(maintenancePlanReq.value);
  currentPage.value = 1
};

//重置
const clear = () => {
  checkboxGroup1.value = [-1];
  planName.value = '';
  dateRange.value = [];
  maintenancePlanReq.value.planName = '';
  maintenancePlanReq.value.startTime = null;
  maintenancePlanReq.value.endTime = null;
  maintenancePlanReq.value.status = [];
}

const resetFilters = () => {
  clear()
  getMaintenance(maintenancePlanReq.value);
};


// 全选、反选功能相关
const checkAll = ref(false);
const checkedItems = ref([]); // 存储每个计划是否被选中
const isIndeterminate = ref(false);

const handleCheckAllChange = (value) => {
  checkedItems.value = value ? data.value.map(() => true) : data.value.map(() => false);
  isIndeterminate.value = false;
};

const handleItemChange = () => {
  const checkedCount = checkedItems.value.filter(Boolean).length;
  checkAll.value = checkedCount === data.value.length;
  isIndeterminate.value = checkedCount > 0 && checkedCount < data.value.length;
};



/**
 * 获取保养计划列表
 * @param maintenancePlanReq
 * @returns {Promise<void>}
 */
const getMaintenance = async (maintenancePlanReq) => {
  const res = await getMaintenancePlan(maintenancePlanReq);
  if (res.data.flag){
    data.value = res.data.data.maintenanceInfoList;
    total.value = res.data.data.total
  }

};


const equipmentInfo = ref([])

const equipmentMaintenanceType = ref([])

const equipmentStore = useEquipmentInfoStore()
// 初始数据加载
onMounted(async () => {
  await getMaintenance(maintenancePlanReq.value);
  await equipmentStore.getEquipmentInfoList();
  await equipmentStore.getEquipmentMaintenanceTypeList();
  equipmentInfo.value = equipmentStore.equipmentInfo
  equipmentMaintenanceType.value = equipmentStore.equipmentMaintenanceType
});
</script>

<style scoped>
.page-container {
  padding: 20px;
  background-color: white;
  //line-height: 10%;
  //overflow: hidden;
  height: 100%;
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
  //overflow: hidden;
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


@media (max-width: 1200px) {
  .filter-content {
    flex-wrap: wrap;
  }

  .status-filter, .input-search, .date-picker, .button-group {
    width: 100%;
    margin-bottom: 10px;
  }
}


.action-link {
  margin-right: 10px;
  font-weight: bold;
  color: #409EFF;
}

.el-overlay-dialog {
  overflow: hidden;
}

</style>