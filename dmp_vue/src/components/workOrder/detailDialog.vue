<template>
    <div id="detailDialog">
        <el-dialog v-model="centerDialogVisible" @close="closeDialog" title="工单详情" width="800" align-center>
            <div id="separate"></div>
            <div id="orderInfo">
                <div class="formTitle">工单基本信息</div>
                <div id="orderForm">
                    <el-form :inline="true" :model="formInline" class="demo-form-inline" disabled>
                        <el-form-item label="保养单号">
                            <el-input v-model="formInline.planId" clearable />
                        </el-form-item>
                        <el-form-item label="计划名称">
                            <el-input v-model="formInline.planName" clearable />
                        </el-form-item>
                        <el-form-item label="计划时间">
                            <el-date-picker v-model="formInline.planTime" type="datetime" clearable />
                        </el-form-item>
                        <el-form-item label="负责人员">
                            <el-input v-model="formInline.workerName" clearable />
                        </el-form-item>
                        <el-form-item label="工单描述">
                            <el-input v-model="formInline.orderDesc" type="textarea" style="width: 540px;" clearable />
                        </el-form-item>
                    </el-form>
                </div>
            </div>
            <div id="equipInfo">
                <div class="formTitle">保养设备</div>
                <div id="equipTable">
                    <el-table :data="equipData" border style="width: 100%">
                        <el-table-column prop="equipName" label="设备名称" align="center" />
                        <el-table-column prop="equipId" label="设备编号" align="center" />
                        <el-table-column prop="lastMaintance" label="执行时间" align="center" />
                        <el-table-column prop="maintanceDesc" label="保养内容" align="center" />
                        <el-table-column prop="orderDesc" label="执行备注" align="center" />
                        <el-table-column prop="orderRecord" label="保养情况" align="center" />
                    </el-table>
                </div>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="centerDialogVisible = false" type="info" plain>关闭</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>
<script lang="ts" setup>
import { ref, reactive, watch, onMounted } from 'vue'
// 对话框之间的数据通信
import emitter from '@/utils/emitter';

// 模版数据
const formInline = reactive({
    planId: '计划编号',
    planName: '计划名称',
    planTime: '计划时间',
    workerName: '负责人员',
    orderDesc: '工单描述'
})

const onSubmit = () => {
    console.log('submit!')
}

const equipData = ref([
    {
        date: '2016-05-03',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-02',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    }
]);

// 接收父组件传递的数据并监听变化，将dialogVisible赋值给centerDialogVisible，selectedOrder赋值给formInline
// const props = defineProps(
//     {
//         dialogVisible:{
//             type:Boolean,
//             default:false
//         },
//         selectedOrder:{
//             type:Object,
//             default:{}
//         }
//     }
// )
const props = defineProps({
    dialogVisible: {
        type: Boolean,
        required: true
    },
    selectedOrder: {
        type: Object,
        required: true
    }
})
// 使用selectedOrder获取设备信息
import { useEquipInfo } from '@/hook/useEquipInfo';
const centerDialogVisible = ref(props.dialogVisible)
const emit = defineEmits(['update:dialogVisible']);
// 监听对话框的关闭事件并发出更新事件
const closeDialog = () => {
    centerDialogVisible.value = false;
    emit('update:dialogVisible', false);
};

// 监听父组件传递的dialogVisible变化
watch(
    () => props.dialogVisible, 
    async(newVal) => {
        // console.log(2333);
        centerDialogVisible.value = newVal;
        if(newVal){
            console.log("详情框Visible变化了");
            
            // 一旦父组件dialogVisible发生变化，当前组件和工作记录对话框组件都会执行获取选中设备信息操作
            // 考虑在一个组件中发请求，另一组建通过组件之间的通信获取数据
            
        }
    });

// 如果selectedOrder变化，也需要更新formInline的数据
watch(() => props.selectedOrder, 
async(newVal) => {
    console.log('详情框selectedOrder变化了');
    
    Object.assign(formInline, newVal)
    if(newVal){
        const { tableData,getSelectedEquipInfo } = useEquipInfo(props.selectedOrder);
            await getSelectedEquipInfo();
            equipData.value = tableData.value;
            // 发数据，触发事件
            emitter.emit('equipInfoTranferrer',equipData)
    }
})

onMounted(() => {
    // onMounted钩子修改#equipTable表头样式，也改不到。。。
    // const ths = document.querySelectorAll('#equipTable .el-table__header tr th')
    // ths.forEach((th) => {
    //     th.style.backgroundColor = '#000'
    // })
    
    // 对话框组件只在工单组件加载时加载，而不是打开对话框的时候，因为打开关闭控制的是display，所以下面的信息获取无效
    // getSelectedEquipInfo();
    // ElMessage.success('组件加载成功!')
})
</script>
<style scoped>
.demo-form-inline .el-input {
    --el-input-width: 220px;
}

.demo-form-inline .el-select {
    --el-select-width: 220px;
}

/* 样式调整 */

.formTitle {
    height: 26px;
    line-height: 26px;
    padding: 0 10px;
    border-left: 4px solid #409eff;
    margin-bottom: 15px;
}

.demo-form-inline {
    text-align: center;

}



/* 调整表头为淡灰色并添加盒阴影 */
.el-table .el-table__header th {
    background-color: #000 !important;
    /* 调不到 */
}


</style>