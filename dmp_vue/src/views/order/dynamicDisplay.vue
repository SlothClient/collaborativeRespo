<template>
    <div id="dynamicOrder">
        <div id="numBlocks">
            <div class="numBlock">
                <div class="bigNum">{{ bigNums.totalOrder }}</div>
                <div class="titleDown">工单总数</div>
            </div>
            <div class="numBlock">
                <div class="bigNum">{{ bigNums.toSendOrder }}</div>
                <div class="titleDown">待派单计划数</div>
            </div>
            <div class="numBlock">
                <div class="bigNum">{{ bigNums.toFinishOrder }}</div>
                <div class="titleDown">待完成工单数</div>
            </div>
            <div class="numBlock">
                <div class="bigNum">{{ bigNums.finishOrder }}</div>
                <div class="titleDown">已完成工单数</div>
            </div>
        </div>
        <div id="midiumCharts">
            <div class="midiumChart">
                <v-chart id="equipPlan" :option="equipOption"></v-chart>
            </div>
            <div class="midiumChart">
                <v-chart id="planType" :option="planOption"></v-chart>
            </div>
            <div class="midiumChart">
                <v-chart id="workerRank" :option="workerOption"></v-chart>
            </div>
        </div>
    </div>
</template>
<script setup lang="ts">
import axios from "axios";
import { ElMessage } from "element-plus";
import { reactive, ref, onMounted } from "vue";

const bigNums = reactive({
    totalOrder: 23,
    toSendOrder: 12,
    toFinishOrder: 22,
    finishOrder: 1
})

const getBigNums = async () => {
    const response = await axios.post('/api/getBigNums');
    try {
        if (response.data.status) {
            Object.assign(bigNums, response.data.list[0]);
            // console.log("bigNums", bigNums);
            ElMessage.info("草率的可视化...");
        }
        else {
            ElMessage.error(response.data.msg);
        }
    }
    catch (err) {
        ElMessage.error("请求失败！" + err);
    }
};

const getEquipPlans = async () => {
    const response = await axios.post('/api/getEquipPlans');
    try {
        if (response.data.status) {
            /**
             * 将返回的list中的每一个equipId、equipName、planCount做成数组
             * 如equipIds: [1,2,3], equipNames: ['A','B','C'], planCounts: [10,5,8]
             */
            const equipIds = [];
            const equipNames = [];
            const planCounts = [];

            response.data.list.forEach(item => {
                equipIds.push(item.equipId);
                equipNames.push(item.equipName);
                planCounts.push(item.planCount);
            });
            // 将equipIds和equipNames拼接成一个数组["1-A","2-B","3-C"]并赋值
            equipOption.value.xAxis.data = equipIds.map((id, index) => `${id}-${equipNames[index]}`);
            equipOption.value.series[0].data = planCounts;
        }
        else {
            ElMessage.error(response.data.msg);
        }
    }
    catch (err) {
        ElMessage.error("请求失败！" + err);
    }
}

const getPlanTypes = async () => {
    const response = await axios.post('/api/getPlanTypes');
    try {
        if (response.data.status) {
            // 将返回的list数据转换成图表所需格式
            const planTypesData = response.data.list.map(item => ({
                value: item.typeCount,
                name: item.planType
            }));
            // 更新planOption的series数据
            planOption.value.series[0].data = planTypesData;

        } else {
            ElMessage.error(response.data.msg);
        }
    } catch (err) {
        ElMessage.error("请求失败！" + err);
    }
};

