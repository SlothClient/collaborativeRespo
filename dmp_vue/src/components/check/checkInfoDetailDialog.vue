<template>
  <div id="detailDialog">
    <el-dialog v-model="dialogVisible" @close="closeDialog" title="详情" width="800" align-center>
      <div id="separate"></div>
      <div id="orderInfo">
        <div class="formTitle">点巡检计划基本信息</div>
        <div id="orderForm">
          <el-form :inline="true" class="demo-form-inline" disabled>
            <el-form-item label="计划编号">
              <el-input v-model="checkInfoDetail.checkId" clearable />
            </el-form-item>
            <el-form-item label="计划名称">
              <el-input v-model="checkInfoDetail.checkName" clearable />
            </el-form-item>
            <el-form-item label="开始时间">
              <el-date-picker v-model="checkInfoDetail.startTime" type="datetime" clearable />
            </el-form-item>
            <el-form-item label="结束时间">
              <el-date-picker v-model="checkInfoDetail.endTime" type="datetime" clearable />
            </el-form-item>
            <el-form-item label="点巡检描述">
              <el-input v-model="checkInfoDetail.checkDesc" type="textarea" style="width: 540px;" clearable />
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div id="equipInfo">
        <div class="formTitle">点巡检项目明细</div>
        <div id="equipTable">
          <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="checkitemName" label="检查项名称" align="center" />
            <el-table-column prop="checkitemWay" label="检查项方法" align="center" />
            <el-table-column prop="checkitemOut" label="检查项结果" align="center" />
            <el-table-column  label="添加" align="center">
              <template #header>
                <el-icon><CirclePlus /></el-icon>添加
              </template>
              <template #default="scope">
                <el-popconfirm title="确定删除吗?" @confirm="deleteRow(scope.row.checkitemId)" confirm-button-text="确定" cancel-button-text="取消">
                  <template #reference>
                    <el-button link size="mini" color="rgb(159.5, 206.5, 255)" style="width: 50px;" type="danger"><el-icon><Delete /></el-icon>删除</el-button>
                  </template>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="closeDialog" type="info" plain>关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import { computed, reactive, ref, watch, onMounted } from "vue";
import { useEquipmentInfoStore } from "@/store/module/equipmentInfo.js";
import { getCheckitem } from "@/api/checkitem/index.js";
import { Delete, Edit } from "@element-plus/icons-vue";

const formInline = reactive({
  planId: '计划编号',
  planName: '计划名称',
  planTime: '开始时间',
  workerName: '结束时间',
  orderDesc: '点巡检描述'
});

const props = defineProps({
  checkInfoDetailVisible: {
    type: Boolean,
    required: false
  },
  checkInfoDetail: {
    type: Object,
    default: () => null,
  }
});

const dialogVisible = ref(props.checkInfoDetailVisible);
const emit = defineEmits(['closeDialog']);

watch(
    () => props.checkInfoDetailVisible,
    (newValue) => {
      dialogVisible.value = newValue;
    }
);

const tableData = ref([]);

const getList = async (val) => {
  if (!val) return; // 确保 checkId 不为空
  const res = await getCheckitem(val);
  console.log(res);
  tableData.value = res.data.data;
  console.log(tableData.value);
};

// 监听 checkInfoDetail 的变化
watch(() => props.checkInfoDetail, async (newVal) => {
  if (newVal && newVal.checkId) {
    await getList(newVal.checkId);
  } else {
    tableData.value = [];
  }
});

const closeDialog = () => {
  dialogVisible.value = false;
  emit('closeDialog');
};

onMounted(async () => {
  if (props.checkInfoDetail && props.checkInfoDetail.checkId) {
    await getList(props.checkInfoDetail.checkId);
  }
});
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
  top: 48px;
  width: 100%;
  border-bottom: 1px solid #ccc;
}

/* 调整表头为淡灰色并添加盒阴影 */
.el-table .el-table__header th {
  background-color: #000 !important;
  /* 调不到 */
}

#equipTable {
  box-shadow: 1px 3px 3px 1px #eee;
}
</style>
