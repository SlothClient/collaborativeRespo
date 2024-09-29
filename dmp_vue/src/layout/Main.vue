<script setup>
import {useRouter} from "vue-router";

const router = useRouter()

</script>
<template>
  <el-container>
    <el-header style="display: flex;align-items: center;gap: 16px">
      <el-icon>
        <Memo/>
      </el-icon>
      <p>
        中药制剂运维系统
      </p>
    </el-header>
    <el-container style="height: 100%">
      <el-aside width="200px">
        <el-menu
            style="height: 100%"
            active-text-color="#ffd04b"
            background-color="#545c64"
            class="el-menu-vertical-demo"
            default-active="1"
            text-color="#fff"
        >
          <el-menu-item index="1" @click="router.replace('/')">
            <el-icon>
              <HomeFilled/>
            </el-icon>
            <span>系统首页</span>
          </el-menu-item>
          <el-menu-item index="2" @click="router.replace('/views/Device')">
            <el-icon>
              <MessageBox/>
            </el-icon>
            <span>设备信息</span>
          </el-menu-item>
          <el-menu-item index="3" @click="router.replace('/views/Maintenance')">
            <el-icon>
              <SetUp/>
            </el-icon>
            <span>保养计划</span>
          </el-menu-item>
          <el-menu-item index="4" @click="router.replace('/views/Approve')">
            <el-icon>
              <document/>
            </el-icon>
            <span>审批管理</span>
          </el-menu-item>
          <el-sub-menu index="5">
            <template #title>
              <el-icon>
                <DocumentChecked/>
              </el-icon>
              <span>工单管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="5-1" @click="router.replace('/views/WorkOrder')">
                <el-icon>
                  <SetUp/>
                </el-icon>
                工单计划
              </el-menu-item>
              <el-menu-item index="5-2" @click="router.replace('/views/WorkHours')">
                <el-icon>
                  <SetUp/>
                </el-icon>
                工时管理
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-main>
        <RouterView/>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
* {
  margin: 0px;
  padding: 0px;
}

html, body, #box, .el-container, .el-menu {
  height: 100%;
  overflow: hidden;
}

.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #ffffff;
  color: #333;
  //overflow: hidden;
  /* text-align: center;
  line-height: 160px; */
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>