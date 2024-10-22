<template>
  <el-dialog
      title="高级搜索"
      v-model="visible"
      width="600px"
      :before-close="closeDialog"
  >
    <el-form :model="searchForm" label-width="120px" class="search-form">
      <!-- 计划名称 -->
      <el-form-item label="计划名称">
        <el-input v-model="searchForm.planName" placeholder="输入计划名称"></el-input>
      </el-form-item>

      <!-- 计划状态 -->
      <el-form-item label="计划状态">
        <el-checkbox-group v-model="searchForm.status">
          <el-checkbox-button
              v-for="(status, index) in statusOptions"
              :key="index"
              :label="status.label"
              :value="status.value"
          >{{ status.label }}
          </el-checkbox-button
          >
        </el-checkbox-group>
      </el-form-item>

      <!-- 日期范围选择 -->
      <el-form-item label="时间范围">
        <el-date-picker
            v-model="searchForm.dateRange"
            type="datetimerange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            size="small"
            format="YYYY/MM/DD HH:mm:ss"
            value-format="YYYY/MM/DD HH:mm:ss"
            style="width: 200px"
        ></el-date-picker>
      </el-form-item>

      <!-- 计划类型 -->
      <el-form-item label="计划类型">
        <el-select v-model="searchForm.maintenanceTypeId" placeholder="选择计划类型">
          <el-option
              v-for="(plan, index) in EquipmentInfoStore.equipmentMaintenanceType"
              :key="index"
              :label="plan.maintanceName"
              :value="plan.typeId"
          ></el-option>
        </el-select>
      </el-form-item>

      <!-- 计划类型 -->
      <el-form-item label="计划设备">
        <el-select v-model="searchForm.equipId" placeholder="选择计划设备">
          <el-option
              v-for="(equip, index) in EquipmentInfoStore.equipmentInfo"
              :key="index"
              :label="equip.equipName"
              :value="equip.equipId"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="计划发起人">
        <el-select v-model="searchForm.creator" placeholder="选择计划发起人">
          <!--          <el-option-->
          <!--              v-for="(creator, index) in EquipmentInfoStore.equipmentInfo"-->
          <!--              :key="index"-->
          <!--              :label="equip.equipName"-->
          <!--              :value="equip.equipId"-->
          <!--          ></el-option>-->
        </el-select>
      </el-form-item>

      <!-- 操作按钮 -->
      <el-form-item>
        <div class="form-footer">
          <el-button @click="resetSearch" icon="Refresh">重置</el-button>
          <el-button type="primary" @click="handleSearch" icon="Search">查询</el-button>
        </div>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup>
import {ref, watch} from 'vue'
import {useEquipmentInfoStore} from "@/store/module/equipmentInfo.js";

const EquipmentInfoStore = useEquipmentInfoStore()

const props = defineProps({
  advancedSearchDialogVisible: {
    type: Boolean,
    default: false
  }
})
const emits = defineEmits(["closeAdvancedSearchDialog", "advancedSearchPlan"])
const visible = ref(props.advancedSearchDialogVisible)
watch(() => props.advancedSearchDialogVisible, newVal => {
  visible.value = newVal
})

//关闭对话框
const closeDialog = () => {
  visible.value = false;
  resetSearch()
  emits("closeAdvancedSearchDialog")
}

const searchForm = ref({
  planName: '',
  status: [],
  dateRange: [],
  equipId: '',
  maintenanceTypeId: '',
  creator: '',
})

// 状态选项
const statusOptions = [
  {label: '待审批', value: 0},
  {label: '审批中', value: 1},
  {label: '未通过', value: 3},
  {label: '待开始', value: 2},
  {label: '执行中', value: 7},
  {label: '已完成', value: 8}
]

// 查询操作
const handleSearch = () => {
  console.log(searchForm.value.dateRange)
  const advancedSearchParam = {
    maintenanceTypeId: searchForm.value.maintenanceTypeId,
    creator: searchForm.value.creator,
    startTime: searchForm.value.dateRange[0] == null ? null :searchForm.value.dateRange[0],
    endTime: searchForm.value.dateRange[1] == null? null :searchForm.value.dateRange[1],
    equipId: searchForm.value.equipId,
    status: searchForm.value.status,
    planName: searchForm.value.planName
  }
  console.log(advancedSearchParam)
  emits('advancedSearchPlan', advancedSearchParam)
  visible.value = false
  closeDialog()
  emits('closeAdvancedSearchDialog')
}

// 重置搜索条件
const resetSearch = () => {
  searchForm.value.planName = '';
  searchForm.value.status = [];
  searchForm.value.dateRange = [];
  searchForm.value.equipId = '';
  searchForm.value.maintenanceTypeId = '';
  searchForm.value.creator = '';
}
</script>

<style scoped>
.search-form {
  padding-top: 10px;
}

.date-picker {
  width: 100%;
}

.form-footer {
  display: flex;
  justify-content: flex-end;
}
</style>