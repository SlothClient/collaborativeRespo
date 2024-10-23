<template>
    <div id="drawerCtn" v-if="drawerVisible">
        <div id="drawerIn" @animationend="handleAnimationEnd" :class="{ closing: isClosing }">
            <div id="drawerHead">
                <div id="closeDrawer" @click="closeDrawer">
                    <el-icon size="25px" color="rgb(255,255,255)">
                        <Close />
                    </el-icon>
                </div>
                <span class="title">工单记录</span>
            </div>
            <div id="space"></div>
            <div class="logItem" v-for="(log, index) in logs" :key="index">
                <span class="marked">记录日期：{{ log.logDate }}</span>
                <div class="logs">
                    <div class="wordLog">
                        <span class="marked">文字记录</span>
                        <div class="wordArea logArea">
                            {{ log.logContent }}
                        </div>
                    </div>
                    <div class="fileLog">
                        <span class="marked">文件记录</span>
                        <div class="fileArea logArea">
                            {{ log.logAttachment }}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { computed, onMounted, ref, watch } from 'vue';

const props = defineProps({
    drawerVisible: {
        type: Boolean,
        default: false,
    },
    selectedOrder: {
        type: Object,
        default: () => {
            return {};
        },
    }
});
const emit = defineEmits(['update:drawerVisible']);
const drawerVisible = computed({
    get: () => props.drawerVisible,
    set: (value) => {
        emit('update:drawerVisible', value);
    }
});
const orderInfo = computed({
    get:() => props.selectedOrder,
    set: (value) => {
        // emit('update:selectedOrder', value);
        console.log("orderInfo被修改了："+value);
    }
})

const isClosing = ref(false);
const logs = ref([
    { date: '1999年9月9日', wordLog: '文字记录', fileLog: '文件记录', marked: false },
    { date: '1999年9月9日', wordLog: '文字记录', fileLog: '文件记录', marked: true },
    { date: '1999年9月9日', wordLog: '文字记录', fileLog: '文件记录', marked: false },
]);

const closeDrawer = () => {
    isClosing.value = true;
};

const handleAnimationEnd = () => {
    if (isClosing.value) {
        emit('update:drawerVisible', false);
        isClosing.value = false;
    }
};

// 向后端请求日志数据的函数
const fetchLogs = async () => {
    try {
        const orderId = orderInfo.value.orderId; // 确保 orderId 是 String 类型
        const response = await axios.get(`/api/work-log/${orderId}`);
        logs.value = response.data; // 假设后端返回的数据格式为数组
        console.log('请求日志数据成功:', response.data);
        
    } catch (error) {
        console.error('请求日志数据失败:', error);
    }
};

watch(orderInfo, () => {
    fetchLogs();
});
</script>

<style scoped>
#drawerCtn {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(127, 127, 127, .3);
    display: flex;
    overflow: hidden;
    z-index: 10;
}

#drawerIn {
    position: absolute;
    right: 0;
    width: 36%;
    height: 100%;
    background-color: rgb(255, 255, 255);
    border-top-left-radius: 30px;
    border-bottom-left-radius: 30px;
    animation: pull .8s ease forwards;
    overflow-y: auto; /* 允许垂直滚动 */
    overflow-x: hidden; /* 隐藏横向滚动条 */
}

#drawerIn::-webkit-scrollbar {
    width: 8px; /* 自定义滚动条宽度 */
}

#drawerIn::-webkit-scrollbar-thumb {
    background-color: rgba(64, 148, 238, 0.6); /* 滚动条颜色 */
    border-radius: 10px; /* 滚动条圆角 */
}

#drawerIn::-webkit-scrollbar-thumb:hover {
    background-color: rgba(64, 148, 238, 0.8); /* 滚动条悬停颜色 */
}

#drawerIn::-webkit-scrollbar-track {
    background-color: rgba(0, 0, 0, 0.1); /* 滚动条轨道颜色 */
    border-radius: 10px;
}

#drawerIn.closing {
    animation: push .8s ease forwards;
}

@keyframes pull {
    from {
        width: 0;
    }
    to {
        width: 36%;
    }
}

@keyframes push {
    from {
        width: 36%;
    }
    to {
        width: 0;
    }
}

#drawerHead {
    position: sticky;
    top: 0;
    z-index: 10;
}

#closeDrawer {
    position: absolute;
    top: 0;
    left: 0;
    width: 50px;
    height: 50px;
    background-color: rgb(64, 148, 238, .8);
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom-right-radius: 30px;
}

.title {
    position: absolute;
    top: 0;
    right: 20px;
    height: 50px;
    line-height: 50px;
    font-weight: 900;
    font-size: 24px;
    font-family: "宋体";
    color: rgb(64, 148, 238);
}

#space {
    height: 50px;
}

.logItem {
    box-shadow: 3px 2px 9px 2px rgba(0, 0, 0, .2);
    border-radius: 10px;
    transition: all .3s;
    font-family: "幼圆";
    padding: 10px;
    margin: 20px 30px;
}

.logItem:hover {
    transform: translate(-1px, -2px);
}

.logs {
    display: flex;
}

.logs div {
    flex: 1;
    /* height: 300px; */
}

.logs div:first-child {
    margin-right: 10px;
}

.marked {
    display: block;
    text-decoration: underline 3px wavy rgb(64, 148, 238);
    margin: 10px 0;
}

.logArea {
    width: 100%;
    height: 200px;
    background-color: rgb(240, 240, 240);
}
</style>
