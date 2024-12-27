<template>
  <div>
    <el-form :model="form" label-width="80px">
      <el-form-item label="旧密码">
        <el-input type="password" v-model="form.oldPassword"></el-input>
      </el-form-item>
      <el-form-item label="新密码">
        <el-input type="password" v-model="form.newPassword"></el-input>
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input type="password" v-model="form.confirmPassword"></el-input>
      </el-form-item>
      <div class="dialog-footer">
        <el-button @click="resetForm">重置</el-button>
        <el-button type="primary" @click="submitForm">修改密码</el-button>
      </div>
    </el-form>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'

const form = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 重置表单
const resetForm = () => {
  form.oldPassword = ''
  form.newPassword = ''
  form.confirmPassword = ''
}

// 表单提交
const submitForm = () => {
  if (form.newPassword !== form.confirmPassword) {
    ElMessage.error('新密码与确认密码不一致')
    return
  }
  ElMessage.success('密码修改成功')
}
</script>

<style scoped>
.dialog-footer {
  margin-top: 20px;
  display: flex;
  justify-content: space-around;
}
</style>