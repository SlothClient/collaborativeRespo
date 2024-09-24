<template>
  <div class="login-container">
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
          <el-button type="primary" @click="submitForm(loginFormRef)" class="login-button">
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
  username: 'admin',
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
        await router.push('/')
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
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;

}

.login-card {
  width: 350px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
  opacity: 0.8;
}

.login-title {
  text-align: center;
  margin-bottom: 30px;
  color: #409EFF;
}

.login-form {
  margin-top: 20px;
}

.login-button {
  width: 100%;
  margin-top: 20px;
}

.reset-button {
  width: 100%;
  margin-top: 10px;
}

.el-input {
  margin-bottom: 20px;
}
</style>