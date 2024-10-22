<template>
  <div class="header-content">
    <div class="header-left">
      <h1>中药制剂运维系统</h1>
    </div>
    <div class="header-right">
      <el-dropdown @command="handleCommand" class="user-menu">
        <div class="avatar-section">
          <el-avatar size="large" :key="userStore.user.avatar+new Date().getTime()" :src="userStore.user.avatar"/>
          <el-icon class="dropdown-icon">
            <arrow-down/>
          </el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item  command="logout"><el-icon><SwitchButton /></el-icon>注销</el-dropdown-item>
            <el-dropdown-item command="personalCenter"><el-icon><User /></el-icon>个人中心</el-dropdown-item>
            <el-dropdown-item command="message"><el-icon><Message /></el-icon>通知</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
  <PersonalInformation
      :personalCenterVisible=personalCenterVisible
      @closeDialog="closeDialog"
  >
  </PersonalInformation>

  <MessageDialog
      :messageDialogVisible=messageDialogVisible
      @closeDialog="closeMessageDialog"
  >
  </MessageDialog>

</template>

<script setup>
import {useUserStore} from "@/store/module/user.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {ref} from "vue";
import PersonalInformation from "@/components/personalCenter/index.vue";
import MessageDialog from  '@/components/message/index.vue'
import {useMessageStore} from "@/store/module/message.js";
const userStore = useUserStore();
const personalCenterVisible = ref(false)
const messageDialogVisible = ref(false)
const messageStore = useMessageStore();
const handleCommand = (command) => {
  switch (command) {
    case "logout": {
      ElMessageBox.confirm(
          '确定退出吗?',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            ElMessage({
              type: 'success',
              message: '退出成功',
            })
            userStore.Logout();
            messageStore.clearMessage();
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '取消退出',
            })
          })

      break;
    }
    case "personalCenter":
      personalCenterVisible.value = true
      break
    case "message":
      messageDialogVisible.value = true
          break
  }
}
const closeMessageDialog = () =>{
  messageDialogVisible.value = false
}

const closeDialog = () =>{
  personalCenterVisible.value = false
}

</script>

<style scoped>
.header-content {
  display: flex;
  height: 10%;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  width: 100%;
  background-color: #ffffff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0);
}

.header-left h1 {
  font-size: 26px;
  color: #4999e8;
  margin: 0;
  font-weight: bold;
}

.header-right {
  display: flex;
  align-items: center;
}

.avatar-section {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.dropdown-icon {
  margin-left: 8px;
  color: #2c3e50;
}

.el-dropdown-menu {
  min-width: 120px;
}

.el-dropdown-item {
  font-size: 14px;
}

</style>