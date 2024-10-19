<template>
  <div class="container">
    <!-- 搜索框和日期选择器 -->
    <div class="filters">
      <el-input
          v-model="searchQuery"
          placeholder="搜索设备名称或计划名称(admin)"
          clearable
          class="search-input"
      />
      <el-date-picker
          v-model="dateRange"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          format="YYYY/MM/DD HH:mm:ss"
          value-format="YYYY/MM/DD HH:mm:ss"
          size="default"
          class="date-picker"
          @change="handleDateChange"
          clearable
      />
      <el-button type="primary" icon="Search" @click="searchApproval">查询</el-button>
      <el-button icon="Refresh" @click="resetSearch">重置</el-button>
    </div>

    <!-- 表格 -->
    <el-table
        @filter-change="handleFilterChange"
        ref="tableRef"
        :data="tableData"
        stripe
        :border="true"
        style="width: 100%"
        class="device-table"
    >
      <el-table-column prop="createTime" label="创建日期" sortable/>
      <el-table-column
          prop="maintenanceName"
          label="计划名称"
      />
      <el-table-column
          :filters="equipNameFilter"
          column-key="equipId"
          prop="equipName"
          label="设备名称"
      />
      <el-table-column
          column-key="maintenanceTypeId"
          prop="maintenanceType"
          label="保养类型"
          :filters="maintenanceTypeFilter"
      />
      <el-table-column
          prop="planStatus"
          label="状态"
          column-key="planStatus"
          :filters="statusFilter"
          filter-placement="bottom-end"
      >
        <template #default="scope">
          <el-tag
              :type="getStatusTagType(scope.row.planStatus)"
              disable-transitions
          >
            {{ statusMap[scope.row.planStatus] }}
          </el-tag>
        </template>
      </el-table-column>

      <!-- 操作列 -->
      <el-table-column label="操作">
        <template #default="scope">
          <div style="display: flex; justify-content: space-between; align-items: center;">
            <!-- 审批和驳回操作
            当前页面为admin所拥有
            1.当前计划的状态处于审批中
            2.该登陆用户的对于该审批状态为待审批或审批中 -->
            <div style="display: flex;" v-if="userStore.user.roles.indexOf('R001') !==-1">
              <el-link
                  v-if="scope.row.myStatus === 0 && scope.row.planStatus === 1 "
                  @click.prevent="handleCommand('approve', scope.row)"
                  type="success"
                  class="action-link"
              >
                <el-icon>
                  <CircleCheck/>
                </el-icon>
                通过
              </el-link>
              <el-link
                  v-if="scope.row.myStatus === 0 && scope.row.planStatus === 1 "
                  @click.prevent="handleCommand('reject', scope.row)"
                  type="danger"
                  class="action-link"
              >
                <el-icon>
                  <CircleClose/>
                </el-icon>
                驳回
              </el-link>
              <!--              下一级处理中-->
              <el-link
                  v-if="scope.row.myStatus === 0 && scope.row.planStatus === 0 "
                  type="warning"
                  class="action-link"
                  disabled
              >
                <el-icon>
                  <CircleCheck/>
                </el-icon>
                下一级处理中
              </el-link>
              <!-- 已处理，
               当该登入用户的审核状态不为0即为已处理，同时当前用户已处理那么整个审核计划不可能在为待审核
               -->
              <el-link
                  v-if="scope.row.myStatus !== 0 || (scope.row.planStatus ===3)"
                  class="action-link"
                  type="info"
                  disabled
              >
                <el-icon style="margin-right: 5px;">
                  <Finished/>
                </el-icon>
                已处理
              </el-link>

            </div>
            <div style="display: flex;" v-else>
              <el-link
                  v-if="scope.row.myStatus === 0 && scope.row.planStatus === 0"
                  @click.prevent="handleCommand('approve', scope.row)"
                  type="success"
                  class="action-link"
              >
                <el-icon>
                  <CircleCheck/>
                </el-icon>
                通过
              </el-link>
              <el-link
                  v-if="scope.row.myStatus === 0 &&scope.row.planStatus === 0"
                  @click.prevent="handleCommand('reject', scope.row)"
                  type="danger"
                  class="action-link"
              >
                <el-icon>
                  <CircleClose/>
                </el-icon>
                驳回
              </el-link>
              <!-- 已处理，
               当前登陆用户已对该计划做出操作，
               该用户对该计划的status不为0
               -->
              <el-link
                  v-if="scope.row.myStatus !== 0"
                  class="action-link"
                  type="info"
                  disabled
              >
                <el-icon style="margin-right: 5px;">
                  <Finished/>
                </el-icon>
                已处理
              </el-link>

            </div>

            <el-popover
                placement="bottom-start"
                :width="160"
                trigger="click"
                popper-class="more-actions-popover"
            >
              <div class="more-actions-content">
                <el-link
                    @click.prevent="viewDetails(scope.row)"
                    type="primary"
                    class="more-action-link"
                >
                  <el-icon>
                    <Document/>
                  </el-icon>
                  详情
                </el-link>
                <el-link
                    type="danger"
                    @click.prevent="confirmDelete(scope.row)"
                    class="more-action-link"
                >
                  <el-icon>
                    <Delete/>
                  </el-icon>
                  删除
                </el-link>
              </div>

              <!-- 更多操作按钮 -->
              <template #reference>
                <el-link type="primary" class="more-link" style="display: flex; align-items: center;">
                  <el-icon>
                    <More/>
                  </el-icon>
                  更多
                </el-link>
              </template>
            </el-popover>
          </div>

        </template>
      </el-table-column>
    </el-table>

    <ApprovalPassDialog
        :visible="approvalOrRejectDialogVisible"
        @closeDialog="handleCloseDialog"
        @onSubmit="handleApproval"
        :currentCommand="currentCommand"
        :title="title"
    >
    </ApprovalPassDialog>

    <!--    详情框-->
    <ApprovalDetailDrawer
        :approvalDetailVisible="approvalDetailVisible"
        @close-approval-detail="closeApprovalDetail()"
        :currentRow="currentRow"
        :title="title"
    ></ApprovalDetailDrawer>

    <!-- 分页控件 -->
    <div style="margin: 10px 0">
      <el-pagination
          :background="true"
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20, 100]"
          large
          :disabled="false"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>

  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {ElMessageBox, ElNotification} from 'element-plus';
