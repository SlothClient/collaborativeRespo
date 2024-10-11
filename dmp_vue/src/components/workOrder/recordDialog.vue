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
                <textarea id="record" placeholder="在此处提交工作记录..."></textarea>
            </el-descriptions-item>
        </el-descriptions>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="centerDialogVisible = false" type="info" plain>取消</el-button>
                <el-button type="primary" @click="centerDialogVisible = false">
                    提交
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>
<script setup lang="ts">
import { ref, watch } from 'vue';
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
const equipData = ref({
    equipName: ''
});
// 监听父组件传递数据的状态变化
// 可见状态变化时，更新本组件的状态
watch(
    () => props.recordDialogVisible,
    (newVal) => {
        centerDialogVisible.value = newVal;
    }
);
// 选中工单数据变化时，更新本组件的状态
watch(
    () => props.selectedOrder,
    async(newVal) => {
        receivedData.value = newVal;
        if(newVal){
            const { tableData,getSelectedEquipInfo } = useEquipInfo(props.selectedOrder);
            await getSelectedEquipInfo();
            console.log(tableData.value);
            equipData.value = tableData.value[0];
        }
    }
)

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