<template>
  <div>
    <el-form :model="form" label-width="80px">
      <el-form-item label="绑定手机号">
        <el-input v-model="form.phone"></el-input>
      </el-form-item>
      <el-form-item label="绑定邮箱">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="推送通知">
        <el-switch v-model="form.notifications"></el-switch>
      </el-form-item>
      <div class="dialog-footer">
        <el-button @click="resetForm">重置</el-button>
        <el-button type="primary" @click="submitForm">保存设置</el-button>
      </div>
    </el-form>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'

const form = reactive({
  phone: '',
  email: '',
  notifications: false
})

// 重置表单
const resetForm = () => {
  form.phone = ''
  form.email = ''
  form.notifications = false
}

// 表单提交
const submitForm = () => {
  ElMessage.success('账号设置已保存')
}
</script>

<style scoped>
.dialog-footer {
  margin-top: 20px;
  display: flex;
  justify-content: space-around;
}
</style>