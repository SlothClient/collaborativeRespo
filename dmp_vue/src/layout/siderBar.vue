<template>
  <el-menu
      :default-active="activeMenu"
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      background-color="#304156"
      text-color="#fff"
      active-text-color="#409EFF"
      router
  >
    <el-menu-item index="/dashboard/home">
      <el-icon><HomeFilled /></el-icon>
      <span>Home</span>
    </el-menu-item>
    <el-menu-item index="/dashboard/Device">
      <el-icon><Cpu /></el-icon>
      <span>Device</span>
    </el-menu-item>
    <el-menu-item index="/dashboard/maintenance">
      <el-icon><Tools /></el-icon>
      <span>Maintenance</span>
    </el-menu-item>
    <el-menu-item index="/dashboard/WorkHours">
      <el-icon><Clock /></el-icon>
      <span>Work Hours</span>
    </el-menu-item>
    <el-menu-item index="/dashboard/WorkOrder">
      <el-icon><Document /></el-icon>
      <span>Work Order</span>
    </el-menu-item>
    <el-menu-item index="/dashboard/approve">
      <el-icon><Check /></el-icon>
      <span>Approve</span>
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRoute } from 'vue-router';
import { HomeFilled, Cpu, Tools, Clock, Document, Check } from '@element-plus/icons-vue';

const isCollapse = ref(false);
const route = useRoute();

// 获取当前激活的菜单
const activeMenu = computed(() => {
  return route.path.startsWith('/dashboard') ? route.path : '/dashboard/home';
});
</script>

<style scoped>
.el-menu-vertical-demo {
  height: 100%;
  border-right: none;
}
</style>