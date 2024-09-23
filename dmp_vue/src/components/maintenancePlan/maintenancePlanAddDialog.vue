<template>
  <el-dialog v-model="dialogVisible" :before-close="closeDialog">
   <span>
   保养计划添加框
 </span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="closeDialog()">Cancel</el-button>
        <el-button type="primary" @click="closeDialog()">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref, watch} from 'vue';

const props = defineProps({
  maintenancePlanAddVisible: {
    type: Boolean,
    default: false
  }
});

const dialogVisible = ref(props.maintenancePlanAddVisible);
const emit = defineEmits(["closeDialog"])
watch(() => props.maintenancePlanAddVisible, (newValue) => {
  dialogVisible.value = newValue;
});
const closeDialog = () => {
  dialogVisible.value = !dialogVisible.value;
  emit("closeDialog")
}


</script>

<style scoped>
</style>