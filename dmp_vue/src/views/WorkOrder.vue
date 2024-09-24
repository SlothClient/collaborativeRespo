<template>
    <div id="order">
        <div class="filter">
            <span class="descWords">工单状态：</span>
            <el-button-group class="ml-4">
                <el-button type="primary">全部</el-button>
                <el-button type="primary" plain>未开始</el-button>
                <el-button type="primary" plain>保养中</el-button>
                <el-button type="primary" plain>已完成</el-button>
            </el-button-group>
            <span class="descWords">工单编号：</span>
            <el-input v-model="orderId" style="width: 200px" placeholder="请输入工单编号" :prefix-icon="Search" clearable />
            <span class="descWords">时间范围：</span>
            <el-date-picker v-model="orderSpan" type="datetimerange" start-placeholder="Start date"
                end-placeholder="End date" format="YYYY-MM-DD HH:mm:ss" date-format="YYYY/MM/DD ddd"
                time-format="A hh:mm:ss" style="width: 200px" />
            <el-button type="primary" :icon="Search" style="margin-left: 10px;">查询</el-button>
            <el-button type="primary" :icon="Search">重置</el-button>
        </div>
        <div class="orders">
            <div id="pickedPrompt">
                <div class="picked">
                    已选择<span>{{ pickedNum }}</span>项
                </div>
                <div class="clear">
                    清空
                </div>
            </div>
            <el-table :data="orderTable" style="width: 99%;margin: 10px;" :stripe="true" :border="true">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column property="id" label="保养工单信息" width="880" align="center">
                    <template #default="scope">
                        <div class="flex-container">
                            <div>工单编号：<span>{{ scope.row.工单编号 }}</span></div>
                            <div>设备编号：<span>{{ scope.row.设备编号 }}</span></div>
                            <div>派单时间：<span>{{ scope.row.派单时间 }}</span></div>
                            <div>开始时间：<span>{{ scope.row.开始时间 }}</span></div>
                            <div>结束时间：<span>{{ scope.row.结束时间 }}</span></div>
                            <div>标准工时：<span>{{ scope.row.标准工时 }}</span></div>
                            <div>负责人编号：<span>{{ scope.row.负责人编号 }}</span></div>
                            <div>工单备注：<span>{{ scope.row.工单备注 }}</span></div>
                            <div>工作记录：<span>{{ scope.row.工作记录 }}</span></div>
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
        </div>
    </div>
</template>
<script setup lang="ts">
import { Search } from '@element-plus/icons-vue';
import { ref, onMounted } from 'vue';
import axios from 'axios';

const orderId = ref('');
const orderSpan = ref([]);
const orderTable = ref([]);
const pickedNum = ref(0);

const fetchOrders = async (status) => {
    const condition = {};

    let formData = new FormData();
    formData.append("conditionJson", JSON.stringify(condition));

    try {
        const response = await axios.post('http://localhost:8889/equip/getOrder',formData);

        if (response.data.status) {
            orderTable.value = response.data.list; // Update orderTable with data from the response
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
    fetchOrders();
};

const handleEdit = (index, row) => {
    console.log(index, row);
};

const handleDelete = (index, row) => {
    console.log(index, row);
};
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
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.id,
.name,
.record {
    flex: 1;
    min-width: 200px;
}
</style>