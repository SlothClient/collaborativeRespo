<template>
    <div class="sideNavBar">
        <div id="navBall" v-show="ballStatus" @click="ballStatus = !ballStatus">
            {{ ballWord }}
        </div>
        <div id="navBar" v-show="!ballStatus">
            
        </div>
    </div>
</template>
<script lang="ts" setup>
import { ref } from "vue";

// hook useNav.ts
let ballStatus = ref(true);
let ballWord = ref("导航");
/**
 * 鼠标可以拖动navBall到任意位置
 */
function dragBall(){
    
}

let navList = ref([
    "首页",
    "产品",
    "服务"
])
</script>
<style scoped>
.sideNavBar {
    width: 200px;
    height: 100%;
    /* background-color: rgba(135, 211, 248, .9); */
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
    box-shadow: 5px 3px 6px 3px rgba(0, 0, 0, .2) ;
    position: absolute;
    top: 100px;
    left: 20px;
    cursor: pointer;
}
</style>