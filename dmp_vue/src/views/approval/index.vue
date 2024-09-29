<template>
  <div class="container">
    <!-- 搜索框和日期选择器 -->
    <div class="filters">
      <el-input
          v-model="searchQuery"
          placeholder="搜索设备名称或计划名称"
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
        :border="false"
        style="width: 100%"
        class="device-table"
    >
      <el-table-column prop="createTime" label="日期" sortable/>
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
          <el-link @click.prevent="approve(scope.row)" type="success" class="action-link">通过</el-link>
          <el-link @click.prevent="reject(scope.row)" type="danger" class="action-link">驳回</el-link>
          <el-popover
              placement="bottom-start"
              :width="160"
              trigger="click"
          >
            <div>
              <el-link @click.prevent="viewDetails(scope.row)" type="primary" class="more-action-link">查看详情
              </el-link>
              <el-link type="danger" @click.prevent="confirmDelete(scope.row)" class="more-action-link">删除</el-link>
            </div>
            <template #reference>
              <el-link type="primary" class="action-link" icon="ArrowDown">
                更多
              </el-link>
            </template>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>

    <!--    详情框-->
    <ApprovalDetailDrawer
        :approvalDetailVisible="approvalDetailVisible"
        @close-approval-detail="closeApprovalDetail()"
        :currentRow="currentRow"
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
import {getApprovalDetail, getApprovalList} from '@/api/approval/index.js';
import {useEquipmentInfoStore} from '@/store/module/equipmentInfo.js';

const equipmentStore = useEquipmentInfoStore();

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
  const res = await getApprovalDetail(row.planId)
  if(!res.data.flag){
    ElNotification({
      message:res.data.msg,
      type:'error'
    })
  }else {
    currentRow.value = res.data.data
    approvalDetailVisible.value = true;
  }
};

// 审批通过
const approve = (row) => {
  row.status = '已通过';
};

// 审批驳回
const reject = (row) => {
  row.status = '已驳回';
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
      .then(() => {
        const index = tableData.value.indexOf(row);
        if (index !== -1) {
          tableData.value.splice(index, 1);
        }
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

  loadData();
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

.action-link:hover {
  background-color: #f0f0f0;
}

.more-action-link {
  margin: 5px 20px 5px 5px;
}
</style>