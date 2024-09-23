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
      <el-button type="primary" icon="Search" @click="searchApproval">查询</el-button>
      <el-button icon="Refresh" @click="resetSearch">重置</el-button>
    </div>

    <!-- 表格 -->
    <el-table
        ref="tableRef"
        :data="filteredData"
        stripe
        :border="false"
        style="width: 100%"
        class="device-table"
    >
      <el-table-column prop="date" label="日期" sortable/>
      <el-table-column prop="deviceName" label="设备名称"/>
      <el-table-column prop="description" label="描述" :formatter="formatter"/>
      <el-table-column
          prop="status"
          label="状态"
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
            <div >
              <el-link @click.prevent="viewDetails(scope.row)" type="primary" class="more-action-link">查看详情</el-link>
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
        :currentRow = "currentRow"
    ></ApprovalDetailDrawer>

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
import {ElMessageBox} from "element-plus";
import ApprovalDetailDrawer from "@/components/approval/approvalDetailDrawer.vue";

// 表格数据
const tableData = ref([
  {date: '2016-05-03', creator:"张三",deviceName: '设备A', description: 'Los Angeles aaa', status: '待审核'},
  {date: '2016-05-02', creator:"张三",deviceName: '设备B', description: 'Los Angeles bbb', status: '审核中'},
  {date: '2016-05-04', creator:"张三",deviceName: '设备A', description: 'Los Angeles ccc', status: '已通过'},
  {date: '2016-05-01', creator:"张三",deviceName: '设备C', description: 'Los Angeles ddd', status: '已驳回'},
  {date: '2016-05-01', creator:"张三",deviceName: '设备D', description: 'Los Angeles bbb', status: '已撤销'},
  {date: '2016-05-01', creator:"张三",deviceName: '设备F', description: 'Los Angeles eee', status: '已撤销'},
  {date: '2016-05-01', creator:"张三",deviceName: '设备G', description: 'Los Angeles fff', status: '已撤销'},
]);

const currentPage = ref(1); // 当前页面
const pageSize = ref(10); // 页面大小
const total = ref(10)
const searchQuery = ref(''); // 搜索框
const dateRange = ref([]); // 日期范围
const approvalDetailVisible = ref(false) //详情框是否可见
const currentRow = ref()

const statusFilter = ref([
  {text: '待审核', value: '待审核'},
  {text: '审核中', value: '审核中'},
  {text: '已驳回', value: '已驳回'},
  {text: '已撤销', value: '已撤销'}
]);
const searchApproval = () => {

}
const resetSearch = () => {

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
  approvalDetailVisible.value = true
  currentRow.value = row
  console.log("查看详情：", row);
};

// 编辑设备
const editItem = (row) => {
  console.log("编辑设备：", row);
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


const confirmDelete = (row) => {
  ElMessageBox.confirm('您确定要删除此项吗？', '确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 执行删除操作
    const index = tableData.value.indexOf(row);
    if (index !== -1) {
      tableData.value.splice(index, 1);
      console.log("已删除：", row);
    }
  }).catch(() => {
    console.log("用户取消了删除操作");
  });
};


const closeApprovalDetail = ()=>{

  approvalDetailVisible.value = false
}



</script>

<style scoped>
.container {
  padding: 40px 20px 20px 20px;
  //margin-top: 20px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 1px 12px rgba(0, 0, 0, 0.1);
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

.action-link {
  padding: 6px 12px; /* 增加内边距 */
  border-radius: 4px; /* 圆角边框 */
  transition: background-color 0.3s, color 0.3s; /* 平滑过渡效果 */
}

.action-link:hover {
  background-color: #f0f0f0; /* 悬停时背景色 */
  //color: #409eff; /* 悬停时字体颜色 */
}

.more-action-link {
  //display: block;
  /* 增加选项间距 */
  //padding: 20px;
  margin: 5px 20px 5px 5px;
}


</style>