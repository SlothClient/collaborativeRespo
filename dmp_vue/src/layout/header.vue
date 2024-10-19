<template>
  <div class="header-content">
    <h1 style="margin:auto;">中药制剂运维系统</h1>
    <el-dropdown @command="handleCommand">
      <el-avatar size='large' src=""/>
      <el-icon class="el-icon--right">
        <arrow-down/>
      </el-icon>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item command="logout">注销</el-dropdown-item>
          <el-dropdown-item>个人中心</el-dropdown-item>
          <el-dropdown-item>其他</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>


<script setup>

import {useUserStore} from "@/store/module/user.js";


const userStore = useUserStore()

const handleCommand = (command) => {
  switch (command) {
    case "logout": {
      userStore.Logout()
      break
    }
  }


}
</script>

<style scoped>
.header-content {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
}

h1 {
  font-size: 24px;
  color: #333;
  margin: 0;
}
</style>
