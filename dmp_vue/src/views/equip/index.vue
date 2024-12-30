<template>
  <div style="display: flex; flex-direction: column; gap: 16px">
    <div class="search-bar">
      <el-input
          class="search-item"
          v-model="equipName"
          placeholder="请输入设备名称"
          clearable
      ></el-input>
      <el-input
          class="search-item"
          v-model="departmentName"
          placeholder="请输入设备部门"
          clearable
      ></el-input>
      <el-input
          class="search-item"
          v-model="typeName"
          placeholder="请输入设备类型"
          clearable
      ></el-input>
      <el-button type="primary" color="rgb(159.5, 206.5, 255)" @click="getList(requestParams)">
        <el-icon style="margin-right: 3px;"><Search /></el-icon>搜索
      </el-button>
      <el-button type="success" color="rgb(242.5, 208.5, 157.5)"  @click="clear">
        <el-icon style="margin-right: 3px;"><RefreshRight /></el-icon>重置
      </el-button>
      <el-button type="success" color="rgb(179, 224.5, 156.5)"  @click="handleAdd">
        <el-icon style="margin-right: 3px;"><Plus /></el-icon>新增
      </el-button>

    </div>
    <!-- 表格 -->
    <equipTable :tableData="tableData" @edit="handleEdit" @delete="deleteRow" />
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
    <!--  新增对话框-->
    <el-dialog v-model="dialogFormVisible" title="用户信息" width="50%">
      <el-form :model="form" ref="ruleFormRef" :rules="rules" >
        <el-row class="row-bg" justify="space-evenly" style="margin-top: 15px;">
          <el-col :span="7"><div class="grid-content ep-bg-purple" >
            <el-form-item label="设备名称" prop="equipName" >
              <el-input v-model="form.equipName" autocomplete="off" placeholder="请输入设备名称" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
          </el-col>
          <el-col :span="7"><div class="grid-content ep-bg-purple-light" >
            <el-form-item prop="statusName" label="设备状态" style="display: flex">
              <el-select v-model="form.statusName" placeholder="请选择状态" size="default" style="width: 132px">
                <el-option
                    v-for="item in equipStatusName"
                    :key="item.statusId"
                    :label="item.statusName"
                    :value="item.statusName"
                />
              </el-select>
            </el-form-item>
          </div>
          </el-col>
          <el-col :span="7"><div class="grid-content ep-bg-purple" >
            <el-form-item prop="remarks" label="设备备注">
              <el-input v-model="form.remarks" autocomplete="off" placeholder="请输入备注" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
          </el-col>
        </el-row>

        <el-row class="row-bg" justify="space-evenly" style="margin-top: 15px;">
          <el-col :span="7"><div class="grid-content ep-bg-purple-light" >
            <el-form-item prop="countBig" label="大修次数">
              <el-input v-model="form.countBig" autocomplete="off" placeholder="请输入大修次数" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
          </el-col>
          <el-col :span="7"><div class="grid-content ep-bg-purple" >
            <el-form-item prop="countMedium" label="中修次数">
              <el-input v-model="form.countMedium" autocomplete="off" placeholder="请输入中修次数" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
          </el-col>
          <el-col :span="7"><div class="grid-content ep-bg-purple" >
            <el-form-item prop="countSmall" label="小修次数">
              <el-input v-model="form.countSmall" autocomplete="off" placeholder="请输入小修次数" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
          </el-col>
        </el-row>
        <el-row class="row-bg" justify="space-evenly"style="margin-top: 15px;">
          <el-col :span="7"><div class="grid-content ep-bg-purple-light" >
            <el-form-item prop="fixBig" label="大修周期">
              <el-input v-model="form.fixBig" autocomplete="off" placeholder="请输入大修周期" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
          </el-col>
          <el-col :span="7"><div class="grid-content ep-bg-purple" >
            <el-form-item prop="fixMedium" label="中修周期">
              <el-input v-model="form.fixMedium" autocomplete="off" placeholder="请输入中修周期" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
          </el-col>
          <el-col :span="7"><div class="grid-content ep-bg-purple" >
            <el-form-item prop="fixSmall" label="小修周期">
              <el-input v-model="form.fixSmall" autocomplete="off" placeholder="请输入小修周期" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
          </el-col>
        </el-row>
        <el-row class="row-bg" justify="space-evenly" style="margin-top: 15px;">
          <el-col :span="7"><div class="grid-content ep-bg-purple-light" >
            <el-form-item prop="validTime" label="有效期限">
              <el-input v-model="form.validTime" autocomplete="off" placeholder="请输入有效期限" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
          </el-col>
          <el-col :span="7"><div class="grid-content ep-bg-purple" >
            <el-form-item prop="purchaseDate" label="购置日期">
              <el-date-picker
                  v-model="form.purchaseDate"
                  type="date"
                  placeholder="请输入购置日期"
                  style="width: 150px;display: flex;margin-right: 10px;"
              />
            </el-form-item>
          </div>
          </el-col>
          <el-col :span="7"><div class="grid-content ep-bg-purple" >
            <el-form-item prop="lastMaintance" label="保养末期">
              <el-date-picker
                  v-model="form.lastMaintance"
                  type="date"
                  placeholder="请输入最后保养日期"
                  style="width: 150px;display: flex;margin-right: 10px;"
              />
            </el-form-item>
          </div>
          </el-col>
        </el-row>
        <el-row class="row-bg" justify="space-evenly" style="margin-top: 15px;">
          <el-col :span="7"><div class="grid-content ep-bg-purple-light" >
            <el-form-item prop="departmentName" label="设备部门"style="display: flex">
              <el-select v-model="form.departmentName" placeholder="请选择部门" size="default" style="width: 132px">
                <el-option
                    v-for="item in departmentList"
                    :key="item.departmentId"
                    :label="item.departmentName"
                    :value="item.departmentName"
                />
              </el-select>
            </el-form-item>
          </div>
          </el-col>
          <el-col :span="7"><div class="grid-content ep-bg-purple-light" >
            <el-form-item prop="typeName" label="设备状态"style="display: flex">
              <el-select v-model="form.typeName" placeholder="请选择类型" size="default" style="width: 132px">
                <el-option
                    v-for="item in typeList"
                    :key="item.typeId"
                    :label="item.typeName"
                    :value="item.typeName"
                />
              </el-select>
            </el-form-item>
          </div>
          </el-col>
          <el-col :span="7"><div class="grid-content ep-bg-purple-light" >
            <el-form-item prop="siteName" label="设备位置"style="display: flex">
              <el-select v-model="form.siteName" placeholder="请选择位置" size="default" style="width: 132px">
                <el-option
                    v-for="item in siteList"
                    :key="item.siteId"
                    :label="item.siteName"
                    :value="item.siteName"
                />
              </el-select>
            </el-form-item>
          </div>
          </el-col>
        </el-row>
        <el-row class="row-bg" style="margin-left: 25px;margin-top: 15px;">
          <el-col :span="7">
            <div class="grid-content ep-bg-purple">
              <el-form-item label="设备照片" prop="equipPic" >
                <el-upload
                    action="http://localhost:5173/api/file/upload"
                    :on-success="handleUploadSuccess"
                    :show-file-list="false"
                    class="el-upload"
                >
                  <img v-if="form.equipPic" :src="form.equipPic"  style=" width: 178px;height: 178px;display: block;" />
                  <el-icon v-else class="avatar-uploader-icon"><Plus /><span style="font-size: 7pt;">上传</span></el-icon>
                </el-upload>
              </el-form-item>
            </div>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="resetDialogForm">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, reactive, watch, computed, onMounted, getCurrentInstance} from 'vue';
