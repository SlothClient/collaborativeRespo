<template>
  <div class="container">
    <!-- 搜索框和日期选择器 -->
    <div class="filters">
      <el-input
          v-model="searchQuery"
          placeholder="搜索设备名称或描述"
          clearable
          class="search-input"
      />
      <el-date-picker
          v-model="dateRange"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          size="default"
          class="date-picker"
          @change="handleDateChange"
          clearable
      />
      <el-button type="primary"  icon="Search" @click="searchApproval">查询</el-button>
      <el-button  icon="Refresh" @click="resetSearch">重置</el-button>
    </div>

    <!-- 表格 -->
    <el-table
        ref="tableRef"
        :data="filteredData"
        stripe
        border
        style="width: 100%"
        class="device-table"
    >
      <el-table-column prop="date" label="日期" sortable width="180"/>
      <el-table-column prop="deviceName" label="设备名称" width="180"/>
      <el-table-column prop="description" label="描述" :formatter="formatter"/>
      <el-table-column
          prop="status"
          label="状态"
          width="100"
          :filters="statusFilter"
          :filter-method="filterTag"
          filter-placement="bottom-end"
      >
        <template #default="scope">
          <el-tag
              :type="getStatusTagType(scope.row.status)"
              disable-transitions
          >
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>

      <!-- 审批操作列 -->
      <el-table-column label="审批操作" width="180">
        <template #default="scope">
          <el-button @click="approve(scope.row)" type="success" size="small">
            通过
          </el-button>
          <el-button @click="reject(scope.row)" type="danger" size="small">
            驳回
          </el-button>
        </template>
      </el-table-column>

      <!-- 操作列 -->
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button @click="viewDetails(scope.row)" type="primary" size="small">
            查看
          </el-button>
          <el-button @click="editItem(scope.row)" type="success" size="small">
            编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页控件 -->
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
  </div>

</template>

<script setup>
import {ref, computed} from 'vue';

// 表格数据
const tableData = ref([
  {date: '2016-05-03', deviceName: '设备A', description: 'Los Angeles aaa', status: '待审核'},
  {date: '2016-05-02', deviceName: '设备B', description: 'Los Angeles bbb', status: '审核中'},
  {date: '2016-05-04', deviceName: '设备A', description: 'Los Angeles ccc', status: '已通过'},
  {date: '2016-05-01', deviceName: '设备C', description: 'Los Angeles ddd', status: '已驳回'},
  {date: '2016-05-01', deviceName: '设备D', description: 'Los Angeles bbb', status: '已撤销'},
  {date: '2016-05-01', deviceName: '设备F', description: 'Los Angeles eee', status: '已撤销'},
  {date: '2016-05-01', deviceName: '设备G', description: 'Los Angeles fff', status: '已撤销'},
]);

const currentPage = ref(1); // 当前页面
const pageSize = ref(10); // 页面大小
const total = ref(10)
const searchQuery = ref(''); // 搜索框
const dateRange = ref([]); // 日期范围


const statusFilter = ref([
  {text: '待审核', value: '待审核'},
  {text: '审核中', value: '审核中'},
  {text: '已驳回', value: '已驳回'},
  {text: '已撤销', value: '已撤销'}
]);
const searchApproval = ()=>{

}
const resetSearch = () =>{

}


// 过滤后的数据（根据搜索条件）
const filteredData = computed(() => {
  let filtered = tableData.value;

  // 搜索过滤
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase();
    filtered = filtered.filter(item =>
        item.deviceName.toLowerCase().includes(query) ||
        item.description.toLowerCase().includes(query)
    );
  }

  // 日期范围过滤
  if (dateRange.value && dateRange.value.length === 2) {
    const [startDate, endDate] = dateRange.value;
    filtered = filtered.filter(item => {
      const itemDate = new Date(item.date);
      return itemDate >= startDate && itemDate <= endDate;
    });
  }

  return filtered;
});

// 分页切换
const handleCurrentChange = (page) => {
  currentPage.value = page;
};

// 页面大小变化
const handleSizeChange = (size) => {
  pageSize.value = size;
};

// 格式化器
const formatter = (row) => row.description;

// 状态样式
const getStatusTagType = (status) => {
  switch (status) {
    case '待审核':
      return 'warning';
    case '审核中':
      return 'primary';
    case '已通过':
      return 'success';
    case '已驳回':
      return 'danger';
    default:
      return 'info';
  }
};

// 过滤方法
const filterTag = (value, row) => row.status === value;

// 查看详情
const viewDetails = (row) => {
  console.log("查看详情：", row);
  // 可以在此处展示详情弹窗
};

// 编辑设备
const editItem = (row) => {
  console.log("编辑设备：", row);
  // 在此处打开编辑界面
};

// 审批通过
const approve = (row) => {
  row.status = '已通过';
  console.log("已通过审批：", row);
};

// 审批驳回
const reject = (row) => {
  row.status = '已驳回';
  console.log("已驳回审批：", row);
};

// 日期范围变化
const handleDateChange = (dates) => {
  dateRange.value = dates;
};

</script>

<style scoped>
.container {
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  line-height: 10%;
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


</style>