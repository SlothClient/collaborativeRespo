<template>
    <div class="header">
        <div class="left">
            <div class="logo">
                <img src="@/assets/logo.jpg" alt="logo">
            </div>
            <div class="title">
                <h3>信息管理平台</h3>
            </div>
        </div>
        <div class="center">
            <span>欢迎您：</span>
            <span id="cu">{{ currentUser === ''?'非法用户':currentUser }}</span>
        </div>
        <div class="right">
            <div class="btns">
                <button>刷新</button>
                <button @click="logout">退出</button>
            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>
    import useVerifyUser from '@/hooks/useVerifyUser'
    const {currentUser} = useVerifyUser()

    import useLogout from '@/hooks/useLogout'
    const {logout} = useLogout()
</script>
<style scoped>
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px 20px;
        background-color: rgba(0, 162, 211, 1);
    }
    .left {
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .left .title {
        margin-left: 18px;
        color: rgba(255, 255, 255, 1);
    }
    .logo {
        /* background-color: rgba(175, 89, 62, .5);
        padding: 8px;
        border-radius: 1000px;
        width: 81px;
        height: 81px;
        border: none; */
        display: flex;
        border-radius: 1000px;
        box-shadow: 0 0 9px 6px rgba(175, 89, 62, .5);
    }
    .logo img {
        width: 66px;
        border-radius: 1000px;
    }
    .right .btns button {
        border: none;
        margin-left: 20px;
        width: 90px;
        height: 30px;
        background-color: rgba(31, 38, 42, 1);
        color: rgba(235, 236, 240, 1);
        border-radius: 5px;
        cursor: pointer;
        box-shadow: 0 0 12px 2px rgba(235, 236, 240, .3);
    }
    .center {
        font-weight: bold;
        font-size: medium;
        font-family: serif;
        color: rgba(255, 255, 255, 1);
    }
    #cu {
        font-size: 19px;
        color: #666;
        text-decoration: underline;
        text-shadow: 1px 1px 1px #000;
        position: relative;
        bottom: 2px;
    }
</style>