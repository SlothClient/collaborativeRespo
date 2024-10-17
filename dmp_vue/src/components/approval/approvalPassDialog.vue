<template>
  <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="40%"
      :before-close="handleClose"
      class="custom-dialog"
  >
    <div class="form-container">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="审批意见" prop="comment">
          <el-input
              type="textarea"
              v-model="form.comment"
              :placeholder="placeholderText"
              :autosize="{ minRows: 4, maxRows: 8 }"
              class="custom-input"
          ></el-input>
        </el-form-item>
      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose" class="cancel-button">取消</el-button>
      <el-button type="primary" @click="submitForm" class="submit-button">提交</el-button>
    </span>
  </el-dialog>
</template>

<script setup>
import {computed, ref, watch} from 'vue';
import {ElMessage, ElNotification} from 'element-plus';

const props = defineProps({
  visible: {
    type: Boolean,
    default: false,
  },
  currentCommand: {
    type: String,
    default: ''
  },
  currentRow:{
    type:Object,
    default:()=>null
  }
});
const emit = defineEmits(['closeDialog', 'onSubmit']);

const dialogVisible = ref(props.visible);
const form = ref({
  comment: '',
});
const formRef = ref(null);

const rules = {
  comment: [
    {required: true, message: '请输入审批意见', trigger: 'blur'},
  ],
};

const dialogTitle = computed(() => props.currentCommand !== 'reject' ? '同意该审批' : '拒绝该审批');
const placeholderText = computed(() => props.currentCommand === 'reject' ? '请输入驳回理由' : '请输入同意理由');

// 关闭对话框
const handleClose = () => {
  emit('closeDialog');
  dialogVisible.value = false;
};

// 提交表单
const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      emit('onSubmit', form.value.comment); // 提交审批意见
      handleClose(); // 关闭对话框
    } else {
      ElNotification({
        message:"您未填写理由",
        type:"error"
      })
    }
  });
};

// 监听父组件传入的 visible 属性变化
watch(() => props.visible, (newVal) => {
  dialogVisible.value = newVal;
  if (!newVal) {
    form.value.comment = ''; // 当对话框关闭时清空输入
  }
});
</script>

<style scoped>
.custom-dialog {
  border-radius: 12px;
  background: linear-gradient(135deg, #f0f0f0, #ffffff);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.form-container {
  padding: 20px;
}

.custom-input .el-textarea__inner {
  background-color: #f9f9f9;
  border-radius: 8px;
  border: 1px solid #dcdfe6;
  padding: 10px;
  transition: all 0.3s ease;
}

.custom-input .el-textarea__inner:focus {
  border-color: #409eff;
  box-shadow: 0 0 8px rgba(64, 158, 255, 0.2);
}

.dialog-footer {
  text-align: right;
  padding: 10px 20px;
  background-color: #ffffff;
  border-top: 1px solid #ffffff;
  border-radius: 0 0 12px 12px;
}

.cancel-button {
  background-color: #f0f0f0;
  color: #606266;
  border: none;
  border-radius: 8px;
  padding: 8px 20px;
  transition: background-color 0.3s;
}

.cancel-button:hover {
  background-color: #e0e0e0;
}

.submit-button {
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 8px 20px;
  transition: background-color 0.3s;
}

.submit-button:hover {
  background-color: #66b1ff;
}
</style>