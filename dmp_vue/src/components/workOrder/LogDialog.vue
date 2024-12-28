<template>
    <el-dialog v-model="centerDialogVisible" @close="closeDialog" title="工作日志" width="fit-content" align-center>
        <div id="separate"></div>
        <el-descriptions title="计划名称" direction="vertical" border style="margin-top: 20px">
            <el-descriptions-item :rowspan="2" :width="140" label="设备照片" align="center">
                <el-image style="width: 100px; height: 100px" :src="equipData.equipPic" />
            </el-descriptions-item>
            <el-descriptions-item label="工单编号">{{ receivedData.orderId }}</el-descriptions-item>
            <el-descriptions-item label="工单描述">{{ receivedData.orderDesc }}</el-descriptions-item>
            <el-descriptions-item label="设备名称">{{ equipData.equipName }}</el-descriptions-item>
            <el-descriptions-item label="设备状态">
                <el-tag size="small">执行中</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="文字记录">
                <textarea id="record" placeholder="在此处提交文字记录..." v-model="word_log"></textarea>
            </el-descriptions-item>
            <el-descriptions-item label="文件记录">
                <!-- 显示添加文件按钮，当没有文件时显示 -->
                <!-- 利用label的for属性实现联动点击功能 -->
                <label v-if="!file" id="addFileLabel" for="addFileBtn">+</label>

                <!-- 文件选择的隐藏输入框 -->
                <input type="file" id="addFileBtn" style="display:none;" @change="handleFileChange" ref="addFileBtn" />

                <!-- 文件信息部分，当文件存在时显示 -->
                <div v-if="file" id="fileInfo">
                    <!-- 文件图标和名称部分，点击时可重新选择文件 -->
                    <div @click="selectFileAgain" style="cursor: pointer;">
                        <img :src="fileIcon" alt="file icon"
                            style="width: 50px; height: 50px; vertical-align: middle;" />
                        <span id="fileName" style="margin-left: 10px; font-size: 16px;">
                            {{ file.name }}
                        </span>
                    </div>

                    <!-- 删除按钮，点击删除文件 -->
                    <el-button type="danger" circle size="small" icon="Close" @click="removeFile"
                        style="margin-left: 10px;"></el-button>
                </div>
            </el-descriptions-item>
        </el-descriptions>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="centerDialogVisible = false" type="info" plain>取消</el-button>
                <el-button type="primary" @click="handleSubmit">
                    提交
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>
<script setup lang="ts">
import { Ref, handleError, ref, reactive, watch, toRefs } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
const props = defineProps({
    recordDialogVisible: {
        type: Boolean,
        default: false,
        required: true
    },
    selectedOrder: {
        type: Object,
        default: () => {
            return {};
        },
        required: true
    }
});
const centerDialogVisible = ref(props.recordDialogVisible);
const receivedData = ref(props.selectedOrder);
const emit = defineEmits(['update:recordDialogVisible']);
const closeDialog = () => {
    centerDialogVisible.value = false;
    word_log.value = '';
    file.value = null;
    // 更新状态到父组件
    emit('update:recordDialogVisible', false);

};
// 使用selectedOrder获取设备信息
import { useEquipInfo } from '@/hook/useEquipInfo';
import axios from 'axios';
// 使用reactive是因为具有深层的响应式，收到数据还能动态渲染到页面
const equipData = reactive(
    {
        equipName: '',
        equipPic: '',
        word_log: ''
    }
);
// 监听父组件传递数据的状态变化
// 可见状态变化时，更新本组件的状态
watch(
    () => props.recordDialogVisible,
    (newVal) => {
        console.log("记录框Visible状态变化");

        centerDialogVisible.value = newVal;
    }
);
// 选中工单数据变化时，更新本组件的状态
watch(
    () => props.selectedOrder,
    async (newVal) => {
        receivedData.value = newVal;
        if (newVal) {
            console.log("记录框selectedOrder变化");

            word_log.value = newVal.word_log;

            // const { tableData, getSelectedEquipInfo } = useEquipInfo(props.selectedOrder);
            // await getSelectedEquipInfo();
            // console.log(tableData.value);
            // equipData.value = tableData.value[0];
            //设备信息通过对话框之间的通信获取
        }
    }
)


// 提交工作日志
const word_log = ref(props.selectedOrder.word_log);

