<template>
  <div id="login">
    <div id="contain">
      <!-- 左侧卡片，显示欢迎信息 -->
      <div id="left_card">
        <h1>欢迎来到中药制剂运维系统</h1>
        <span>Welcome to Chinese Medicine Preparation Operation and Maintenance System</span>
      </div>

      <!-- 右侧卡片，登录表单 -->
      <div id="right_card">
        <el-card class="login-card">
          <h2 class="login-title">用户登录</h2>
          <el-form
              ref="loginFormRef"
              :model="loginForm"
              status-icon
              :rules="rules"
              label-width="0"
              class="login-form"
          >
            <el-form-item prop="username">
              <el-input
                  v-model="loginForm.username"
                  type="text"
                  autocomplete="off"
                  placeholder="请输入用户名"
                  :prefix-icon="User"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                  v-model="loginForm.userpwd"
                  type="password"
                  autocomplete="off"
                  placeholder="请输入密码"
                  :prefix-icon="Lock"
                  show-password
              />
            </el-form-item>
            <el-form-item>
              <el-button :disabled="loading.value" type="primary" @click="submitForm(loginFormRef)" class="login-button">
                <span v-if="!loading">登 录</span>
                <span v-else>登 录中...</span>
              </el-button>
            </el-form-item>
            <el-form-item>
              <el-button @click="resetForm(loginFormRef)" class="reset-button">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </div>
  </div>
</template>
<script setup>
import {reactive, ref} from 'vue'
import {User, Lock} from '@element-plus/icons';
import {router} from '@/router/index.js';
import {ElMessage} from "element-plus";
import {useUserStore} from "@/store/module/user.js";

const loading = ref(false);

const userStore = useUserStore()

const loginFormRef = ref()

const loginForm = reactive({
  username: 'user' ,
  userpwd: 'password123',
})

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else if (value.length < 1 /*|| value.length >= 10*/) {
    callback(new Error('用户名不符合规则'))
  } else {
    callback()
  }
}

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else if (value.length < 3 /*|| value.length >= 10*/) {
    callback(new Error('密码不符合规则'))
  } else {
    callback()
  }
}

const rules = reactive({
  username: [{validator: validateUsername, trigger: 'blur'}],
  userpwd: [{validator: validatePassword, trigger: 'blur'}],
})

const submitForm = async (formEl) => {
  console.log(formEl)
  if (!formEl) return
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      loading.value = true
      const res = await userStore.Login(loginForm)
      if (!res) {
        ElMessage.error('登入失败')
        loading.value = false
      } else {
        ElMessage.success('登录成功')
        loading.value = false
        await router.push('/dashboard/home')
      }
    } else {
      console.log('error submit!', fields)
      ElMessage.error('提交失败，请检查表单')
    }
  })
}

const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
<style scoped>
#login {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  background-size: cover;
  background-position: center;
  animation: backgroundAnimation 10s linear infinite; /* 10秒循环切换 */
}

/* 定义背景图片切换的动画 */
@keyframes backgroundAnimation {
  0% {
    background-image: url('@/assets/images/bg1.webp');
  }
  50% {
    background-image: url('@/assets/images/bg2.webp');
  }
  100% {
    background-image: url('@/assets/images/bg1.webp');
  }
}

#contain {
  display: flex;
  flex-direction: row;
  width: 80%;
  max-width: 900px;
  height: 450px;
  background-color: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

#left_card {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #409EFF;
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  padding: 20px;
}

#left_card h1 {
  color: white;
  font-size: 2rem;
  margin-bottom: 20px;
}

#left_card span {
  color: white;
  font-size: 1.2rem;
}

#right_card {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

.login-card {
  width: 100%;
  padding: 20px;
  border-radius: 8px;
  background-color: white;
}

.login-title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}

.el-input {
  margin-bottom: 20px;
  background-color: #f4f6f8;
  border-radius: 4px;
}

.login-button {
  width: 100%;
  padding: 10px;
  background-color: #409EFF;
  color: white;
  border-radius: 4px;
  font-size: 16px;
}

.reset-button {
  width: 100%;
  padding: 10px;
  background-color: #f0f0f0;
  color: #333;
  border-radius: 4px;
  font-size: 16px;
  margin-top: 10px;
}
</style>