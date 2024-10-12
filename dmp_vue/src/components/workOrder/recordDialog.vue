<template>
    <el-dialog v-model="centerDialogVisible" @close="closeDialog" title="工作记录" width="fit-content" align-center>
        <div id="separate"></div>
        <el-descriptions title="计划名称" direction="vertical" border style="margin-top: 20px">
            <el-descriptions-item :rowspan="2" :width="140" label="设备照片" align="center">
                <el-image style="width: 100px; height: 100px"
                    src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
            </el-descriptions-item>
            <el-descriptions-item label="工单编号">{{ receivedData.orderId }}</el-descriptions-item>
            <el-descriptions-item label="工单描述">{{ receivedData.orderDesc }}</el-descriptions-item>
            <el-descriptions-item label="设备名称">{{ equipData.equipName }}</el-descriptions-item>
            <el-descriptions-item label="设备状态">
                <el-tag size="small">执行中</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="工作记录">
                <textarea id="record" placeholder="在此处提交工作记录..." v-model="orderRecord"></textarea>
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
    orderRecord: ''
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
            
            orderRecord.value = newVal.orderRecord;
            // const { tableData, getSelectedEquipInfo } = useEquipInfo(props.selectedOrder);
            // await getSelectedEquipInfo();
            // console.log(tableData.value);
            // equipData.value = tableData.value[0];
            // 设备信息通过对话框之间的通信获取
        }
    }
)

// 提交工作记录
const orderRecord = ref(props.selectedOrder.orderRecord);

const submitRecord = async () => {
    const condition = {
        orderId: receivedData.value.orderId,
        orderRecord: orderRecord.value
    }
    const formData = new FormData();
    formData.append('conditionJson', JSON.stringify(condition));
    try {
        const response = await axios.post('/api/addWorkRecord', formData);
        if (response.data.status) {
            ElMessage.success('提交成功');
            closeDialog();
        }
        else {
            ElMessage.error(response.data.message);
        }
    }
    catch (err) {
        ElMessage.error(err);
    }
};

// 提交确认
const handleSubmit = (done: () => void) => {
    ElMessageBox.confirm('请确认提交', '提示')
        .then(() => {
            submitRecord();
            done()
        })
}

// 对话框之间的数据通信
import emitter from '@/utils/emitter';
const rcvData = ref({});
// 收数据，绑定事件
emitter.on('equipInfoTranferrer',(val:Ref)=>{
    // 解构属性为响应式ref对象
    let {equipName,orderRecord} = toRefs(equipData);
    // 注意value后面要加索引[0]
    equipName.value = val.value[0].equipName;
    orderRecord.value = val.value[0].orderRecord;
})
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
</style>