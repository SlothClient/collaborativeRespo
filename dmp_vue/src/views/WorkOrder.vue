<template>
    <div id="order">
        <div class="filter">
            <span class="descWords">工单状态：</span>
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
            <el-input v-model="orderId" style="width: 200px" placeholder="请输入工单编号" :prefix-icon="Search" clearable />
            <span class="descWords">时间范围：</span>
            <el-date-picker v-model="orderSpan" type="datetimerange" start-placeholder="Start date"
                end-placeholder="End date" format="YYYY-MM-DD HH:mm:ss" date-format="YYYY/MM/DD ddd"
                time-format="A hh:mm:ss" style="width: 200px" />
            <el-button type="primary" :icon="Search" style="margin-left: 10px;">查询</el-button>
            <el-button type="primary" :icon="Refresh" @click="resetFilters">重置</el-button>
        </div>
        <div class="orders">
            <!--  v-if="pickedNum > 0" -->
            <div id="pickedPrompt">
                <div class="picked">
                    已选择<span>{{ pickedNum }}</span>项
                </div>
                <div class="clear" @click="clearSelection">
                    清空
                </div>
            </div>
            <el-table :data="orderTable" style="width: 99%;margin: 10px;" :stripe="true" :border="true"
                @selection-change="handleSelectionChange" ref="orderTableRef">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column property="id" label="保养工单信息" width="880" align="center">
                    <template #default="scope">
                        <div class="flex-container">
                            <div>计划名称：<span>{{ scope.row.planName }}</span></div>
                            <div>工单编号：<span>{{ scope.row.orderId }}</span></div>
                            <div>设备编号：<span>{{ scope.row.equipId }}</span></div>
                            <br />
                            <!-- <div>标准工时：<span>{{ scope.row.标准工时 }}</span></div> -->
                            <div>负责人员：<span>{{ scope.row.workerId }}</span></div>
                            <div>工单备注：<span>{{ scope.row.orderDesc }}</span></div>
                            <br />
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
                    <template #default="scope">
                        <el-button size="small" @click="handleEdit(scope.$index, scope.row)" type="primary">
                            工单详情
                        </el-button>
                        <el-button size="small" type="success" @click="handleDelete(scope.$index, scope.row)">
                            更多
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div id="pagination">
                <span style="margin-right: 20px;">共<span style="color: #409eff;">{{ totalOrders }}</span>条</span>
                <el-pagination background layout="prev, pager, next, sizes" @change="fetchOrders(statusFilter)"
                    v-model:current-page="currentPage" v-model:page-size="pageSize" v-model:total="totalOrders"
                    :page-sizes="[3, 5, 10]" />
            </div>

        </div>
    </div>
</template>
<script setup lang="ts">
import { Refresh, Search } from '@element-plus/icons-vue';
import { ref, onMounted } from 'vue';
import axios from 'axios';

const orderId = ref('');
const orderSpan = ref([]);
const orderTable = ref([]);
const pickedNum = ref(0);
const statusFilter = ref('no');
const selectedRows = ref([]); // 新增一个变量来追踪选中的行
const orderTableRef = ref(null); // 引用 el-table 组件

const currentPage = ref(1); // 当前页码
const pageSize = ref(3); // 每页显示的条目数
const totalOrders = ref(0); // 总条目数
const fetchOrders = async (status = 'no') => {
    const condition = {
        offset: (currentPage.value - 1) * pageSize.value,
        limit: pageSize.value,
    };

    // 激活按钮，显示被选中
    statusFilter.value = status;

    let formData = new FormData();
    formData.append("conditionJson", JSON.stringify(condition));

    try {
        const response = await axios.post('/api/getOrdersByPage', formData);

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
            }
        } else {
            console.error("Error:", response.data.msg);
        }
    } catch (error) {
        console.error("Error fetching orders:", error);
    }
};

onMounted(() => {
    fetchOrders();
});

const resetFilters = () => {
    orderId.value = '';
    orderSpan.value = [];
    statusFilter.value = ''; // Reset status filter
    fetchOrders();
};

const handleEdit = (index, row) => {
    console.log(index, row);
};

const handleDelete = (index, row) => {
    console.log(index, row);
};

const handleSelectionChange = (val) => {
    selectedRows.value = val; // 更新选中的行
    pickedNum.value = val.length; // 更新已选数量
};

const clearSelection = () => {
    if (orderTableRef.value) {
        orderTableRef.value.clearSelection(); // 清除所有选中的行
    }
    pickedNum.value = 0; // 重置已选数量
    selectedRows.value = []; // 清空选中的行
};

// 查询，工单编号、时间范围，选择几个条件查询几个条件，不选择默认查询所有
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
}

.flex-container>div {
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
</style>