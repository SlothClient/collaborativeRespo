<template>
  <div class="user-management">
    <div class="search-bar">
      <el-input v-model="username" placeholder="请输入用户名" clearable/>
      <el-input v-model="roleName" placeholder="请输入用户角色" clearable/>
      <el-button type="primary" @click="getList">
        <el-icon>
          <Search/>
        </el-icon>
        搜索
      </el-button>
      <el-button type="success" @click="resetSearch">
        <el-icon>
          <RefreshRight/>
        </el-icon>
        重置
      </el-button>
      <el-button type="success" @click="openDialog">
        <el-icon>
          <Plus/>
        </el-icon>
        新增
      </el-button>
    </div>

    <el-table :data="tableData" style="width: 100%;">
      <el-table-column type="selection" width="28"/>
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="userpwd" label="密码"/>
      <el-table-column prop="roleName" label="用户角色"/>
      <el-table-column label="操作" fixed="right" width="150">
        <template #default="scope">
          <el-popconfirm title="确定删除吗?" @confirm="confirmDelete(scope.row)" confirm-button-text="确定" cancel-button-text="取消">

            <template #reference>
              <el-button type="danger" size="small">
                <el-icon>
                  <Delete />
                </el-icon>
                删除
              </el-button>
            </template>
          </el-popconfirm>

          <!-- 编辑按钮 -->
          <el-button type="success" size="small" @click="editUser(scope.row)">
            <el-icon>
              <Edit />
            </el-icon>
            编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[1, 4, 10, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />

    <el-dialog v-model="dialogFormVisible" title="用户信息" width="500px" center>
      <div class="form-content">
        <el-input v-model="form.username" placeholder="请输入用户名"/>
        <el-input v-model="form.userpwd" placeholder="请输入密码"/>
        <el-select v-model="form.roleId" placeholder="请选择身份" filterable>
          <el-option
              v-for="item in roleList"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
          />
        </el-select>
      </div>
      <template #footer>
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUser">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, onMounted, reactive, computed, watch, toRef, toRaw} from 'vue';
import {getUserInfoList, addUserInfoList, updateUserInfoList, deleteUserInfo} from '@/api/user/index.js';
import {ElMessage, ElNotification} from 'element-plus';
import {useUserStore} from '@/store/module/user.js';

// 引入状态管理
const userStore = useUserStore();
const roleList = ref([]);

const currentPage = ref(1);
const pageSize = ref(4);
const total = ref(0);

const username = ref('');
const roleName = ref('');
const tableData = ref([]);

const form = reactive(
    {
      username: '',
      userpwd: '',
      roleId: '',
      userId: '',
    });
const dialogFormVisible = ref(false);


const CAMMAND = ref('')

// 计算请求参数
const requestParams = computed(() => ({
  username: username.value,
  roleName: roleName.value,
  currentPage: currentPage.value,
  pageSize: pageSize.value,
}));

// 获取用户列表
const getList = async () => {
  try {
    const res = await getUserInfoList(requestParams.value);
    tableData.value = res.data.data.records;
    total.value = res.data.data.total;
  } catch (error) {
    console.error('获取用户列表失败:', error);
  }
};

// 表单重置逻辑
const resetSearch = () => {
  username.value = '';
  roleName.value = '';
  currentPage.value = 1;
  getList();
};

// 处理分页变化
watch([currentPage, pageSize], getList);

const handleSizeChange = (size) => (pageSize.value = size);
const handleCurrentChange = (page) => (currentPage.value = page);

// 打开新增/编辑弹窗
const openDialog = (user = {}, command) => {
  if (command === "edit") {
    form.userId = user.id
    form.username = user.username
    form.userpwd = user.userpwd
    form.roleId = roleList.value.find(role => role.roleName === user.roleName).roleId
    console.log(form)
    CAMMAND.value = "edit"
  } else {
    form.roleId = '';
    form.username = '';
    form.userpwd = ''
    CAMMAND.value = "add"
  }
  dialogFormVisible.value = true;
  // Object.assign(form, user);
};

// 保存用户
const saveUser = async () => {
  try {
    const param = toRaw(form)
    if (CAMMAND.value === "edit") {
      console.log()
      console.log(param)
      await updateUserInfoList(param);
      ElMessage.success('编辑成功');
    } else {
      console.log(param)
      await addUserInfoList(param);
      ElMessage.success('新增成功');
    }
    dialogFormVisible.value = false;
    await getList();
  } catch (error) {
    ElMessage.error('保存失败');
  }
};

// 确认删除用户
const confirmDelete = async (user) => {
  try {
    const param = {
      userId: user.id
    }
    await deleteUserInfo(param);
    ElNotification.success('删除成功');
    await getList();
  } catch (error) {
    ElNotification.error('删除失败');
  }
};

const editUser = (row) => {
  openDialog(row, 'edit')
  console.log(row)
}

// 初始化数据
onMounted(async () => {
  await getList();
  await userStore.getRoleList();
  roleList.value = userStore.role;
});
</script>

<style scoped>
.user-management {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.search-bar {
  display: flex;
  gap: 16px;
  align-items: center;
}

.form-content {
  display: flex;
  flex-direction: column;
  gap: 15px;
}
</style>