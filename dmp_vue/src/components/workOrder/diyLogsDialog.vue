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
                        <textarea class="wordArea logArea" v-model="log.logContent" :readonly="!ifCancel"></textarea>
                    </div>
                    <div class="fileLog">
                        <span class="logTitle marked">文件记录</span>
                        <div class="fileArea logArea">
                            <!-- 文件展示部分 -->
                            <a :href="log.logAttachment" target="_blank" class="attachmentLot" v-if="log.logAttachment">
                                <img :src="getFileIcon(log.logAttachment)" alt="file icon"
                                    style="width: 40px; height: 40px;" />
                                <span style="margin-left: 10px; font-size: 16px;">
                                    {{ getFileName(log.logAttachment) }}
                                </span>
                            </a>
                            <span class="noFile" v-else>暂无文件</span>
                            <el-button type="danger" circle size="small" icon="Close" style="margin-left: 10px;" v-if="ifEdit" @click="cleanFile(log)"></el-button>
                            <!-- label包input实现点击选择文件，for配id更好不过这里的input执行v-for后不止一个，退而求其次 -->
                            <label class="selectFile" v-if="ifSelect">+
                                <input type="file" @change="handleFileChange($event,log)">
                            </label>
                            
                        </div>
                    </div>
                    <button class="LogBtn cancelEditBtn" v-if="ifCancel" @click="cancelEdit(log,index)">取消</button>
                    <button class="LogBtn editLogBtn" @click="editLog(log)" v-text="funcBtnVal"></button>
                </div>
            </div>

        </div>
    </div>
</template>

<script setup>
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
    { logDate: '1999年9月9日', logContent: '文字记录', logAttachment: '文件记录', marked: false },
    { logDate: '1999年9月9日', logContent: '文字记录', logAttachment: '文件记录', marked: true },
    { logDate: '1999年9月9日', logContent: '文字记录', logAttachment: '文件记录', marked: false },
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
// 编辑
const funcBtnVal = ref('编辑');
const ifEdit = ref(false);
const ifSelect = ref(false);
const ifCancel = ref(false);
// originalLog 用于保存原始日志数据，不用动态绑定
const originalLog = ref({});
const editLog = (log) => {
    originalLog.value = JSON.parse(JSON.stringify(log));
    funcBtnVal.value = '提交';
    ifEdit.value = true;
    ifCancel.value = true;
};
const cancelEdit = (log,index) => {
    logs[index] = originalLog.value;
    funcBtnVal.value = '编辑';
    ifEdit.value = false;
    ifCancel.value = false;
    
};
const cleanFile = (log) => {
    log.logAttachment = '';
    ifEdit.value = false;
    ifSelect.value = true;
};
const file = ref(null);
const handleFileChange = (event,log) => {
    const selectedFile = event.target.files[0];
    const maxFileSize = 20 * 1024 * 1024; // 设置最大文件限制为20MB

    if (selectedFile) {
        file.value = selectedFile;

        // 检查文件大小
        if (selectedFile.size > maxFileSize) {
            ElMessage.error(`选择的文件大小超过最大限制（${maxFileSize / (1024 * 1024)}MB）。`);
            // 清空文件输入等待重新选择
            removeFile();
            return; // 退出
        }
        // logAttachment为文件名
        log.logAttachment = selectedFile.name;
        ifSelect.value = false;
    }
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
    position: relative;
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
    /* box-sizing: border-box; */
    width: 100%;
    height: fit-content;
    min-height: 66px;
    max-height: 200px;
    background-color: rgba(221, 247, 199, .5);
    border-radius: 6px;
    padding: 6px;
    display: flex;
    align-items: center;
    overflow: auto/* 溢出显示滚动条 */
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
.wordArea {
    overflow-wrap: break-word; /* 溢出换行 */
    resize: none; /* 禁止调整容器尺寸 */
    border: none;
    outline: none;
}
/* 只读样式 */
.wordArea[readonly] {
    cursor: not-allowed;
}
.attachmentLot {
    display: flex;
    align-items: center;
    text-decoration: none;
    color: #000;
}
.attachmentLot:hover {
    text-decoration: underline;
    color: rgb(65, 148, 203);
}
.logDate {
    padding-bottom: 10px;
    border-bottom: 3px dotted #ddd;
}
.logTitle {
    position: absolute;
    bottom: 0;
    right: 0;
    color: rgba(64, 148, 238, 0.3);
    font-weight: 900;
    font-family: "宋体","幼圆";
    font-size: x-large;
    user-select: none;
}
.LogBtn {
    position: absolute;
    right: 10px;
    padding: 2px 5px;
    border-radius: 5px;
    cursor: pointer;
}
.editLogBtn {
    bottom: 5px;
    color: #52c41a;
    background: #f6ffed;
    border: 1px solid #b7eb8f;
}
.cancelEditBtn {
    bottom: 40px;
    color: #fa8c16;
    background: #fff7e6;
    border: 1px solid #ffd591;
}
.selectFile {
    cursor: pointer;
    width: 100px;
    height: 66px;
    border: 2px dotted #000;
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: 900;
    font-size: xx-large;
}
.selectFile input {
    display: none;
}
</style>