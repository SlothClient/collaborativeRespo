<template>
    <div id="main">
        <!-- 左侧导航区域 -->
        <div id="SideNav">
            <div id="navBall" v-show="ballStatus" @click="ballStatus = !ballStatus">
                {{ ballWord }}
            </div>
            <div id="navBar" v-show="!ballStatus">
                <RouterLink v-for="nav in navList" :key="nav.id" :to="`${nav.path}/${nav.api}`" active-class="active">{{ nav.name }}</RouterLink>
            </div>
        </div>
        <!-- 右侧展示区 -->
        <RouterView />
    </div>
</template>
<script lang="ts" setup>
import { ref } from "vue";
import { RouterLink, RouterView } from "vue-router";

// hook useNav.ts
let ballStatus = ref(false);
let ballWord = ref("导航");
let navList = ref([
    {id:0,name:"保养管理",api:"getMaintance",path:"/data"},
    {id:1,name:"工单管理",api:"getOrder",path:"/data"},
    {id:2,name:"其他管理",api:"getSth",path:"/data"}
]);
/**
 * 鼠标可以拖动navBall到任意位置
 */
function dragBall() {

}
</script>
<style scoped>
#sideNav {
    width: 200px;
    height: 100%;
}

#navBall {
    width: 99px;
    height: 38px;
    text-align: center;
    line-height: 38px;
    color: rgba(45, 12, 6, 1);
    font-weight: 600;
    font-family: "幼圆";
    background-color: rgba(135, 211, 248, 1);
    border-radius: 1000px;
    box-shadow: 5px 3px 6px 3px rgba(0, 0, 0, .2);
    position: absolute;
    top: 100px;
    left: 20px;
    cursor: pointer;
}

#navBar {
    width: 200px;
    height: 100%;
    background-color: rgba(135, 211, 248, .9);
}
#navBar a {
    display: block;
    width: 100%;
    height: 45px;
    line-height: 45px;
    text-align: center;
    text-decoration: none;
    color: rgb(31, 38, 42);
    /* background-color: #fff;
    border: 1px solid #000; */
}
#navBar a.active {
    background-color: rgba(126, 182, 255, .9);
    color: rgb(240, 248, 255);
    font-weight: 500;
}
</style>