const getWorkerRank = async () => {
    const response = await axios.post('/api/getWorkerRank');
    try {
        if (response.data.status) {
            const workerIds = [];
            const workerNames = [];
            const workerOrds = [];
            const workerHours = [];
            response.data.list.forEach(item => {
                workerIds.push(item.workerId);
                workerNames.push(item.workerName);
                workerOrds.push(item.workerOrds);
                workerHours.push(item.workerHours);
            });
            workerOption.value.xAxis[0].data = workerIds.map((id, index) => `${id}-${workerNames[index]}`);
            workerOption.value.series[0].data = workerOrds;
            workerOption.value.series[1].data = workerHours;
            // console.log("workerIds", workerIds);
            // console.log("workerNames", workerNames);
            // console.log("workerOrds", workerOrds);
            // console.log("workerHours", workerHours);
        } else {
            ElMessage.error(response.data.msg);
        }
    } catch (err) {
        ElMessage.error("请求失败！" + err);
    }
}

const planOption = ref({
    title: {
        text: '计划类型',
        subtext: '保养/维修',
        left: 'right',
        padding: [20,10,0,0]

    },
    tooltip: {
        trigger: 'item'
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        top: 20
    },
    series: [
        {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
                { value: 1048, name: '大修' },
                { value: 735, name: '中修' },
                { value: 580, name: '小修' }
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            },
            bottom: -200
        }
    ]
})

const workerOption = ref({
    title: {
        text: '工时排名',
        padding: [20,0,10,10],
        right: 10
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['单量', '工时'],
        top: 45,
        right: 10
    },
    toolbox: {
        show: true,
        feature: {
            dataView: { show: true, readOnly: false },
            saveAsImage: { show: true }
        },
        left: 10,
        top: 10


    },
    calculable: true,
    xAxis: [
        {
            type: 'category',
            // prettier-ignore
            data: ['张三', '李四', '王五', '赵六']
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: '单量',
            type: 'bar',
            data: [
                2.0, 4.9, 7.0, 23.2
            ],
            markPoint: {
                data: [
                    { type: 'max', name: 'Max' },
                    { type: 'min', name: 'Min' }
                ]
            },
            markLine: {
                data: [{ type: 'average', name: 'Avg' }]
            }
        },
        {
            name: '工时',
            type: 'bar',
            data: [
                2.6, 5.9, 9.0, 26.4
            ],
            markPoint: {
                data: [
                    { type: 'max',name: 'Max' },
                    { type: 'min',name: 'Min' }
                ]
            },
            markLine: {
                data: [{ type: 'average', name: 'Avg' }]
            }
        }
    ],
    grid: {
        top: "15%",
        bottom: "10%"
    }
})

const equipOption = ref({
    title: {
        text: '设备计划数',
        left: 'center',
        padding: [20,0,0,0]
    },
    tooltip: {
        trigger: 'axis'
    },
    xAxis: {
        name: "设备",
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
        name: "计划数",
        type: 'value'
    },
    series: [
        {
            data: [150, 230, 224, 218, 135, 147, 260],
            type: 'line'
        }
    ],
    grid: {
        top: "15%",
        bottom: "10%"
    }
})

onMounted(() => {
    getBigNums();
    getEquipPlans();
    getPlanTypes();
    getWorkerRank();
})
</script>
<style scoped>
#dynamicOrder * {
    box-sizing: border-box;
}

#numBlocks {
    display: flex;
    justify-content: space-between;
}

.numBlock {
    background-color: rgb(85, 181, 102);
    border-radius: 10px;
    box-shadow: 2px 3px 3px 3px #ccc;
    flex: 1;
    margin: 10px;
    color: ivory;
    padding: 20px;
    transition: all 0.3s;
}

.numBlock .bigNum {
    font-size: 30px;
    font-family: "华文琥珀";
    margin: 15px auto;
}

.numBlock:hover {
    box-shadow: 3px 5px 10px 4px #ccc;
    transform: translate(-2px, -2px);
    cursor: pointer;
}

#midiumCharts {
    display: flex;
    justify-content: space-between;
}

.midiumChart {
    flex: 1;
    background-color: rgba(64, 158, 255, .1);
    margin: 5px;
    box-shadow: 1px 5px 6px 2px #eee;
}

#planType {
    height: 500px;
}
</style>