<template>
    <div id="detailDialog">
        <el-dialog v-model="centerDialogVisible" @close="closeDialog" title="工单详情" width="800" align-center>
            <div id="separate"></div>
            <div id="orderInfo">
                <div class="formTitle">工单基本信息</div>
                <div id="orderForm">
                    <el-form :inline="true" :model="formInline" class="demo-form-inline">
                        <el-form-item label="保养单号">
                            <el-input v-model="formInline.planId" clearable disabled/>
                        </el-form-item>
                        <el-form-item label="计划名称">
                            <el-input v-model="formInline.planName" clearable disabled/>
                        </el-form-item>
                        <el-form-item label="计划时间">
                            <el-date-picker v-model="formInline.planTime" type="date" clearable disabled/>
                        </el-form-item>
                        <el-form-item label="负责人员">
                            <el-input v-model="formInline.workerId" clearable disabled/>
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
                    <el-table :data="tableData" border style="width: 100%">
                        <el-table-column prop="date" label="设备名称" width="180" />
                        <el-table-column prop="name" label="设备编号" width="180" />
                        <el-table-column prop="address" label="执行时间" />
                        <el-table-column prop="address" label="保养内容" />
                        <el-table-column prop="address" label="执行备注" />
                        <el-table-column prop="address" label="保养情况" />
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
import { ref, reactive,watch } from 'vue'

// 模版数据
const formInline = reactive({
    planId:'计划编号',
    planName:'计划名称',
    planTime:'计划时间',
    workerId:'负责人员',
    orderDesc:'工单描述'
})

const onSubmit = () => {
    console.log('submit!')
}

const tableData = [
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
]

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
    dialogVisible:{
        type:Boolean,
        required:true
    },
    selectedOrder:{
        type:Object,
        required:true
    }
})

const centerDialogVisible = ref(props.dialogVisible)
const emit = defineEmits(['update:dialogVisible']);
// 监听对话框的关闭事件并发出更新事件
const closeDialog = () => {
  centerDialogVisible.value = false;
  emit('update:dialogVisible', false);
};

// 监听父组件传递的dialogVisible变化
watch(
  () => props.dialogVisible,(newVal) => {
    // console.log(2333);
    centerDialogVisible.value = newVal;
  });

// 如果selectedOrder变化，也需要更新formInline的数据
watch(() => props.selectedOrder, (newVal) => {
  Object.assign(formInline, newVal)
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
/* 分割线 */
#separate {
    position: absolute;
    left: 0;
    top:48px;
    width: 100%;
    border-bottom: 1px solid #ccc;
}
/* 调整表头为淡灰色并添加盒阴影 */
</style>