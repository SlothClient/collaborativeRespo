<template>
  <el-dialog
      @close="closeDialog"
      :close-on-click-modal="false"
      v-model="messageDialogVisible"
      title="消息通知"
      width="900"
      center>
    <div class="notification-container">
      <NotificationTabs/>
      <NotificationList/>
      <NotificationDetails/>
    </div>
  </el-dialog>
</template>

<script setup>
import NotificationTabs from './NotificationTabs.vue';
import NotificationList from './NotificationList.vue';
import NotificationDetails from './NotificationDetials.vue';

import {computed, ref, watch} from "vue";

const props = defineProps({
  messageDialogVisible: {
    type: Boolean,
    default: false
  }
})

const emits = defineEmits(["closeDialog"])

const closeDialog = () => {
  messageDialogVisible.value = false
  emits('closeDialog')
}

watch(() => props.messageDialogVisible, newVal => messageDialogVisible.value = newVal)

const messageDialogVisible = ref(props.messageDialogVisible)




</script>


<style scoped>

.notification-container {
  display: flex;
  height: 500px;
}


</style>