<template>
  <el-dialog v-model="dialogVisible" :before-close="closeDialog">
   <span>
   编辑保养计划详情框
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
import { ref, watch} from 'vue';

const props = defineProps({
  maintenancePlanEditVisible: {
    type: Boolean,
    default: false
  }
});

const dialogVisible = ref(props.maintenancePlanEditVisible);
const emit = defineEmits(["closeDialog"])
watch(() => props.maintenancePlanEditVisible, (newValue) => {
  dialogVisible.value = newValue;
});
const closeDialog = () =>{
  dialogVisible.value = !dialogVisible.value;
  emit("closeDialog")
}


</script>

<style scoped>
</style>