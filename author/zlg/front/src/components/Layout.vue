<template>
    <!-- 头部 -->
    <Header />
    <!-- 内容区 -->
    <Main />
</template>
<script lang="ts" setup>
import Header from "@/components/Header.vue";
import Main from "@/components/Main.vue";
import { onMounted } from "vue";
import {useRouter} from "vue-router";
const router = useRouter();


onMounted(() => {
    document.title = "首页";
});
</script>
<style scoped>
#main {
    display: flex;
    width: 100%;
    height: 100%;
}

</style>