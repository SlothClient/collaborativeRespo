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
            <el-table :data="orderTable" style="width: 99%;margin: 10px;" stripe="true" border="true">
                <el-table-column type="selection" width="55" align="center"/>
                <el-table-column property="id" label="保养工单信息" width="880" align="center">
                    <template #default="scope">
                        <div class="id">工单编号：<span>{{ scope.row.id }}</span></div>
                        <div class="name">计划名称：<span>{{ scope.row.name }}</span></div>
                        <div class="record">工作记录：<span>{{ scope.row.record }}</span></div>
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
import { ref } from 'vue';
const orderId = ref('');
const orderSpan = ref('');
const orderTable = ref([
    {
        id: '1234567890',
        name: '工单描述',
        overflow: '溢出处理',
        record: '工单记录',
    },
 ]);
 const handleEdit = (index: number, row: any) => {
    console.log(index, row);
 }
 const handleDelete = (index: number, row: any) => {
    console.log(index, row);
 }

 const pickedNum = ref(0);

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
    background-color: rgba(64, 158, 255,.3);
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

</style>