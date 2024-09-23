<template>
    <div style="display: flex;flex-direction: column;gap: 16px">
    <div style="display: flex;gap: 16px">
      <el-input style="width: 260px;margin-right: 10px;" v-model="deviceName" placeholder="请输入设备名称" clearable></el-input>
      <el-input style="width: 260px;margin-right: 10px;" v-model="typeName" placeholder="请输入设备类型" clearable></el-input>
      <el-button type="primary" color="#a0cfff" @click="load"><el-icon style="margin-right: 3px;"><Search /></el-icon>搜索</el-button>
      <el-button type="success" @click="handleAdd"><el-icon style="margin-right: 3px;"><Plus /></el-icon>新增</el-button>

    </div>
  <el-table :data="state.tableData" style="width: 100%;text-align: center">
    <el-table-column prop="id" label="ID"/>
    <el-table-column prop="deviceName" label="设备名称" />
    <el-table-column prop="status" label="状态"  />
    <el-table-column prop="supplierId" label="供应商ID"  />
    <el-table-column prop="supplierName" label="供应商"  />
    <el-table-column prop="departmentId" label="部门ID"  />
    <el-table-column prop="departmentName" label="部门"  />
    <el-table-column prop="typeId" label="类型ID"  />
    <el-table-column prop="typeName" label="类型"  />
    <el-table-column prop="position" label="位置"  />
    <el-table-column prop="photo" label="照片"  />
    <el-table-column prop="notes" label="备注"  />
    <el-table-column prop="bigRepairsNumber" label="大修次数"  />
    <el-table-column prop="middleRepairsNumber" label="中修次数"  />
    <el-table-column prop="smallRepairsNumber" label="小修次数"  />
    <el-table-column prop="bigRepairsCycle" label="大修周期"  />
    <el-table-column prop="middleRepairsCycle" label="中修周期"  />
    <el-table-column prop="smallRepairsCycle" label="小修周期"  />
    <el-table-column prop="lastMaintenanceTime" label="最后保养日期"  />
    <el-table-column prop="purchasingDate" label="购置日期"  />
    <el-table-column prop="validPeriod" label="有效期限"  />

    <el-table-column min-width="150" fixed="right" label="操作" >
      <template #default="scope">
        <el-popconfirm title="确定删除吗?" @confirm="deleteRow(scope.row.id)"  confirm-button-text="确定" cancel-button-text="取消">
          <template #reference>
            <el-button size="mini" style="width: 50px;" type="danger"><el-icon style="margin-right: 3px;"><Delete /></el-icon>删除</el-button>
          </template>
        </el-popconfirm>
        <el-button style="width: 50px;" type="success"@click="handleEdit(scope.row)"><el-icon style="margin-right: 3px;"><Edit /></el-icon>编辑</el-button>
      </template>
    </el-table-column>
  </el-table>
      <!--  新增对话框-->
      <el-dialog v-model="dialogFormVisible" title="用户信息" width="50%">
        <el-form :model="state.form" ref="ruleFormRef" :rules="state.rules" style="width:95%;margin-right: 10px;">
          <div class="form-group">
          <el-form-item label="设备名称" prop="deviceName" >
            <el-input v-model="state.form.deviceName" autocomplete="off" placeholder="请输入设备名称" style="width: 150px;display: flex;margin-right: 10px;"/>
          </el-form-item>
          <el-form-item prop="status" label="状态"style="display: flex">
            <el-input v-model="state.form.status" autocomplete="off" placeholder="请输入状态" style="width: 120px;display: flex;margin-right: 10px;"/>
          </el-form-item>
          <el-form-item prop="supplierId" label="供应商ID">
            <el-input v-model="state.form.supplierId" autocomplete="off" placeholder="请输入供应商ID" style="width: 150px;display: flex;margin-right: 10px;"/>
          </el-form-item>
          </div>

          <div class="form-group">
            <el-form-item prop="departmentId" label="部门ID">
              <el-input v-model="state.form.departmentId" autocomplete="off" placeholder="请输入部门ID" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
            <el-form-item lprop="typeId" label="类型ID">
              <el-input v-model="state.form.typeId" autocomplete="off" placeholder="请输入类型ID"style="width: 150px;display: flex;margin-right: 10px;" />
            </el-form-item>
            <el-form-item prop="position" label="位置">
              <el-input type="textarea" v-model="state.form.position" autocomplete="off" placeholder="请输入位置" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
        <div class="form-group">
          <el-form-item prop="photo" label="照片">
            <el-input v-model="state.form.photo" autocomplete="off" placeholder="请输入照片" style="width: 150px;display: flex;margin-right: 10px;"/>
          </el-form-item>
          <el-form-item prop="notes" label="备注">
            <el-input v-model="state.form.notes" autocomplete="off" placeholder="请输入备注" style="width: 150px;display: flex;margin-right: 10px;"/>
          </el-form-item>
          <el-form-item prop="bigRepairsNumber" label="大修次数">
            <el-input v-model="state.form.bigRepairsNumber" autocomplete="off" placeholder="请输入大修次数" style="width: 150px;display: flex;margin-right: 10px;"/>
          </el-form-item>
        </div>
          <div class="form-group">
            <el-form-item prop="middleRepairsNumber" label="中修次数">
              <el-input v-model="state.form.middleRepairsNumber" autocomplete="off" placeholder="请输入中修次数" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
            <el-form-item prop="smallRepairsNumber" label="小修次数">
              <el-input v-model="state.form.smallRepairsNumber" autocomplete="off" placeholder="请输入小修次数" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
            <el-form-item prop="bigRepairsCycle" label="大修周期">
              <el-input v-model="state.form.bigRepairsCycle" autocomplete="off" placeholder="请输入大修周期" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
          <div class="form-group">
            <el-form-item prop="middleRepairsCycle" label="中修周期">
              <el-input v-model="state.form.middleRepairsCycle" autocomplete="off" placeholder="请输入中修周期" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
            <el-form-item prop="smallRepairsCycle" label="小修周期">
              <el-input v-model="state.form.smallRepairsCycle" autocomplete="off" placeholder="请输入小修周期" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
            <el-form-item prop="lastMaintenanceTime" label="最后保养日期">
              <el-input v-model="state.form.lastMaintenanceTime" autocomplete="off" placeholder="请输入最后保养日期" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
          <div class="form-group">
            <el-form-item prop="purchasingDate" label="购置日期">
              <el-input v-model="state.form.purchasingDate" autocomplete="off" placeholder="请输入购置日期" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
            <el-form-item prop="validPeriod" label="有效期限">
              <el-input v-model="state.form.validPeriod" autocomplete="off" placeholder="请输入有效期限" style="width: 150px;display: flex;margin-right: 10px;"/>
            </el-form-item>
          </div>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click="save">保存</el-button>
          </div>
        </template>
      </el-dialog>
      <!--  分页-->
      <div style="margin: 10px 0">
        <el-pagination
            :background="true"
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[1, 10, 20, 100]"
            large
            :disabled="false"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
      </div>
  <!--    详情弹窗-->
  <el-dialog v-model="addVisible" title="设备详情" width="500">
      
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addVisible = false">取消</el-button>
          <el-button type="primary" >
            Confirm
          </el-button>
        </div>
      </template>
    </el-dialog>