import {getEquipInfoList,addEquipInfo,updateEquipInfo,deleteEquipInfo} from "@/api/equip/index.js";
import equipTable from "@/components/equip/equipTable.vue";
import {ElMessage, ElNotification} from "element-plus";
import {useEquipmentInfoStore} from "@/store/module/equipmentInfo.js";

import axios from "axios";

//表单校验
const rules = reactive({
  equipName:[
    {required: true, message: '请输入设备名称', trigger: 'blur'}
  ],
  statusName:[
    {required: true, message: '请选择设备状态', trigger: 'blur'}
  ],
  departmentName:[
    {required: true, message: '请选择部门', trigger: 'blur'}
  ],
  typeName:[
    {required: true, message: '请选择类型', trigger: 'blur'}
  ],
  siteName:[
    {required: true, message: '请选择位置', trigger: 'blur'}
  ],
  purchaseDate:[
    {required: true, message: '请输入购置日期', trigger: 'blur'}
  ],
  lastMaintance:[
    {required: true, message: '请输入最后保养日期', trigger: 'blur'}
  ]
})

const currentPage = ref(1);
const pageSize = ref(4);
const tableData = ref([]);
const equipName = ref('');
const departmentName = ref('');
const typeName = ref('');

const total = ref(0);

// 创建一个计算属性来封装所有的请求参数
const requestParams = computed(() => ({
  equipName: equipName.value,
  departmentName: departmentName.value,
  typeName: typeName.value,
  currentPage: currentPage.value,
  pageSize: pageSize.value,
}));
const clear = ()=>{
  currentPage.value=1,
      pageSize.value=4,
      equipName.value='',
      departmentName.value='',
      typeName.value=''
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
  const res = await getEquipInfoList(params);
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
//保存图片内存中的临时地址
const imageUrl = ref('')

//图片上传成功前
const beforeUpload = (file) => {
  const isImage = file.type.startsWith('image/');
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isImage) {
    ElMessage.error('上传图片只能是 JPG/PNG 格式!');
  }
  if (!isLt2M) {
    ElMessage.error('上传图片大小不能超过 2MB!');
  }
  return isImage && isLt2M;
};
//图片上传成功后
const handleUploadSuccess = (response) => {
  console.log(response)
  form.equipPic = response.data
};
const {proxy} =getCurrentInstance()
const getEdit =async (form)=>{
  const res = await updateEquipInfo(form)
  ElMessage.success("保存成功")
  dialogFormVisible.value=false
  console.log(res)
  await getList(requestParams.value);
}
const getadd = async (form)=>{
  const res = await addEquipInfo(form)
  ElMessage.success("保存成功")
  dialogFormVisible.value=false
  console.log(res)
  await getList(requestParams.value);
}
const ruleFormRef = ref(null)
// 重置对话框表单
const resetDialogForm = () => {
  ruleFormRef.value.resetFields(); // 重置表单字段和验证状态
  dialogFormVisible.value = false; // 关闭对话框
  // window.location.reload(); // 刷新页面
};

