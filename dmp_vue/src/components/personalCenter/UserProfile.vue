<template>
  <div class="profile-container">
    <div class="left-column">
      <div class="avatar-container" @click="changeAvatar">
        <el-upload
            class="avatar-uploader"
            :show-file-list="false"
            :auto-upload="false"
            :on-change="handleLicensePreview"
            ref="avatarUploader"
        >
          <div class="avatar-container">
            <el-avatar :size="100" :src="displayedAvatar" alt="User Avatar" />
            <span class="avatar-tip">点击更换头像</span>
          </div>
        </el-upload>
      </div>
      <el-button type="success" @click="saveAvatar" :disabled="!avatarChanged">保存</el-button>
      <el-button @click="cancelAvatarChange" :disabled="!avatarChanged">取消</el-button>
    </div>
    <div class="right-column">
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" :disabled="editOrCancel"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" :disabled="editOrCancel"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" :disabled="editOrCancel"></el-input>
        </el-form-item>
        <div class="dialog-footer">
          <el-button @click="toggleEdit">{{ editOrCancel ? '修改' : '取消' }}</el-button>
          <el-button type="primary" @click="submitForm" :disabled="editOrCancel">保存</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {inject, reactive, ref} from 'vue';
import {ElMessage, ElNotification} from 'element-plus';
import { useUserStore } from '@/store/module/user.js';
import {getUserInfo, updateAvatar} from "@/api/user/index.js";

const userStore = useUserStore();

// 原始表单数据
const originalForm = reactive({
  username: userStore.user.username,
  email: '66666@qq.com',
  phone: '13333333333',
  avatar: userStore.user.avatar // 示例头像链接
});

// 当前表单数据
const form = reactive({ ...originalForm });

// 编辑状态
const editOrCancel = ref(true);

// 保存的表单数据
let savedForm = null;

// 标记是否选择了新头像
const avatarChanged = ref(false);

// 本地预览的头像
const displayedAvatar = ref(originalForm.avatar);

// 临时选择的头像文件
let selectedAvatarFile = null;


const handleLicensePreview = (file) => {
  const isJPG = file.raw.type === 'image/jpeg' || file.raw.type === 'image/png';
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isJPG) {
    ElMessage.error('上传头像图片只能是 JPG 或 PNG 格式!');
    return;
  }
  if (!isLt2M) {
    ElMessage.error('上传头像图片大小不能超过 2MB!');
    return;
  }

  selectedAvatarFile = file.raw;
  displayedAvatar.value = URL.createObjectURL(file.raw);
  avatarChanged.value = true;
};

const toggleEdit = () => {
  if (editOrCancel.value) {
    // 进入编辑模式
    editOrCancel.value = false;
    savedForm = { ...form }; // 保存当前表单数据
  } else {
    // 取消编辑，恢复原始值
    Object.keys(form).forEach((key) => {
      form[key] = savedForm[key];
    });
    displayedAvatar.value = originalForm.avatar; // 恢复为原始头像
    avatarChanged.value = false; // 重置头像变更状态
    editOrCancel.value = true;
  }
};

const changeAvatar = () => {
  if (editOrCancel.value) {
    ElMessage.info('点击修改信息后可更换头像');
  }
};

const saveAvatar = async () => {
  if (avatarChanged.value && selectedAvatarFile) {
    try {
      // 手动上传头像
      await uploadAvatar();
      ElMessage.success('头像已保存');
      await getUserInfo()
      avatarChanged.value = false; // 保存后重置头像变更状态
    } catch (error) {
      ElMessage.error('保存头像失败，请重试');
    }
  }
};

const submitForm = async () => {
  if (!editOrCancel.value) {
    try {
      // 提交表单数据
      // await userStore.updateUserInfo({ ...form });
      ElMessage.success('个人资料已保存');
      editOrCancel.value = true;
    } catch (error) {
      ElMessage.error('保存失败，请重试');
    }
  }
};

const uploadAvatar = async () => {

  const avatar = new FormData();
  avatar.append("avatar",selectedAvatarFile)

  const res = await updateAvatar(avatar)
  if(res.data.flag){
    ElNotification({
      message:res.data.data,
      type:"success"
    })
  }
  else {
    ElNotification({
      message:res.data.data,
      type:"error"
    })
  }
};

const cancelAvatarChange = () => {
  displayedAvatar.value = originalForm.avatar; // 恢复为原始头像
  avatarChanged.value = false; // 重置头像变更状态
};
</script>

<style scoped>
.profile-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.left-column {
  width: 200px;
  text-align: center;
  margin-right: 20px;
}

.avatar-container {
  position: relative;
  cursor: pointer;
  margin-bottom: 10%;
}

.avatar-container .el-avatar {
  width: 100px !important;
  height: 100px !important;
  border-radius: 50%;
  object-fit: cover;
}

.avatar-tip {
  position: absolute;
  bottom: -10px;
  left: 50%;
  transform: translateX(-50%);
  font-size: 12px;
  width:100%;
  color: #999;
}

.right-column {
  flex-grow: 1;
}

.dialog-footer {
  margin-top: 20px;
  display: flex;
  justify-content: space-around;
}
</style>