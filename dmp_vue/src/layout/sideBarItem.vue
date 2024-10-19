<template>
  <el-menu-item v-if="!item.children || item.children.length === 0" :index="resolvePath(item.path)">
    <el-icon v-if="item.meta && item.meta.icon">
      <component :is="item.meta.icon" />
    </el-icon>
    <template #title>{{ item.meta.title }}</template>
  </el-menu-item>

  <el-sub-menu v-else :index="resolvePath(item.path)" :popper-append-to-body="false">
    <template #title>
      <el-icon v-if="item.meta && item.meta.icon">
        <component :is="item.meta.icon" />
      </el-icon>
      <span>{{ item.meta.title }}</span>
    </template>
    <!-- 递归渲染子菜单 -->
    <side-bar-item
        v-for="child in item.children"
        :key="child.path"
        :item="child"
        :base-path="resolvePath(item.path)"
    />
  </el-sub-menu>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  item: {
    type: Object,
    required: true
  },
  basePath: {
    type: String,
    default: ''
  }
});

const resolvePath = (path) => {
  if (path.startsWith('/')) {
    return path;
  }

  if (!props.basePath) {
    return path;
  }

  return props.basePath ? `${props.basePath}/${path}`.replace(/\/+/g, '/') : path;
};
computed(() => {
  return props.item.children && props.item.children.length > 0;
});
</script>

<style scoped>
/* 可以根据需要添加特定于项目的样式 */
</style>