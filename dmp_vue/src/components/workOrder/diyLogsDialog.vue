<template>
    <div id="drawerCtn" v-if="drawerVisible" @click.self="closeDrawer">
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
                <span class="logDate marked">记录日期：{{ log.logDate }}</span>
                <div class="logs">
                    <div class="wordLog">
                        <span class="logTitle marked">文字记录</span>
                        <div class="wordArea logArea">
                            {{ log.logContent }}
                        </div>
                    </div>
                    <div class="fileLog">
                        <span class="logTitle marked">文件记录</span>
                        <div class="fileArea logArea" v-if="log.logAttachment">
                            <!-- 文件展示部分 -->
                            <a :href="log.logAttachment" target="_blank" style="display: flex; align-items: center;text-decoration: none;color: #000;">
                                <img :src="getFileIcon(log.logAttachment)" alt="file icon"
                                    style="width: 40px; height: 40px;" />
                                <span style="margin-left: 10px; font-size: 16px;">
                                    {{ getFileName(log.logAttachment) }}
                                </span>
                            </a>
                        </div>
                        <div v-else>
                            无文件记录
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</template>

<script setup lang="ts">
import emitter from '@/utils/emitter';
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
    get: () => props.selectedOrder,
    set: (value) => {
        // emit('update:selectedOrder', value);
        console.log("orderInfo被修改了：" + value);
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
        ElMessage.success("请求工作日志成功!")

    } catch (error) {
        ElMessage.error("请求工作日志失败!");
    }
};
// 收数据，绑定事件
emitter.on('updateWorkLogs', fetchLogs);
watch(orderInfo, () => {
    fetchLogs();
});

// ------------------------------------------------收到文件处理名称并按类型显示图标------------------------------------------------
import imgIcon from '@/assets/image.jpg'; // 图片类型的图标
import pdfIcon from '@/assets/pdf.jpg';   // PDF类型的图标
import txtIcon from '@/assets/text.jpg';  // 文本类型的图标
import filesIcon from '@/assets/file.jpg'; // 其他文件类型的图标

// 文件类型图标获取函数
const getFileIcon = (filePath) => {
  const fileExtension = filePath.split('.').pop().toLowerCase(); // 获取文件扩展名
  if (['jpg', 'jpeg', 'png', 'gif', 'bmp'].includes(fileExtension)) {
    return imgIcon;
  } else if (fileExtension === 'pdf') {
    return pdfIcon;
  } else if (fileExtension === 'txt') {
    return txtIcon;
  } else {
    return filesIcon;
  }
};

// 获取文件名的函数
const getFileName = (filePath) => {
  return filePath.split('/').pop(); // 返回文件名
};
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
    animation: pull .5s ease forwards;
    overflow-y: auto;
    /* 允许垂直滚动 */
    overflow-x: hidden;
    /* 隐藏横向滚动条 */
}

#drawerIn::-webkit-scrollbar {
    width: 8px;
    /* 自定义滚动条宽度 */
}

#drawerIn::-webkit-scrollbar-thumb {
    background-color: rgba(64, 148, 238, 0.6);
    /* 滚动条颜色 */
    border-radius: 10px;
    /* 滚动条圆角 */
}

#drawerIn::-webkit-scrollbar-thumb:hover {
    background-color: rgba(64, 148, 238, 0.8);
    /* 滚动条悬停颜色 */
}

#drawerIn::-webkit-scrollbar-track {
    background-color: rgba(0, 0, 0, 0.1);
    /* 滚动条轨道颜色 */
    border-radius: 10px;
}

#drawerIn.closing {
    animation: push .5s ease forwards;
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
    flex-direction: column;
}

.logs>div {
    position: relative;
    width: 80%;
    /* flex: 1; */
    /* height: 300px; */
}

.logs>div:first-child {
    margin-bottom: 10px;
}

.marked {
    display: block;
    /* text-decoration: underline 3px wavy rgb(64, 148, 238); */
    margin: 10px 0;
}

.logArea {
    box-sizing: border-box;
    width: 100%;
    height: fit-content;
    min-height: 66px;
    max-height: 200px;
    background-color: rgba(221, 247, 199, .5);
    border-radius: 10px;
    white-space: normal;
    display: flex;
    align-items: center;
    padding: 10px;
    overflow: auto
}
.logArea::-webkit-scrollbar {
    width: 6px;
    height: 6px;
    /* 自定义滚动条宽度 */
}

.logArea::-webkit-scrollbar-thumb {
    background-color: rgba(64, 148, 238, 0.6);
    /* 滚动条颜色 */
    border-radius: 10px;
    /* 滚动条圆角 */
}

.logArea::-webkit-scrollbar-thumb:hover {
    background-color: rgba(64, 148, 238, 0.8);
    /* 滚动条悬停颜色 */
}

.logArea::-webkit-scrollbar-track {
    background-color: rgba(0, 0, 0, 0.1);
    /* 滚动条轨道颜色 */
    border-radius: 10px;
}
.logDate {
    padding-bottom: 10px;
    border-bottom: 3px dotted #ddd;
}
.logTitle {
    position: absolute;
    bottom: 0;
    right: 10px;
    color: rgba(64, 148, 238, 0.3);
    font-weight: 900;
    font-family: "宋体","幼圆";
    font-size: x-large;
    user-select: none;
}
</style>