<template>
  <div class="common-layout">
    <el-container>
      <el-aside :width="isCollapse ? '64px' : '200px'" height="100%" class="el-aside">
        <div class="el-aside__logo"></div>
        <sideBar :isCollapse="isCollapse"></sideBar>
      </el-aside>
      <el-container>
        <el-header class="main-header">
          <el-button @click="isCollapse = !isCollapse" class="collapse-button">
            <el-icon>
              <DArrowRight v-if="isCollapse"/>
              <DArrowLeft v-else/>
            </el-icon>
          </el-button>
          <Header/>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import Header from "./header.vue";
import SideBar from "./DynamicSideBar.vue";
import {ref} from 'vue'

const isCollapse = ref(true);

</script>

<style scoped>
.common-layout {
  padding: 0;
  margin: 0;
  height: 100%;
}

.el-aside {
  transition: width 0.3s;
  position: relative;
  background-color: #304156;

  .collapse-button {
    position: absolute;
    top: 10px;
    right: -20px;
    transform: translateY(-50%);
    transition: right 0.3s;
  }
}

.el-container {
  height: 100%;
}

.main-header {
  height:10%;
  width: 100%;
  display: flex;
  align-items: center;
  background-color: #ffffff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  padding: 0 20px;
  position: relative;
}
</style>