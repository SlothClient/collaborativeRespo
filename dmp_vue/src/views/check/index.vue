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
          <el-input v-model="checkName" placeholder="输入计划名称" size="small" class="input-search">
            <template #prepend>计划名称:</template>
          </el-input>
          <el-date-picker
              v-model="dateRange"
              type="datetimerange"
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
                 @click="isCheckInfoAddVisible = !isCheckInfoAddVisible" class="add-btn">新增
      </el-button>
      <el-button type="primary" size="small" icon="User" class="add-btn" @click="openDispatchOrderDialog">
        分派人员
      </el-button>
      <el-button type="primary" size="small" icon="Filter" class="add-btn"
                 @click="advancedSearchDialogVisible =!advancedSearchDialogVisible">高级查询
      </el-button>
    </el-row>
    <!-- 数据展示-->
    <el-table :data="data" stripe style="width: 100%;" @selection-change="handleItemChange" ref="tableRef">
      <el-table-column type="selection" width="28" ></el-table-column>

      <el-table-column label="点巡检计划信息">
        <template #default="scope">
          <el-card>
            <el-row :gutter="12">
              <el-col :span="19">
                <el-descriptions :column="2" size="small">
                  <el-descriptions-item label="计划名称">{{ scope.row.checkName }}</el-descriptions-item>
                  <el-descriptions-item label="计划编号">{{ scope.row.checkId }}
                    <el-tag
                        size="small"
                        :class="getStatusClass(scope.row.status)"
                    >
                      {{ getStatusLabel(scope.row.status) }}
                    </el-tag>
                  </el-descriptions-item>
                  <el-descriptions-item label="开始时间">{{ scope.row.startTime }}</el-descriptions-item>
                  <el-descriptions-item label="结束时间">{{ scope.row.endTime }}</el-descriptions-item>
                  <el-descriptions-item label="点巡检描述" >{{scope.row.checkDesc}}</el-descriptions-item>
                  <el-descriptions-item label="点巡检位置" >{{scope.row.checkSite}}</el-descriptions-item>
                  <el-descriptions-item label="点巡检内容" >{{scope.row.checkContent}}</el-descriptions-item>
                </el-descriptions>
              </el-col>
            </el-row>
            <el-row class="info-row" :gutter="10">
              <el-col :span="6">
                <span class="info-item"><label>创建时间：</label>{{ scope.row.createTime }}</span>
              </el-col>
              <el-col :span="6">
                <span class="info-item"><label>创建人：</label>{{ scope.row.creator }}</span>
              </el-col>
              <el-col :span="6">
                <span class="info-item"><label>更新时间：</label>{{ scope.row.updateTime }}</span>
              </el-col>
              <el-col :span="6">
                <span class="info-item"><label>更新人：</label>{{ scope.row.updatePerson }}</span>
              </el-col>
            </el-row>
          </el-card>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="100">
        <template #default="scope">
          <div class="action-buttons">
            <el-link type="primary" @click="openEditDialog(scope.row)">
              <el-icon>
                <Edit/>
              </el-icon>
              编辑
            </el-link>
            <el-link type="primary" @click="openDetailDialog(scope.row.checkId)">
              <el-icon>
                <Document/>
              </el-icon>
              详情
            </el-link>
            <el-link type="primary" @click.prevent="handleDelete(scope.row)">
              <el-icon>
                <Delete/>
              </el-icon>
              撤销
            </el-link>
          </div>
        </template>
      </el-table-column>
    </el-table>

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
    <AdvancedSearchDialog
        :advancedSearchDialogVisible="advancedSearchDialogVisible"
        @close-advanced-search-dialog="closeAdvancedSearchDialog"
        @advanced-search-plan="advancedSearchPlan"
    ></AdvancedSearchDialog>
    <!--    详情框-->
    <check-info-detail-dialog
        :checkInfoDetailVisible ="isCheckInfoDetailVisible"
        @close-dialog="closeDetailDialog()"
        :checkInfoDetail="currentRowDetail"
    ></check-info-detail-dialog>
    <!--    增加框-->
    <check-info-add-dialog
        :checkInfoAddVisible ="isCheckInfoAddVisible"
        @close-dialog="closeAddDialog()"
        :equipmentInfoList="equipmentInfo"
        @add-check-info="addCheck"
    ></check-info-add-dialog>
    <!--    修改框-->
    <check-info-edit-dialog
        :checkInfoEditVisible ="isCheckInfoEditVisible"
        @close-dialog="closeEditDialog()"
        :currentRow="currentRowEdit"
        :equipmentInfoList="equipmentInfo"
        @edit-check-info="editCheckInfo"
    ></check-info-edit-dialog>
    <!--    派单-->
    <dispatch-order
        :visible="dispatchVisible"
        @close-dialog="closeDispatchDialog"
        :selectedPlan="selectedPlan"
        @dispatch-order="addOrder"
        @remove-selected-plan="removeSelectedPlan"
    >
    </dispatch-order>
  </div>
