<template>
  <div class="tap-container">
    <el-tabs v-model="messageStore.activeCategory" tab-position="left" @tab-click="handleTabClick">
      <el-tab-pane label="系统消息" name="System" />
      <el-tab-pane label="用户消息" name="Common" />
      <el-tab-pane label="审批消息" name="Approval" />
    </el-tabs>
  </div>
</template>

<script setup>
import { useMessageStore } from '@/store/module/messageStore.js';

const messageStore = useMessageStore();

const handleTabClick = (tab) => {
  messageStore.setActiveCategory(tab.props.name);
};
</script>

<style scoped>
.el-tabs__nav {
  width: 200px;
}
.tap-container{
  width: 10%;
}
</style>