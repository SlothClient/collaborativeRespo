<template>
  <el-dialog
      @close="closeDialog"
      :close-on-click-modal="false"
      v-model="centerDialogVisible"
      title="个人中心"
      width="600"
      center>

    <div class="personal-center-container">
      <!-- Tabs 功能选项 -->
      <el-tabs v-model="activeTab" @tab-click="handleTabClick">
        <el-tab-pane label="个人资料" name="UserProfile">
          <UserProfile/>
        </el-tab-pane>
        <el-tab-pane label="修改密码" name="ChangePassword">
          <ChangePassword/>
        </el-tab-pane>
        <el-tab-pane label="账号设置" name="AccountSettings">
          <AccountSettings/>
        </el-tab-pane>
      </el-tabs>
    </div>
  </el-dialog>
</template>
<script setup>
import { ref, watch} from 'vue'

// 导入各个功能的组件
import UserProfile from './UserProfile.vue'
import ChangePassword from './ChangePassword.vue'
import AccountSettings from './AccountSettings.vue'

const props = defineProps({
  personalCenterVisible: {
    type: Boolean,
    default: false
  }
})

const emits = defineEmits(["closeDialog"])

const centerDialogVisible = ref(props.personalCenterVisible)
const activeTab = ref('UserProfile') // 默认显示的 Tab

watch(() => props.personalCenterVisible, newVal => centerDialogVisible.value = newVal)

// 关闭对话框
const closeDialog = () => {
  centerDialogVisible.value = false
  emits('closeDialog')
}

// Tab 切换逻辑
const handleTabClick = (tab) => {
  activeTab.value = tab.name
}
const handleClose = ()=>{

}
</script>
<style scoped>
.personal-center-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.el-tabs {
  flex: 1;
  padding: 20px;
}

.el-tab-pane {
  padding: 20px;
}
</style>