<template>
    <div id="dynamicOrder">
        <div id="upper">
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
            <div ref="radarContainer" id="raderBlock">
                <div class="midiumChart">
                    <v-chart ref="equipType" id="equipType" :option="typeOption"></v-chart>
                </div>
            </div>
        </div>
        <div ref="chartsContainer" id="midiumCharts">
            <div class="midiumChart">
                <v-chart ref="equipPlan" id="equipPlan" :option="equipOption"></v-chart>
            </div>
            <div class="midiumChart">
                <v-chart ref="planType" id="planType" :option="planOption"></v-chart>
            </div>
            <div class="midiumChart">
                <v-chart ref="workerRank" id="workerRank" :option="workerOption"></v-chart>
            </div>
        </div>
    </div>
</template>
<script setup lang="ts">
import axios from "axios";
import { ElMessage } from "element-plus";
import { reactive, ref, onMounted, onBeforeUnmount } from "vue";

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

const equipTypeData = ref([]);
const getEquipType = async () => {
    const res = await axios.post('/api/getEquipType');
    console.log(res);

    try {
        if (res.data.status && Array.isArray(res.data.list)) {
            equipTypeData.value = res.data.list.map(item => {
                if (item.typeCount !== undefined && item.equipType !== undefined) {
                    return {
                        value: item.typeCount,
                        name: item.equipType
                    };
                } else {
                    console.warn('Invalid item in list:', item);
                    return null;
                }
            }).filter(item => item !== null); // 过滤掉无效的项
            const maxVal = Math.max(...equipTypeData.value.map(item => item.value))+1;
            typeOption.value.radar.indicator = equipTypeData.value.map((item) => ({
                name: item.name,
                max: maxVal,
                color: '#666'
            }));
            typeOption.value.series[0].data[0] = equipTypeData.value.map((item) => item.value);
        } else {
            ElMessage.error(res.data.msg || 'Invalid response data');
        }
    } catch (err) {
        ElMessage.error("请求失败！" + err);
    }
};

const planOption = ref({
    title: {
        text: '计划类型',
        subtext: '保养/维修',
        left: 'right',
        padding: [20, 10, 0, 0]

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
            radius: '40%',
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
            bottom: -190
        }
    ]
})

const workerOption = ref({
    title: {
        text: '工时排名',
        padding: [20, 0, 10, 10],
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
            name: '工人',
            nameTextStyle: {
                align: "left",
                verticalAlign: "bottom",
                lineHeight: 40
            },
            type: 'category',
            data: ['张三', '李四', '王五', '赵六']
        }
    ],
    yAxis: [
        {
            name: '工单/工时',
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
                    { type: 'max', name: 'Max' },
                    { type: 'min', name: 'Min' }
                ]
            },
            markLine: {
                data: [{ type: 'average', name: 'Avg' }]
            }
        }
    ],
    grid: {
        top: "18%",
        bottom: "8%"
    }
})

const equipOption = ref({
    title: {
        text: '设备计划数',
        left: 'center',
        padding: [20, 0, 0, 0]
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
        top: "18%",
        bottom: "8%"
    }
})

const typeOption = ref({
    title: {
        text: '设备类型',
        left: 'right',
        padding: [20, 10, 0, 0]
    },
    tooltip: {
        trigger: 'item'
    },
    radar: {
        // shape: 'circle',
        indicator: [
            { name: '木', max: 6500 },
            { name: '火', max: 16000 },
            { name: '土', max: 30000 },
            { name: '金', max: 38000 },
            { name: '水', max: 52000 },
            { name: '天', max: 25000 }
        ],
        darkMode: true,
        center: ['50%', '55%'],
    },
    series: [
        {
            name: '设备类型',
            type: 'radar',
            symbol: 'circle',
            symbolSize: 8,
            itemStyle: {
                color: '#55b566'
            },
            areaStyle: {
                color: '#55b566',
                opacity: 0.3
            },
            lineStyle: {
                width: 2,
                color: '#55b566'
            },
            label: {
                show: true,
                position: 'top',
                distance: 3,
                color: '#55b566',
                fontWeight: '900',
                fontSize: 14,
                fontFamily: '苹方'


            },
            data: [
                {
                    value: [4200, 3000, 20000, 35000, 50000, 18000],
                    name: '设备类型'
                }
            ]
        }
    ]
})

const equipPlan = ref(null);
const planType = ref(null);
const workerRank = ref(null);
const chartsContainer = ref(null);

const equipType = ref(null);
const radarContainer = ref(null);

const resizeCharts = () => {
    equipPlan.value?.resize();
    planType.value?.resize();
    workerRank.value?.resize();
    equipType.value?.resize();
};

onMounted(async () => {
    const observer = new ResizeObserver(() => {
        resizeCharts();
    });

    if (chartsContainer.value) {
        observer.observe(chartsContainer.value);
    }

    if (radarContainer.value) {
        observer.observe(radarContainer.value)
    };

    resizeCharts(); // 初始化时调用一次 resize
    window.addEventListener('resize', resizeCharts);

    // 等着数据加载完成就不会出现数据还没到就先渲染了图表导致内容为空，await就行了，笑
    await getBigNums();
    await getEquipPlans();
    await getPlanTypes();
    await getWorkerRank();
    await getEquipType();
    // resizeCharts(); // 获取到数据后时调用一次 resize
});

onBeforeUnmount(() => {
    window.removeEventListener('resize', resizeCharts);
});
</script>
<style scoped>
#dynamicOrder * {
    box-sizing: border-box;
}

#upper {
    display: flex;
}

#numBlocks {
    flex: 1;
    display: flex;
    justify-content: space-between;
    width: 50%;
    flex-wrap: wrap;
}

.numBlock {
    background-color: rgb(85, 181, 102);
    border-radius: 10px;
    box-shadow: 2px 3px 3px 3px #ccc;
    /* flex: 1; */
    flex: 1 1 calc(50% - 20px);
    /* 设置为每行两个，考虑间距 */
    box-sizing: border-box;
    margin: 10px;
    color: ivory;
    padding: 20px;
    transition: all 0.3s;
}

/* 媒体查询用于小屏幕上的响应式设计 */
@media (max-width: 768px) {
    .numBlock {
        flex: 1 1 100%;
        /* 小屏幕时每个 numBlock 占满一行 */
    }
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
    height: 425px;
}

/* 新增雷达图 */
#raderBlock {
    flex: 1;
    display: flex;
    padding: 5px 0;
}

#radarBlock .midiumChart {
    flex: 1;
}
</style>