import ApprovalDetailDrawer from '@/components/approval/approvalDetailDrawer.vue';
import {
  approvalPass,
  approvalReject,
  deleteApproval,
  getApprovalDetail,
  getApprovalList
} from '@/api/approval/index.js';
import {useEquipmentInfoStore} from '@/store/module/equipmentInfo.js';
import ApprovalPassDialog from "@/components/approval/approvalPassDialog.vue";
import {useUserStore} from "@/store/module/user.js";

const userStore = useUserStore();
const equipmentStore = useEquipmentInfoStore();

const title = ref('')

const tableRef = ref();

// 过滤条件对象
const filters = ref({
  equipId: [],           // 设备 ID
  maintenanceTypeId: [], // 维修类型 ID
  status: [],            // 状态
  searchQuery: '',         // 搜索关键词
  startDate: null,         // 开始日期
  endDate: null,           // 结束日期
  currentPage: 1,          // 当前页码
  pageSize: 10             // 每页显示的记录数
});

const tableData = ref([]);//表格数据
const currentPage = ref(1);//当前页
const pageSize = ref(10);//一页记录数
const total = ref(10);//总记录数
const searchQuery = ref(''); // 搜索框
const dateRange = ref([]); // 日期范围
const approvalDetailVisible = ref(false); // 详情框是否可见
const currentRow = ref(); // 当前行
const equipNameFilter = ref([]);
const maintenanceTypeFilter = ref([]);
const approvalOrRejectDialogVisible = ref(false);
const selectedPlanId = ref(null);
//true表示
const currentCommand = ref('')
// 显示对话框
const handleCommand = (command, row) => {
  title.value = row.maintenanceName
  currentCommand.value = command;
  selectedPlanId.value = row.planId;
  approvalOrRejectDialogVisible.value = true;
};

// 关闭对话框
const handleCloseDialog = () => {
  approvalOrRejectDialogVisible.value = false;
};

// 处理提交操作
const handleApproval = (comment) => {
  if (currentCommand.value === 'approve') {
    // 同意审批逻辑
    approve(selectedPlanId.value, comment);
  } else {
    // 驳回审批逻辑
    reject(selectedPlanId.value, comment);
  }
};

// 审批通过
const approve = async (planId, approvalRemark) => {
  const res = await approvalPass(planId, approvalRemark)
  if (res.data.flag) {
    ElNotification({
      message: res.data.data,
      type: 'success'
    })
    await loadData();
  }
};

// 审批驳回
const reject = async (planId, approvalRemark) => {

  const res = await approvalReject(planId, approvalRemark)
  if (res.data.flag) {
    ElNotification({
      message: res.data.data,
      type: 'success'
    })
  }
  await loadData();
};

// 筛选
const statusFilter = ref([
  {text: '待审批', value: 0},
  {text: '审批中', value: 1},
  {text: '已通过', value: 2},
  {text: '已驳回', value: 3},
]);

const statusMap = {
  0: '待审批',
  1: '审批中',
  2: '已通过',
  3: '已驳回',
  6: '已通过',
  7: '已通过',
  8: '已通过',
};