</div>
</template>

<!--<script lang="ts" setup>-->
<!--import {Delete, Edit} from "@element-plus/icons-vue";-->
<!--import {getCurrentInstance, reactive, ref} from 'vue'-->
<!--import request from "../request"-->
<!--import {ElMessage} from "element-plus";-->
<!--const {proxy} =getCurrentInstance()-->
<!--const addVisible = ref(false)-->
<!--// 打开新增面板-->
<!--const state=reactive({-->
<!--  tableData:[],-->
<!--  form:{},-->
<!--})-->

<!--const deviceName=ref()-->
<!--const typeName=ref()-->
<!--const currentPage=ref(1)-->
<!--const pageSize=ref(10)-->
<!--const total=ref(0)-->
<!--const load =()=>{-->
<!--  request.get("/device/findBySearch",{-->
<!--    params:{-->
<!--      currentPage:currentPage.value,-->
<!--      pageSize:pageSize.value,-->
<!--      deviceName:deviceName.value,-->
<!--      typeName:typeName.value-->
<!--    }-->
<!--  }).then(res =>{-->
<!--      state.tableData = res.data.list-->
<!--      total.value=res.data.total-->

<!--  })-->
<!--}-->
<!--load()-->

<!--const handleSizeChange =(val)=>{-->
<!--  pageSize.value=val-->
<!--  console.log(pageSize)-->
<!--  load()-->

<!--}-->
<!--const handleCurrentChange =(val)=>{-->
<!--  currentPage.value=val-->
<!--  console.log(currentPage)-->
<!--  load()-->

<!--}-->
<!--const handleEdit=(row)=>{-->
<!--  dialogFormVisible.value=true-->
<!--  //实现了数据的转让-->
<!--  state.form=JSON.parse(JSON.stringify(row))-->
<!--}-->
<!--const dialogFormVisible = ref(false)-->
<!--const handleAdd=()=>{-->
<!--  dialogFormVisible.value=true-->
<!--  state.form={}-->
<!--}-->
<!--const save = ()=>{-->
<!--  proxy.$refs.ruleFormRef.validate((valid)=>{-->
<!--    if(valid){-->
<!--      state.form.password='123'-->
<!--      if(state.form.id){//如果id存在，那就是编辑-->
<!--        request.post("/device/update",state.form).then(res=>{-->
<!--            ElMessage.success("保存成功")-->
<!--            dialogFormVisible.value=false-->
<!--            load()-->
<!--        })-->

<!--      }else{//新增-->
<!--          request.post("/device/add",state.form).then(res=>{-->
<!--            ElMessage.success("保存成功")-->
<!--            dialogFormVisible.value=false-->
<!--            load()-->
<!--          })-->

<!--      }-->

<!--    }-->

<!--  })-->
<!--}-->
<!--const deleteRow = (id) => {-->
<!--  console.log("Deleting row with id:", id);-->
<!--  request.get("/device/delete", { params: { id } })-->
<!--      .then(res => {-->
<!--        if (res.code === '200') {-->
<!--          ElMessage.success("删除成功");-->
<!--        } else {-->
<!--          ElMessage.error("删除失败");-->
<!--        }-->
<!--        load();-->
<!--      })-->
<!--      .catch(error => {-->
<!--        console.error("删除操作失败:", error.response ? error.response.data : error.message);-->
<!--        if (error.response) {-->
<!--          console.error("Response data:", error.response.data);-->
<!--          console.error("Response status:", error.response.status);-->
<!--          console.error("Response headers:", error.response.headers);-->
<!--        }-->
<!--        ElMessage.error("删除失败，请稍后再试");-->
<!--      });-->
<!--};-->
<!--</script>-->
<style scoped>
.form-group {
  display: flex;
  justify-content: space-between;
}

</style>