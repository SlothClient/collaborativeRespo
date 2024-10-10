<template>
    <div id="detailDialog">
        <el-dialog v-model="centerDialogVisible" title="工单详情" width="800" align-center>
            <div id="separate"></div>
            <div id="orderInfo">
                <div class="formTitle">工单基本信息</div>
                <div id="orderForm">
                    <el-form :inline="true" :model="formInline" class="demo-form-inline">
                        <el-form-item label="保养单号">
                            <el-input v-model="formInline.user" placeholder="保养单号" clearable disabled/>
                        </el-form-item>
                        <el-form-item label="计划名称">
                            <el-input v-model="formInline.user" placeholder="计划名称" clearable disabled/>
                        </el-form-item>
                        <el-form-item label="计划时间">
                            <el-date-picker v-model="formInline.date" type="date" placeholder="计划时间" clearable disabled/>
                        </el-form-item>
                        <el-form-item label="负责人员">
                            <el-input v-model="formInline.user" placeholder="负责人员" clearable disabled/>
                        </el-form-item>
                        <el-form-item label="工单描述">
                            <el-input v-model="formInline.user" type="textarea" style="width: 540px;" placeholder="工单描述" clearable />
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
import { ref, reactive } from 'vue'

const centerDialogVisible = ref(true)

// 模版数据
const formInline = reactive({
    user: '',
    region: '',
    date: '',
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