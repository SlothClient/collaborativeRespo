<template>
  <div :class="['sidebar', { collapsed: isCollapse }]">
    <el-menu
        :collapse="isCollapse"
        @open="handleOpen"
        @close="handleClose"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        :collapse-transition="false"
        :router="true"
        :unique-opened="true"
        default-active="/dashboard/home"
        aria-label="Site Navigation"
    >
      <!-- 动态菜单项 -->
      <side-bar-item
          v-for="item in menuItems"
          :key="item.path"
          v-memo="[item, isCollapse]"
          :item="item"
          base-path="/dashboard"
      />
    </el-menu>
  </div>
</template>

<script setup>
import { computed } from "vue";
import { useRouter } from "vue-router";
import SideBarItem from "./sideBarItem.vue";

const router = useRouter();

const menuItems = computed(() => {
  const homeRoute = router.getRoutes().find(routes => routes.name === 'dashboard');
  if (!homeRoute) {
    console.error('没找到dashboard');
    return [];
  }
  return homeRoute.children || [];
});

const props = defineProps({
  isCollapse: {
    type: Boolean,
    default: false
  }
});

const handleOpen = (key, keyPath) => {
  console.log('Opened:', key, keyPath);
};

const handleClose = (key, keyPath) => {
  console.log('Closed:', key, keyPath);
};
</script>

<style  scoped>
.sidebar {
  height: 100%;
  background-color: #304156;
  transition: width 0.3s;
  width: 200px;
  overflow-x: hidden;

  &.collapsed {
    width: 64px;
  }

  .el-menu {
    border-right: none;
  }

  .el-menu-item,
  .el-sub-menu__title {
    font-size: 14px;
    height: 50px;
    line-height: 50px;
  }

  .el-menu-item-group__title {
    padding: 0 20px;
    line-height: 30px;
    font-size: 12px;
    color: #909399;
  }

  .el-sub-menu .el-menu-item {
    min-width: 200px;
  }

  .el-menu-item .el-icon,
  .el-sub-menu__title .el-icon {
    margin-right: 5px;
    width: 24px;
    text-align: center;
    font-size: 18px;
  }

  .el-menu-item.is-active,
  .el-menu-item:hover,
  .el-sub-menu__title:hover {
    background-color: #263445 !important;
  }
}


.horizontal-collapse-transition {
  transition: 0s width ease-in-out, 0s padding-left ease-in-out, 0s padding-right ease-in-out;
}

@media (max-width: 768px) {
  .sidebar {
    width: 100%;

    &.collapsed {
      width: 64px;
    }
  }
}
</style>