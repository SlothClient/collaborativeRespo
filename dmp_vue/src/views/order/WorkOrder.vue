<template>
  <!-- 工单页容器 -->
  <div id="order">
    <!-- 搜索、筛选元素容器 -->
    <div class="filter">
      <span class="descWords">工单状态：</span>
      <!-- 工单状态组容器 -->
      <div id="statusGroup">
        <el-button-group class="ml-4">
          <el-button :class="{ active: statusFilter === 'no' }" @click="fetchOrders()">全部
          </el-button>
          <el-button :class="{ active: statusFilter === 'not_started' }"
                     @click="fetchOrders('not_started')">未开始
          </el-button>
          <el-button :class="{ active: statusFilter === 'in_progress' }"
                     @click="fetchOrders('in_progress')">保养中
          </el-button>
          <el-button :class="{ active: statusFilter === 'completed' }" @click="fetchOrders('completed')">已完成
          </el-button>
        </el-button-group>
      </div>
      <span class="descWords">工单编号：</span>
      <el-input v-model="orderId" style="width: 200px" placeholder="请输入工单编号" :prefix-icon="Search" clearable
                @keyup.enter="fetchOrders(statusFilter)"/>
      <span class="descWords">时间范围：</span>
      <el-date-picker v-model="orderSpan" type="datetimerange" start-placeholder="Start date"
                      end-placeholder="End date" format="YYYY-MM-DD HH:mm:ss" date-format="YYYY/MM/DD ddd"
                      time-format="A hh:mm:ss" style="width: 200px"/>
      <el-button type="primary" :icon="Search" style="margin-left: 10px;"
                 @click="fetchOrders(statusFilter)">查询
      </el-button>
      <el-button type="primary" :icon="Refresh" @click="resetFilters">重置</el-button>
    </div>
    <!-- 工单记录展示容器 -->
    <div class="orders">
      <!--  v-if="pickedNum > 0" -->
      <!-- 选中提示框容器 -->
      <div id="pickedPrompt">
        <div class="picked">
          已选择<span>{{ pickedNum }}</span>项
        </div>
        <div class="clear" @click="clearSelection">
          清空
        </div>
      </div>
      <!-- 工单记录容器 -->
      <el-table :data="orderTable" style="width: 99%;margin: 10px;" :stripe="true" :border="true"
                @selection-change="handleSelectionChange" ref="orderTableRef">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column property="id" label="保养工单信息" width="880" align="center">
          <template #default="scope">
            <div class="flex-container">
              <div>计划名称：<span>{{ scope.row.planName }}</span></div>
              <div>计划时间：<span>{{ scope.row.planTime }}</span></div>
              <div>设备编号：<span>{{ scope.row.equipId }}</span></div>
              <button :class="getOrderStatus(scope.row).class" class="statusBtn">{{
                  getOrderStatus(scope.row).desc
                }}
              </button>
              <br/>
              <!-- <div>标准工时：<span>{{ scope.row.标准工时 }}</span></div> -->
              <div>负责人员：<span>{{ scope.row.workerId }}-{{ scope.row.workerName }}</span></div>
              <div>工单备注：<span>{{ scope.row.orderDesc }}</span></div>
              <br/>
              <div>派单时间：<span>{{ scope.row.startTime }}</span></div>
              <div>派单人：<span>{{ '暂无' }}</span></div>
              <div>更新时间：<span>{{ '暂无' }}</span></div>
              <div>更新人：<span>{{ '暂无' }}</span></div>
              <!-- <div>工作记录：<span>{{ scope.row.工作记录 }}</span></div> -->
            </div>
          </template>
        </el-table-column>
        <!-- <el-table-column property="id" label="工单编号" width="120" />
        <el-table-column property="name" label="工单描述" width="120" />
        <el-table-column property="overflow" label="溢出处理" width="240"
            show-overflow-tooltip />
        <el-table-column property="record" label="工单记录" /> -->
        <el-table-column label="操作" align="center">
          <!-- 后期修改成图标，悬停显示描述 -->
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)" type="primary">
              工单详情
            </el-button>
            <el-dropdown>
                            <span class="el-dropdown-link">
                                更多
                                <el-icon class="el-icon--right">
                                    <arrow-down/>
                                </el-icon>
                            </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item
                      @click="handleWorkRecord(scope.$index, scope.row)">工作记录
                  </el-dropdown-item>
                  <el-dropdown-item @click="handleSubmit(scope.row)">提交工单</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页容器 -->
      <div id="pagination">
        <span style="margin-right: 20px;">共<span style="color: #409eff;">{{ totalOrders }}</span>条</span>
        <!-- 直接使用:报错，无法显示分页器，使用v-model:不报错 -->
        <el-pagination background layout="prev, pager, next, sizes" @change="fetchOrders(statusFilter)"
                       v-model:current-page="currentPage" v-model:page-size="pageSize" v-model:total="totalOrders"
                       :page-sizes="[3, 5, 10]"/>
      </div>
    </div>
    <!-- 详情框组件 -->
    <detailDialog :dialogVisible="dialogVisible" :selectedOrder="selectedOrder"
                  @update:dialogVisible="dialogVisible = $event"/>
    <!-- 工作记录框组件 -->
    <recordDialog :recordDialogVisible="recordDialogVisible" :selectedOrder="selectedOrder"
                  @update:recordDialogVisible="recordDialogVisible = $event"/>
  </div>
