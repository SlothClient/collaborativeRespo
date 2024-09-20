<template>
  <el-dialog v-model="dialogVisible">
    <span>
      保养计划详情框
    </span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<script setup>
import {computed, ref, watch} from "vue";

const props = defineProps(
    {
      maintenancePlanDetailVisible: {
        type:Boolean,
        default: () => false
      }
    }
)
const dialogVisible = ref(false)

watch(props.maintenancePlanDetailVisible, (newValue, oldValue)=>{
      dialogVisible.value = newValue.value
    }
)

</script>

<style scoped>

</style>