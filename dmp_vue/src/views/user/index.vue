<template>
<div style="display: flex; flex-direction: column; gap: 16px">
    <div style="display: flex; gap: 16px">
      <el-input
          style="width: 260px; margin-right: 10px;"
          v-model="username"
          placeholder="请输入用户名"
          clearable
      ></el-input>
      <el-input
          style="width: 260px; margin-right: 10px;"
          v-model="roleName"
          placeholder="请输入用户角色"
          clearable
      ></el-input>
      <el-button type="primary" color="#a0cfff" @click="getList(requestParams)">
        <el-icon style="margin-right: 3px;"><Search /></el-icon>搜索
      </el-button>
      <el-button type="success" color="#a0cfff"  @click="clear">
        <el-icon style="margin-right: 3px;"><RefreshRight /></el-icon>重置
      </el-button>
      <el-button type="success" color="rgb(179, 224.5, 156.5)"  @click="handleAdd">
        <el-icon style="margin-right: 3px;"><Plus /></el-icon>新增
      </el-button>
    </div>
    <el-table :data="tableData" style="width: 100%;text-align: center">
        <el-table-column type="selection" width="28"></el-table-column>
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="userpwd" label="密码"  />
      <el-table-column prop="roleName" label="用户角色"  />
      <el-table-column min-width="150" fixed="right" label="操作">
        <template #default="scope">
          <el-popconfirm title="确定删除吗?" @confirm="deleteRow(scope.row)" confirm-button-text="确定" cancel-button-text="取消">
            <template #reference>
              <el-button size="mini" style="width: 50px;" color="rgb(248, 152.1, 152.1)" type="danger"><el-icon><Delete /></el-icon>删除</el-button>
            </template>
          </el-popconfirm>
           <el-button size="mini" style="width: 50px;" color="#a0cfff" type="success" @click="handleEdit(scope.row)"><el-icon><Edit /></el-icon>编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
</div>
 <!-- 分页 -->
 <div style="margin: 10px 0">
      <el-pagination
          :background="true"
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[1, 4, 10, 100]"
          large
          :disabled="false"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>

<!--  弹窗-->
  <el-dialog  v-model="dialogFormVisible" title="用户信息" width="500px" center>
    <div class="dialog-content">
    <!-- 用户名 -->
    <div class="form-item">
      <label class="form-label">用户名：</label>
      <el-input
          v-model="form.username"
          placeholder="请输入用户名"
          size="default"
          class="form-input"
      />
    </div>
    <!-- 密码 -->
    <div class="form-item">
      <label class="form-label">密码：</label>
      <el-input
          v-model="form.userpwd"
          placeholder="请输入密码"
          size="default"
          class="form-input"
      />
    </div>
      <!-- 身份选择 -->
      <div class="form-item">
        <label class="form-label">请选择身份：</label>
        <el-select
            v-model="form.roleName"
            placeholder="选择身份"
            size="default"
            class="form-select"
            filterable
        >
          <el-option
              v-for="item in roleList"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
          />
        </el-select>
      </div>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </div>
    </template>
  </el-dialog>

</template> 
<script setup>
import {ref, onMounted, computed, watch, getCurrentInstance, reactive} from 'vue'
import{getUserInfoList,updateUserInfoList,deleteUserInfo,addUserInfoList} from '@/api/user/index.js'
import {ElMessage, ElNotification} from "element-plus";
import {useUserStore} from "@/store/module/user.js"
const currentPage = ref(1);
const pageSize = ref(4);
const tableData = ref([]);
const username = ref('');
const roleName = ref('');
const total = ref(0);

// 创建一个计算属性来封装所有的请求参数
const requestParams = computed(() => ({
    username: username.value,
    roleName: roleName.value,
  currentPage: currentPage.value,
  pageSize: pageSize.value,
}));
const clear = ()=>{
  currentPage.value=1,
      pageSize.value=4,
      username.value='',
      roleName.value=''
  getList(requestParams.value);
}
// 监听 currentPage 和 pageSize 的变化，并在变化时调用 getList
watch([currentPage, pageSize], () => {
  getList(requestParams.value);
});
const handleSizeChange = (val) => {
  pageSize.value = val;
  getList(requestParams.value);
};

const handleCurrentChange = (val) => {
  currentPage.value = val;
  getList(requestParams.value);
};

const getList = async (params) => {
  const res = await getUserInfoList(params);
  console.log(res);
  tableData.value = res.data.data.records;
  total.value = res.data.data.total;
};
const form=reactive({

})

const dialogFormVisible = ref(false)
const handleAdd = () => {
  dialogFormVisible.value = true;
  // 重置 form 对象
  Object.keys(form).forEach(key => delete form[key]);
};

const handleEdit = (row) => {
  dialogFormVisible.value = true;
  // 将 row 对件转换为 JSON 字符串
  const jsonString = JSON.stringify(row);
  // 解析 JSON 字符串回对象
  const parsedRow = JSON.parse(jsonString);
  // 修改 form 对象的属性
  Object.keys(parsedRow).forEach(key => {
    form[key] = parsedRow[key];
  });
};



const getEdit =async (form)=>{
  const res = await updateUserInfoList(form)
  ElMessage.success("保存成功")
  dialogFormVisible.value=false
  console.log(res)
  await getList(requestParams.value);
}

const getadd = async (form)=>{
  const res = await addUserInfoList(form)
  ElMessage.success("保存成功")
  dialogFormVisible.value=false
  console.log(res)
  await getList(requestParams.value);
};
const {proxy} =getCurrentInstance()
const save = ()=>{
      if(form.username){
        //如果username存在，那就是编辑
        getEdit(form)
      }else{
        getadd(form)
      }
}
const deleteRow = async (form)=>{
  const res= await deleteUserInfo(form)
  console.log(res)
  if(res.data.flag){
    ElNotification({
      message:res.data.data,
      type:"success"
    })
  }
  await getList(requestParams.value);
}

const userStore = useUserStore()
const roleList = ref([])

onMounted(async () => {
  await getList(requestParams.value);
  await userStore.getRoleList();
  roleList.value=userStore.role
});

</script>

<style scoped>
.dialog-content {
  display: flex;
  flex-direction: column;
  gap: 15px; /* 元素之间的间距 */
}

.form-item {
  display: flex;
  align-items: center; /* 垂直居中 */
  gap: 10px; /* label与输入框之间的间距 */
}

.form-label {
  width: 110px; /* label宽度一致 */
  font-weight: bold;
}

.form-select, .form-input, .form-date-picker, .form-textarea {
  width: 100%; /* 保证输入框和选择框占满可用空间 */
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px; /* 按钮之间的间距 */
}
</style>