</template>
<script setup>
import {Refresh, Search} from '@element-plus/icons-vue';
import {ref, onMounted, computed} from 'vue';
import axios from 'axios';
import {ElMessage, ElMessageBox} from 'element-plus'; // 导入 ElMessage
import detailDialog from '@/components/workOrder/detailDialog.vue'; // 导入详情对话框组件
import recordDialog from '@/components/workOrder/recordDialog.vue';
import {useLoadingStore} from "@/store/module/loadingStore.js"; // 导入工作记录对话框组件


const loading = useLoadingStore();


const orderId = ref('');
const orderSpan = ref([]);
const orderTable = ref([]);
const pickedNum = ref(0);
const statusFilter = ref('no');
const selectedRows = ref([]); // 新增一个变量来追踪选中的行
const orderTableRef = ref(null); // 引用 el-table 组件

const currentPage = ref(1); // 当前页码
const pageSize = ref(10); // 每页显示的条目数
const totalOrders = ref(0); // 总条目数
/**
 * 异步获取订单数据
 *
 * @param status 订单状态，默认为'no'，表示获取所有订单；'not_started'表示未开始，'in_progress'表示进行中，'completed'表示已完成
 * @returns 无返回值
 */
const fetchOrders = async (status = 'no') => {
  const condition = {
    offset: (currentPage.value - 1) * pageSize.value,
    limit: pageSize.value,
    orderId: orderId.value ? orderId.value : null, // 添加工单编号
    startTime: orderSpan.value[0] ? orderSpan.value[0].getTime() : null, // 开始时间
    endTime: orderSpan.value[1] ? orderSpan.value[1].getTime() : null, // 结束时间
  };

  // 激活按钮，显示被选中
  statusFilter.value = status;

  let formData = new FormData();
  formData.append("conditionJson", JSON.stringify(condition));

  try {
    loading.startLoading();
    const response = await axios.post('/api/getOrdersByPage', formData);
    loading.stopLoading()
    if (response.data.status) {
      orderTable.value = response.data.list; // 接收后端数据
      totalOrders.value = response.data.totalCount; // 存储总条目数

      // 根据激活（选中）状态筛选数据，默认状态是全部，即不筛选
      if (status) {
        orderTable.value = orderTable.value.filter(order => {
          const now = new Date();
          const startTime = new Date(order.startTime);
          // bug:endTime为null时，new Date(order.endTime)创建出来是标准时间仍然是对象而非null
          // const endTime = new Date(order.endTime);
          // 修复bug：事先判断
          const endTime = order.endTime ? new Date(order.endTime) : null;

          switch (status) {
            case 'not_started':
              return now < startTime;
            case 'in_progress':
              // 未结单就是执行中
              return endTime === null ? (now >= startTime) : (now >= startTime && now < endTime);
              // return now >= startTime && now <= endTime;
            case 'completed':
              // 未结单就是执行中，不返回
              return endTime === null ? (null) : (now > endTime);
              // return now > endTime;
            default:
              return true; // 工单状态为全部，不筛选
          }
        });
        ElMessage.success("查询筛选成功！");
      }
    } else {
      // 错误处理，但此处不一定为错误导致，可能没有符合筛选条件的数据，除了错误提示之外也要接收数据
      orderTable.value = response.data.list;
      totalOrders.value = response.data.totalCount;
      ElMessage.error(response.data.msg);
    }
  } catch (error) {
    ElMessage.error("获取工单时出错，请稍后再试！");
  }
};

// 页面加载，获取订单
onMounted(() => {
  fetchOrders();
});

/**
 * 重置过滤器
 *
 * 重置订单ID、订单时间范围和订单状态过滤器，并重新请求订单数据。
 */
const resetFilters = () => {
  orderId.value = '';
  orderSpan.value = [];
  statusFilter.value = '';
  fetchOrders();
};

/**
 * 处理记录选中事件
 *
 * @param val 当前选择的行数据数组
 */
const handleSelectionChange = (val) => {
  selectedRows.value = val; // 更新选中的行
  pickedNum.value = val.length; // 更新已选数量
};

/**
 * 清除所有选中项
 *
 * 清除订单表中的所有选中项，并重置已选数量和选中的行数组
 */
const clearSelection = () => {
  if (orderTableRef.value) {
    orderTableRef.value.clearSelection(); // 清除所有选中的行
  }
  pickedNum.value = 0; // 重置已选数量
  selectedRows.value = []; // 清空选中的行
};