</template>
<script setup>
import{getCheckInfo,addCheckInfo,undoCheckInfo,getCheckInfoDetail,updateCheck,addWorkOrder} from "@/api/check/index.js"
import {onMounted, ref} from "vue";
import CheckInfoEditDialog from "@/components/check/checkInfoEditDialog.vue";
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";
import {useEquipmentInfoStore} from "@/store/module/equipmentInfo.js";
import CheckInfoAddDialog from "@/components/check/checkInfoAddDialog.vue";
import CheckInfoDetailDialog from "@/components/check/checkInfoDetailDialog.vue";
import DispatchOrder from "@/components/check/dispatchOrder.vue";
import AdvancedSearchDialog from "@/components/check/advancedSearchDialog.vue";
const tableRef = ref(null)
// //禁用多选框
// const selectable = (row, index) => {
//   return row.status === 2;
//
// }
//派单
const dispatchVisible = ref(false)
const closeDispatchDialog = () => {
  dispatchVisible.value = false
}
const openDispatchOrderDialog = () => {
  dispatchVisible.value = true
  console.log(selectedPlan)
}

const selectedPlan = ref([])
const handleItemChange = (selection) => {
  selectedPlan.value = selection

};
//移除相关计划
const removeSelectedPlan = (index, checkId) => {
  const removedPlan = data.value.find(item => item.checkId === checkId);  // 找到要删除的计划

  // 找到 selectedPlan 中对应的项并删除
  const planIndex = selectedPlan.value.findIndex(check => check.checkId === removedPlan.checkId);
  if (planIndex !== -1) {
    selectedPlan.value.splice(planIndex, 1);  // 同步移除
  }

  // 取消表格中该行的选中状态
  if (tableRef.value) {
    const row = data.value.find(item => item.checkId === removedPlan.checkId);
    if (row) {
      tableRef.value.toggleRowSelection(row, false);  // 取消选中
    }
  }
};

//派单
const addOrder = async (param) => {
  console.log('父组件添加', param)
  const res = await addWorkOrder(param)
  if (res.data.flag) {
    ElNotification({
      message: res.data.data,
      type: "success"
    })
    getCheck(checkInfoReq.value);
  }
}
//高级搜索
const advancedSearch = ref(null)
const advancedSearchDialogVisible = ref(false)