const save = ()=>{
  proxy.$refs.ruleFormRef.validate((valid)=>{
    if(valid){
      if(form.equipId){
        //如果id存在，那就是编辑
        getEdit(form)
      }else{
        if(form == null){
          ElMessage.error("不能全为空")
        }else{
          getadd(form)
          ElMessage.success("新增成功")
        }

      }
    }
  })
}
const deleteRow = async (equipId)=>{
  console.log('equipId:', equipId);
  const res= await deleteEquipInfo(equipId)
  console.log(res)
  if(res.data.flag){
    ElNotification({
      message:res.data.data,
      type:"success"
    })
  }
  await getList(requestParams.value);
}
const equipStatusName=ref([])
const equipmentStore = useEquipmentInfoStore()

const departmentList = ref([])
const typeList = ref([])
const siteList = ref([])

onMounted(async () => {
  await getList(requestParams.value);
  await equipmentStore.getEquipStatusNameList();
  await equipmentStore.getDepartmentList();
  await equipmentStore.getTypeList();
  await equipmentStore.getSiteList();
  equipStatusName.value=equipmentStore.equipStatusName
  departmentList.value=equipmentStore.department
  typeList.value=equipmentStore.type
  siteList.value=equipmentStore.site
  console.log(equipStatusName)
});

</script>
<style scoped>
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.el-form-item {
  margin-right: 10px;
}
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.el-upload {
  border: 1px dashed var(--el-border-color);

}
.el-upload:hover{
  border-color: var(--el-color-primary);
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
.search-bar {
  display: flex;
  gap: 16px;
  align-items: center;
}
.search-item{
  width: 260px;
  margin-right: 10px;
}
</style>
