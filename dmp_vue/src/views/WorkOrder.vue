<template>
    <div id="dynamicOrder">
  
      <el-row>
        <div style="width: 500px;height: 350px;">
          <div style="display: flex;">
            <div class="focus-effect" style="margin-top: 10px;color: ivory;">
            {{ bigNums.totalOrder }} &emsp;工单总数
            </div>
            <div class="focus-effect"style="margin-top: 10px;margin-right: 10px;">
              {{ bigNums.toSendOrde }} &emsp;待派单计划数
            </div>
          </div>
          <div style="display: flex">
            <div class="focus-effect" style="margin-top: 10px;">
              {{ bigNums.toFinishOrder }} &emsp;待完成工单数
            </div>
            <div class="focus-effect" style="margin-top: 10px;margin-right: 10px;">
              {{ bigNums.finishOrder }} &emsp;已完成工单数
            </div>
          </div>
  
  
        </div>
        <div id="num3" class="focus-effect" style="width: 800px;height: 340px;background-color:#d9ecff;margin-left: 10px;border-radius: 15px;">
        </div>
      </el-row>
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
  <script setup>
  import axios from "axios";
  import { ElMessage } from "element-plus";
  import * as echarts from 'echarts'
  import {ref, reactive, onMounted, onBeforeUnmount} from 'vue';
  
  
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
  const equipTypeData= ref([])
  // const getEquipType = async ()=>{
  //   const res = await  axios.post('/api/getEquipType');
  //   try{
  //     if(res.data.status){
  //         equipTypeData = res.data.list.map(item=>({
  //         value: item.typeCount,
  //         name:item.equipType
  //       }));
  
  //     }
  //   }
  // }
  
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
  
  const equipTypeOption = ref({
    title: {
      text: '设备类型'
    },
    legend: {
      data: ['Allocated Budget']
    },
    //雷达的坐标系配置
    radar: {
      name:{
        textStyle:{
            clolor:'#05D5FF',
            fontSize:14
  
        }
      },
      shape:'polygon',
      center:['50%','50%'],
      radius:'80%',
      startAngle:120,
      axisLine:{
        lineStyle:{
          color:'rgba(5,213,255, .8)'
        }
      },
      splitLine:{
        show:true,
        lineStyle:{
          width:1,
          color:'rgba(5,213,255, .8)'
        }
  
      },
      // shape: 'circle',
      indicator: equipTypeData.map((item)=>({
        name:item.name,
        max: 100
      })),
      splitArea:{
        show:false
      }
    },
    //坐标极点
    polar:{
      center:['50%','50%'],
      radius:'0%'
    },
    //坐标角度
    angleAxis:{
      min:0,
      interval:5,
      clockwise:false
    },
    //径向轴
    radiusAxis:{
      min:0,
      interval:20,
      splitLine:{
        show:true
      }
    },
    series: {
      type: 'radar',
      symbol:'circle',
      symbolSize: 10,
      itemStyle:{
        normal:{
          color:'#05D5FF'
        }
      },
      areaStyle:{
        normal:{
          color:'#05D5FF',
          opacity:0.5
        }
      },
      lineStyle:{
        width:2,
        color:'#05D5FF'
      },
      
    }
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
      top: "15%",
      bottom: "10%"
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
      top: "15%",
      bottom: "10%"
    }
  })
  
  const equipPlan = ref(null);
  const planType = ref(null);
  const workerRank = ref(null);
  const chartsContainer = ref(null);
  const equipType= ref(null);
  
  const resizeCharts = () => {
    equipPlan.value?.resize();
    planType.value?.resize();
    workerRank.value?.resize();
  };
  
  onMounted(async() => {
    const observer = new ResizeObserver(() => {
      resizeCharts();
    });
  
    if (chartsContainer.value) {
      observer.observe(chartsContainer.value);
    }
  
    resizeCharts(); // 初始化时调用一次 resize
    window.addEventListener('resize', resizeCharts);
  
    // 等着数据加载完成就不会出现数据还没到就先渲染了图表导致内容为空，await就行了，笑
    await getBigNums();
    await getEquipPlans();
    await getPlanTypes();
    await getWorkerRank();
    // resizeCharts(); // 获取到数据后时调用一次 resize
    const initChart = (domId, option) => {
      const chartDom = document.getElementById(domId);
      if (chartDom) {
        const myChart = echarts.init(chartDom);
        myChart.setOption(option);
      }
    };
    const option3 = {
      title: {
        text: 'Basic Radar Chart'
      },
      legend: {
        data: ['Allocated Budget', 'Actual Spending']
      },
      radar: {
        // shape: 'circle',
        indicator: [
          { name: 'Sales', max: 6500 },
          { name: 'Administration', max: 16000 },
          { name: 'Information Technology', max: 30000 },
          { name: 'Customer Support', max: 38000 },
          { name: 'Development', max: 52000 },
          { name: 'Marketing', max: 25000 }
        ]
      },
      series: [
        {
          name: 'Budget vs spending',
          type: 'radar',
          data: [
            {
              value: [4200, 3000, 20000, 35000, 50000, 18000],
              name: 'Allocated Budget'
            },
            {
              value: [5000, 14000, 28000, 26000, 42000, 21000],
              name: 'Actual Spending'
            }
          ]
        }
      ]
    };
  
    initChart('num3', option3);
  });
  
  onBeforeUnmount(() => {
    window.removeEventListener('resize', resizeCharts);
  });
  </script>
  <style scoped>
  
  .focus-effect {
    border-radius: 15px;
    width: 230px;
    height: 160px;
    margin-left: 20px;
    display: flex;
    justify-content: center;
    align-items: center; /* 垂直居中 */
    background-color: #c6e2ff;
    text-align: center;
    transition: background-color 0.3s ease, transform 0.3s ease; /* 平滑过渡效果 */
  }
  
  .focus-effect:hover {
    background-color: #eef; /* 鼠标移入时变亮 */
    transform: scale(1.05); /* 鼠标移入时稍微放大 */
  }
  #dynamicOrder * {
    box-sizing: border-box;
  }
  
  #numBlocks {
    display: flex;
    justify-content: space-between;
  }
  
  .numBlock {
    border-radius: 10px;
    box-shadow: 2px 3px 3px 3px #ccc;
    width: 230px;
    height: 120px;
    flex: 1;
    color: ivory;
    padding: 20px;
    transition: all 0.3s;
    margin-left: 20px;
    display: flex;
    justify-content: center;
    align-items: center; /* 垂直居中 */
    background-color: #79bbff;
    text-align: center;
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