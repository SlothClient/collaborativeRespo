<template>
  <el-dialog v-model="dialogVisible" :before-close="closeDialog">
   <span>
   保养计划详情框
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
  maintenancePlanDetailVisible: {
    type: Boolean,
    default: false
  }
});

const dialogVisible = ref(props.maintenancePlanDetailVisible);
const emit = defineEmits(["closeDialog"])
watch(() => props.maintenancePlanDetailVisible, (newValue) => {
  dialogVisible.value = newValue;
});
const closeDialog = () =>{
  dialogVisible.value = !dialogVisible.value;
  emit("closeDialog")

}


</script>

<style scoped>
</style>