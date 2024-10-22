<template>
  <el-tabs v-model="messageStore.activeCategory" tab-position="left" @tab-click="handleTabClick">
    <el-tab-pane label="系统消息" name="system" />
    <el-tab-pane label="用户消息" name="user" />
    <el-tab-pane label="审批消息" name="approval" />
  </el-tabs>
</template>

<script setup>
import { useMessageStore } from '@/store/module/message.js';

const messageStore = useMessageStore();

const handleTabClick = (tab) => {
  messageStore.setActiveCategory(tab.props.name);
};
</script>

<style scoped>
.el-tabs__nav {
  width: 200px;
}
</style>