// 查询，工单编号、时间范围，选择几个条件查询几个条件，不选择默认无条件（空条件）查询，即查询所有记录

// -----------------------------------------详情框-----------------------------------------
const dialogVisible = ref(false);
const selectedOrder = ref(new Object);
/**
 * 处理编辑事件
 *
 * @param {number} index - 当前行的索引
 * @param {Object} row - 当前行的数据对象
 */
const handleEdit = (index, row) => {
  // 打印查看
  // console.log(index, row);
  // 打开详情框
  dialogVisible.value = true;
  // 发送选中数据到详情框组件
  selectedOrder.value = row;
};

// -----------------------------------------工作记录框-----------------------------------------
const recordDialogVisible = ref(false);
/**
 * 处理工作记录事件
 *
 * @param {number} index - 当前行的索引
 * @param {Object} row - 当前行的数据对象
 */
const handleWorkRecord = (index, row) => {
  // 打印查看
  // console.log(index, row);
  // 打开详情框
  recordDialogVisible.value = true;
  // 发送选中数据到详情框组件
  selectedOrder.value = row;
};
// -----------------------------------------临时提交工单框-----------------------------------------
const handleSubmit = (row) => {
  ElMessageBox.confirm('请确认提交', '提示')
      .then(() => {
        submitOrder(row);
      })
}

const submitOrder = async (row) => {
  const condition = {
    orderId: row.orderId,
    workerId: row.workerId,
    startTime: new Date(row.startTime).toISOString(),
    endTime: new Date().toISOString()
  }
  const formData = new FormData();
  formData.append('conditionJson', JSON.stringify(condition));
  try {
    loading.startLoading();
    const response = await axios.post('/api/submitOrder', formData);
    loading.stopLoading();
    if (response.data.status) {
      ElMessage.success("提交成功！");
      fetchOrders(); // 提交成功后刷新工单列表
    } else {
      ElMessage.error(response.data.msg);
    }
  } catch (err) {
    ElMessage.error("请求错误！" + err);
  }
}
// -----------------------------------------工单状态按钮-----------------------------------------
const getOrderStatus = (row) => {
  const now = new Date();
  const startTime = new Date(row.startTime);
  const endTime = row.endTime ? new Date(row.endTime) : null;

  if (startTime > now) {
    return {
      desc: "未开始",
      class: "pendingBtn"
    };
  } else if (endTime == null || endTime > now) {
    return {
      desc: "进行中",
      class: "processingBtn"
    };
  } else {
    return {
      desc: "已完成",
      class: "finishBtn"
    };
  }
}
</script>
<style scoped>
.filter {
  padding: 20px;
  display: flex;
  /* height: 32px; */
  justify-content: space-between;
  align-items: center;
}

.filter .descWords:not(:first-child) {
  margin: 0 0 0 10px;
}

/* 修复组件宽度收缩span内文字换行 */
.filter .descWords {
  min-width: fit-content;
}

.cell div span {
  color: rgb(71, 196, 238);
}

#pickedPrompt {
  background-color: rgba(64, 158, 255, .3);
  border: 1px solid rgb(64, 158, 255);
  border-radius: 5px;
  margin: 10px;
  padding: 10px 30px;
  display: flex;
  position: relative;
  color: rgb(157, 157, 157);
}

#pickedPrompt .clear {
  position: absolute;
  right: 30px;
  color: rgb(64, 158, 255);
  cursor: pointer;
}

#pickedPrompt .picked span {
  color: rgb(64, 158, 255);
}

.flex-container {
  /* display: flex;
  flex-wrap: wrap;
  gap: 10px; */
  text-align: left;
  position: relative;
}

.flex-container > div {
  display: inline-block;
  margin: 10px 5px 10px;
}

.id,
.name,
.record {
  flex: 1;
  min-width: 200px;
}

.active {
  background-color: #409EFF;
  color: white;
}

#pagination-deprecated {
  width: fit-content;
  /* 调整分页容器内样式，内部容器左右排列，上下水平居中 */
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  /* 固定位置 */
  position: absolute;
  bottom: 50px;
  left: 50%;
  transform: translateX(-50%);
  /* 向左偏移50%以达到居中效果 */
}

#pagination {
  width: fit-content;
  margin: 30px auto;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 修复组件宽度收缩状态按钮组换行 */
#statusGroup {
  min-width: fit-content;
}

#recordLink {
  margin-left: 10px;
}

/* 更多按钮 */
.el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
  padding: 7px 11px;
  font-size: 13px;
  font-family: Arial;
}

/* 状态按钮 */
.statusBtn {
  border: 1px solid #ddd;
  margin-left: 3px;
  border-radius: 5px;
}

.pendingBtn {
  color: #fa8c16;
  background: #fff7e6;
  border-color: #ffd591;
}

.processingBtn {
  color: #409eff;
  background: #e6f7ff;
  border-color: #91d5ff;
}

.finishBtn {
  color: #52c41a;
  background: #f6ffed;
  border-color: #b7eb8f
}
</style>