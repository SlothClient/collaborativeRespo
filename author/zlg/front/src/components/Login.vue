<template>
    <div id="Login">
        <div id="loginApp">
            <el-form ref="loginForm" :model="loginInfo" :rules="rules" class="demo-form-inline" label-width="80px">
                <h3 class="loginTitle">管理系统登录</h3>
                <el-form-item label="用户名" prop="uName">
                    <el-input class="needInput" v-model="loginInfo.uName"></el-input>
                </el-form-item>
                <p></p>
                <el-form-item label="密码" prop="uPwd">
                    <el-input class="needInput" v-model="loginInfo.uPwd" type="password" @keyup.enter="login"></el-input>
                </el-form-item>
                <p></p>
                <el-form-item class="loginBtn">
                    <el-button type="primary" @click="login">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup lang="ts">
import useLogin from '@/hooks/useLogin';

const { loginInfo, loginForm, rules, login } = useLogin();
</script>

<style scoped>
#Login {
    width: 100%;
    height: 100%;
    background: url(../assets/bgp.jpg) center center no-repeat;
    background-size: cover;
    background-color: rgba(0, 0, 0, .3);
    background-blend-mode: multiply;
    display: flex;
    justify-content: center;
    align-items: center;
}

#loginApp {
    width: 688px;
    height: 388px;
    background-color: rgba(255, 255, 255, .6);
    border-radius: 18px;
    box-shadow: 5px 8px 6px 3px #111;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    padding: 10px 0;
}

.loginTitle {
    width: fit-content;
    margin: 20px auto;
    font-size: 20px;
    font-family: "华文中宋";
    color: aliceblue;
}

.el-form-item__label {
    color: #fff;
    margin-left: -80px;
}

.el-form-item {
    margin: 20px 0;
}

.needInput {
    width: 320px;
}

.loginBtn {
    width: 100%;
    text-align: center;
}

.el-button {
    width: 100px;
}
</style>