const closeAdvancedSearchDialog = () => {
  advancedSearch.value = null;
  advancedSearchDialogVisible.value = false
}
const advancedSearchPlan = (param) => {
  checkInfoReq.value.checkName = param.checkName
  checkInfoReq.value.status = param.status
  checkInfoReq.value.startTime = param.startTime
  checkInfoReq.value.endTime = param.endTime
  checkInfoReq.value.equipId = param.equipId
  checkInfoReq.value.creator = param.creator
  checkInfoReq.value.currentPage = 1
  checkInfoReq.value.pageSize = 3
  getCheck(checkInfoReq.value);
}
// 状态映射
const statusOptions = [
  {label: '全部', value: -1},
  {label: '待开始', value: 2},
  {label: '已派单', value: 6},
  {label: '执行中', value: 7},
  {label: '已完成', value: 8}
];
const getStatusLabel = (status) => {
  switch (status) {
    case 0:
      return '待审批';
    case 1:
      return '审批中';
    case 2:
      return '待开始';
    case 3:
      return '已驳回';
    case 6:
      return '已派单';
    case 7:
      return '执行中';
    case 8:
      return '已完成';
    default:
      return '未知状态'
  }
}
const getStatusClass = (status) => {
  switch (status) {
    case 0:
      return 'status-pending';       // 待审批
    case 1:
      return 'status-in-approval';   // 审批中
    case 2:
      return 'status-to-start';      // 待开始
    case 3:
      return 'status-rejected';      // 已驳回
    case 6:
      return 'status-assigned';      // 已派单
    case 7:
      return 'status-in-progress';   // 执行中
    case 8:
      return 'status-completed';     // 已完成
    default:
      return 'status-unknown';       // 未知状态
  }
}
const currentRowEdit = ref(null)
const currentRowDetail = ref(null)
const checkboxGroup1 = ref([-1]); // 默认选择全部
const checkName = ref("");
const dateRange = ref([]);
//删除
const handleDelete = (plan) => {
  ElMessageBox.confirm(
      '你确定要撤销' + plan.checkName,
      '删除计划',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(async () => {
        const res = await undoCheckInfo(plan.checkId)

        if (res.data.flag) {
          ElNotification({
            message: res.data.data,
            type: "success"
          })
          await getCheck(checkInfoReq.value);
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

const data = ref([
  // 示例数据
]);
//搜索请求
const checkInfoReq = ref({
  currentPage: 1,
  pageSize: 3,
  checkName: '',
  startTime: null,
  endTime: null,
  status: [],
  equipId: '',
  creator: ''
});
//分页
const currentPage = ref(1);
const pageSize = ref(3);
const total = ref(data.value.length);

const handleCurrentChange = (newPage) => {
  checkInfoReq.value.currentPage = newPage;
  getCheck(checkInfoReq.value);
};

const handleSizeChange = (newPageSize) => {
  checkInfoReq.value.pageSize = newPageSize;
  getCheck(checkInfoReq.value);
};
//搜索
const searchPlans = () => {
  checkInfoReq.value.checkName = checkName.value;
  checkInfoReq.value.startTime = dateRange.value[0];
  checkInfoReq.value.endTime = dateRange.value[1];
  checkInfoReq.value.currentPage = 1;
  if (checkboxGroup1.value.includes(-1)) {
    checkInfoReq.value.status = [] //statusOptions.slice(1).map(option => option.value);
  } else {
    checkInfoReq.value.status = checkboxGroup1.value;
  }
  getCheck(checkInfoReq.value);
  currentPage.value = 1
};
//重置
const clear = () => {
  checkboxGroup1.value = [-1];
  checkName.value = '';
  dateRange.value = [];
  checkInfoReq.value.checkName = '';
  checkInfoReq.value.startTime = null;
  checkInfoReq.value.endTime = null;
  checkInfoReq.value.status = [];
  checkInfoReq.value.equipId = ''
  checkInfoReq.value.creator = ''
  checkInfoReq.value.currentPage = 1
  checkInfoReq.value.pageSize = 3
}
const resetFilters = () => {
  clear()
  getCheck(checkInfoReq.value);
};
// 详情框
const isCheckInfoDetailVisible = ref(false);
const closeDetailDialog = () => {
  isCheckInfoDetailVisible.value = false;
};
const openDetailDialog = async (checkId) => {
  console.log(checkId)
  const res = await getCheckInfoDetail(checkId)
  console.log(res)
  if (!res.data.flag) {
    ElNotification({
      message: res.data.data,
      type: "error"
    })
    return
  }
  currentRowDetail.value = res.data.data
  console.log(currentRowDetail)
  isCheckInfoDetailVisible.value = true;
  console.log(isCheckInfoDetailVisible)
};
// 修改
const isCheckInfoEditVisible = ref(false);
const closeEditDialog = () => {
  isCheckInfoEditVisible.value = false;
};
const openEditDialog = (row) => {
  currentRowEdit.value = row
  console.log(row)
  isCheckInfoEditVisible.value = true;
};

const editCheckInfo = async (val) => {
  console.log(val)
  const editPlan = {
    equipId: val.equipId,
    checkName: val.checkName,
    startTime: val.startTime,
    endTime: val.endTime,
    checkDesc: val.checkDesc,
    checkSite: val.checkSite,
    checkContent: val.checkContent,
    checkId: val.checkId
  }
  const res = await updateCheck(editPlan)
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
    await getCheck(checkInfoReq.value)
  }

}
// 添加
const isCheckInfoAddVisible = ref(false);
const closeAddDialog = () => {
  isCheckInfoAddVisible.value = false;
};
/**
 * 添加保养计划
 * @param Plan
 * @returns {Promise<void>}
 */
const addCheck = async (Plan) => {
  const res = await addCheckInfo(Plan);
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
    await getCheck(checkInfoReq.value)
  }

}
/**
 * 获取保养计划列表
 * @param checkInfoReq
 * @returns {Promise<void>}
 */
const getCheck = async (checkInfoReq)=>{
  const res = await getCheckInfo(checkInfoReq)
  console.log(res)
  if (res.data.flag) {
    data.value = res.data.data.records;
    total.value = res.data.data.total
  }
}

const equipmentInfo = ref([])

const equipmentStore = useEquipmentInfoStore()

onMounted(async ()=>{
  await getCheck(checkInfoReq.value);
  await equipmentStore.getEquipmentInfoList();
  await equipmentStore.getWorkerInfo();
  equipmentInfo.value = equipmentStore.equipmentInfo
})
</script>
<style scoped>
.page-container {
  padding: 20px;
  background-color: white;
}

.filter-container {
  padding: 20px;
  background-color: #ffffff;
  margin-bottom: 0;
  border-radius: 8px;
}

.filter-content {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
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
  min-width: 150px;
  max-width: 300px;
  flex-shrink: 0;
}

.date-picker {
  width: 30%;
  min-width: 250px;
  max-width: 400px;
  flex-shrink: 0;
}

.button-group {
  display: flex;
  gap: 10px;
  flex-shrink: 0;
}

.add-button-row {
  margin-bottom: 20px;
  margin-left: 1%;
}

.add-btn {
  float: right;
  min-width: 80px;
  width: 100px;
}

.el-table {
  width: 100%;
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

.el-pagination {
  margin: 10px 0;
}

@media (max-width: 768px) {
  .filter-content {
    flex-direction: column;
    align-items: flex-start;
  }

  .status-filter, .input-search, .date-picker, .button-group {
    width: 100%;
    margin-bottom: 10px;
  }

  .add-button-row {
    margin-left: 0;
    text-align: center;
  }
}

.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 8px;
  align-items: flex-start;
}

.el-link {
  display: flex;
  align-items: center;
}

.el-icon {
  margin-right: 5px;
  font-size: 14px;
}

/* 状态样式定义 */

.status-pending {
  background-color: #e0e0e0;
  color: #606266;
  border: 1px solid #bfbfbf;
  border-radius: 4px;
  padding: 2px 8px;
}

.status-in-approval {
  background-color: #fdf6ec;
  color: #e6a23c;
  border: 1px solid #e6a23c;
  border-radius: 4px;
  padding: 2px 8px;
}

.status-to-start {
  background: linear-gradient(90deg, #e6e6e6, #dcdcdc);
  color: #606266;
  border: 1px dashed #bfbfbf;
  border-radius: 4px;
  font-weight: bold;
  animation: pulse 2s infinite;
}

.status-rejected {
  background-color: #fef0f0;
  color: #f56c6c;
  border: 1px solid #f56c6c;
  border-radius: 4px;
  padding: 2px 8px;
}

.status-assigned {
  background-color: #ecf5ff;
  color: #409eff;
  border: 1px solid #409eff;
  border-radius: 4px;
  padding: 2px 8px;
}

.status-in-progress {
  background-color: #fdf6ec;
  color: #e6a23c;
  border: 1px solid #e6a23c;
  border-radius: 4px;
  padding: 2px 8px;
}

.status-completed {
  background-color: #f0f9eb;
  color: #67c23a;
  border: 1px solid #67c23a;
  border-radius: 4px;
  padding: 2px 8px;
}

.status-unknown {
  background-color: #e0e0e0;
  color: #606266;
  border: 1px solid #bfbfbf;
  border-radius: 4px;
  padding: 2px 8px;
}

@keyframes pulse {
  0% {
    box-shadow: 0 0 0px rgba(102, 102, 102, 0.7);
  }
  50% {
    box-shadow: 0 0 10px rgba(102, 102, 102, 0.7);
  }
  100% {
    box-shadow: 0 0 0px rgba(102, 102, 102, 0.7);
  }
}

</style>