const submitLog = async () => {
    if (receivedData.value.orderStatus === "已完成") {
        ElMessage.error('该工单已完成，无法提交日志');
        return;
    }
    const formData = new FormData();
    formData.append('orderId', receivedData.value.orderId);
    formData.append('workerId', receivedData.value.workerId);
    formData.append('wordLog', word_log.value);
    formData.append('fileLog', addFileBtn.value?.files[0]);


    try {
        const response = await axios.post('/api/upload-log', formData, {
            headers: {
                'Content-Type': 'multipart/form-data', // 指定表单数据类型
            },
        });

        ElMessage.success(response.data);
        closeDialog();
    } catch (error) {
        ElMessage.error('工作日志提交失败: ' + (error.response?.data || error.message));
    }
};

// 提交确认
const handleSubmit = (done: () => void) => {
    // 显示确认信息的弹窗
    const wordLog = word_log.value; // 获取文字记录
    const fileName = addFileBtn.value?.files[0]?.name || '无文件记录'; // 获取文件名，若无文件则显示 '无文件记录'

    // 生成确认信息
    const message = `<h4 style="margin:0">请确认信息</h4><p>文字记录：<span style="color:skyblue">${wordLog}</span></p><p>文件记录：<span style="color:skyblue">${fileName}</span></p>`;

    // 调用确认对话框
    ElMessageBox.confirm(message,
        '提交日志',
        {
            dangerouslyUseHTMLString: true // 允许使用 HTML 字符串
        })
        .then(async () => {
            await submitLog();
            // 提交后重新拉取工作日志
            // 发数据，触发事件
            emitter.emit("updateWorkLogs");
        })
}

// -----------------------------------------对话框之间的数据通信-----------------------------------------
import emitter from '@/utils/emitter';
const rcvData = ref({});
// 收数据，绑定事件
emitter.on('equipInfoTranferrer', (val: Ref) => {
    // 解构属性为响应式ref对象
    let { equipName, word_log, equipPic } = toRefs(equipData);
    // 注意value后面要加索引[0]
    equipName.value = val.value[0].equipName;
    equipPic.value = val.value[0].equipPic;
    word_log.value = val.value[0].word_log;
})
// -----------------------------------------工作日志文件提交逻辑-----------------------------------------
const file = ref(null); // 保存上传的文件对象
const fileIcon = ref(''); // 保存文件对应的图标URL
const addFileBtn = ref(null); // 保存文件输入框的ref
import imgIcon from '@/assets/image.jpg'; // 图片类型的图标
import pdfIcon from '@/assets/pdf.jpg'; // 文件类型的图标
import txtIcon from '@/assets/text.jpg'; // 文件类型的图标
import filesIcon from '@/assets/file.jpg'; // 文件类型的图标


// 处理文件选择
const handleFileChange = (event) => {
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

        // 根据文件类型设置图标
        if (selectedFile.type.startsWith('image/')) {
            fileIcon.value = imgIcon;
        } else if (selectedFile.type === 'application/pdf') {
            fileIcon.value = pdfIcon;
        } else if (selectedFile.type === 'text/plain') {
            fileIcon.value = txtIcon;
        } else {
            fileIcon.value = filesIcon;
        }
    }
};

// 删除文件
const removeFile = () => {
    file.value = null; // 清空文件
    fileIcon.value = ''; // 清空图标
    if (addFileBtn.value) {
        addFileBtn.value = ''; // 清空文件输入框的值
    }
};

// 重新选择文件，点击图标或文件名时触发
const selectFileAgain = () => {
    addFileBtn.value.click();
};
</script>
<style scoped>
/* 分割线 */
#separate {
    position: absolute;
    left: 0;
    top: 48px;
    width: 100%;
    border-bottom: 1px solid #ccc;
}

/* 工作记录输入框 */
#record {
    border: none;
    width: 100%;
    height: 100px;
    resize: none;
}

#record:focus {
    outline: none;
}

/* 上传文件区域 */
#addFileLabel {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px;
    border: 2px dashed #ccc;
    cursor: pointer;
    font-size: 24px;
    color: #666;
}

#addFileLabel:hover {
    border-color: #999;
    color: #333;
}

#fileInfo {
    display: flex;
    align-items: center;
}
</style>