// 搜索功能
const searchApproval = () => {
  console.log("查询")
  // 将查询条件传给后端
  loadData();
};

// 重置搜索条件
const resetSearch = () => {
  searchQuery.value = '';
  dateRange.value = [];
  tableRef.value.clearFilter()
  clear()
  loadData();
};

// 分页切换
const handleCurrentChange = (page) => {
  currentPage.value = page;
  loadData();
};

// 页面大小变化
const handleSizeChange = (size) => {
  pageSize.value = size;
  loadData();
};

// 状态样式
const getStatusTagType = (status) => {
  switch (status) {
    case 0:
      return 'warning';
    case 1:
      return 'primary';
    case 2:
      return 'success';
    case 6:
      return 'success';
    case 7:
      return 'success';
    case 8:
      return 'success';
    case 3:
      return 'danger';
    default:
      return 'info';
  }
};

// 过滤方法
const handleFilterChange = (newFilters) => {
  // 过滤条件变化时会传递一个 filters 对象，包含所有列的过滤条件
  if (newFilters.equipId) {
    filters.value.equipId = newFilters.equipId
  }
  if (newFilters.planStatus) {
    filters.value.status = newFilters.planStatus
  }
  if (newFilters.maintenanceTypeId) {
    filters.value.maintenanceTypeId = newFilters.maintenanceTypeId
  }

  // console.log(filters.value)
  // 发送请求更新数据
  loadData();
};

// 查看详情
const viewDetails = async (row) => {
  console.log(row)
  const res = await getApprovalDetail(row.planId)
  if (!res.data.flag) {
    ElNotification({
      message: res.data.msg,
      type: 'error'
    })
  } else {
    currentRow.value = res.data.data
    title.value = row.maintenanceName
    approvalDetailVisible.value = true;
  }
};

// 日期范围变化
const handleDateChange = (dates) => {
  dateRange.value = dates;
  loadData();
};

// 删除操作确认
const confirmDelete = (row) => {
  ElMessageBox.confirm('您确定要删除此项吗？', '确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
      .then(async () => {
        const res = await deleteApproval(row.planId)
        if(res.data.flag){
          ElNotification({
            message:res.data.data,
            type:"success"
          })
        }
        await loadData()
      })
      .catch(() => {
        console.log('用户取消了删除操作');
      });
};

// 关闭详情框
const closeApprovalDetail = () => {
  approvalDetailVisible.value = false;
};

const clear = () => {
  filters.value.currentPage = 1
  filters.value.endDate = null
  filters.value.equipId = []
  filters.value.maintenanceTypeId = []
  filters.value.pageSize = 10
  filters.value.searchQuery = null
  filters.value.startDate = null
  filters.value.status = []

}

// 统一加载数据
const loadData = async () => {

  const param = {
    searchQuery: searchQuery.value,
    startDate: dateRange.value[0],
    endDate: dateRange.value[1],
    currentPage: currentPage.value,
    pageSize: pageSize.value,
    equipId: filters.value.equipId,
    maintenanceTypeId: filters.value.maintenanceTypeId,
    status: filters.value.status,
  }

  const res = await getApprovalList(param);

  if (!res.data.flag) {
    ElNotification({
      message: res.data.msg,
      type: 'error',
    });
  } else {
    tableData.value = res.data.data.records;
    total.value = res.data.data.total;
  }
};

// 初始数据加载
onMounted(async () => {
  await equipmentStore.getEquipmentInfoList();
  await equipmentStore.getEquipmentMaintenanceTypeList();

  equipNameFilter.value = equipmentStore.equipmentInfo.map((equipment) => ({
    text: equipment.equipName,
    value: equipment.equipId,
  }));

  maintenanceTypeFilter.value = equipmentStore.equipmentMaintenanceType.map(
      (maintenanceType) => ({
        text: maintenanceType.maintanceName,
        value: maintenanceType.typeId,
      })
  );

  await loadData();
});
</script>

<style scoped>
.container {
  padding: 40px 20px 20px 20px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 1px 12px rgba(0, 0, 0, 0.1);
}

.filters {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.search-input {
  width: 250px;
}

.date-picker {
  width: 250px;
}

.device-table {
  margin-bottom: 20px;
}

.action-link {
  padding: 6px 12px;
  border-radius: 4px;
  transition: background-color 0.3s, color 0.3s;
}

.more-link {
  padding: 6px 12px;
  border-radius: 4px;
  transition: background-color 0.3s, color 0.3s;
  display: flex;
  justify-content: flex-end; /* 确保更多按钮在最右边 */
}

.action-link:hover {
  background-color: #f0f0f0;
}

.more-action-link {
  margin: 5px 20px 5px 5px;

}
</style>