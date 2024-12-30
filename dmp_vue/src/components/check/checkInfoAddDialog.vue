<template>
  <el-dialog v-model="dialogVisible" :before-close="closeDialog" title="添加计划" width="500px" center>
    <div class="dialog-content">
      <!-- 设备选择 -->
      <div class="form-item">
        <label class="form-label">请选择设备：</label>
        <el-select
            v-model="addPlan.equipId"
            placeholder="选择设备"
            size="default"
            class="form-select"
            filterable
        >
          <el-option
              v-for="item in props.equipmentInfoList"
              :key="item.equipId"
              :label="item.equipName"
              :value="item.equipId"
          />
        </el-select>
      </div>

      <!-- 计划名称 -->
      <div class="form-item">
        <label class="form-label">计划名称：</label>
        <el-input
            v-model="addPlan.checkName"
            placeholder="请输入计划名称"
            size="default"
            class="form-input"
        />
      </div>

      <!-- 计划时间 -->
      <div class="form-item">
        <label class="form-label">计划时间：</label>
        <el-date-picker
            v-model="planDate"
            type="datetimerange"
            start-placeholder="计划开始时间"
            end-placeholder="计划结束时间"
            format="YYYY/MM/DD HH:mm:ss"
            value-format="YYYY/MM/DD HH:mm:ss"
            class="form-date-picker"
        />
      </div>

      <!-- 点巡检位置 -->
      <div class="form-item">
        <label class="form-label">点巡检位置：</label>
        <el-input
            v-model="addPlan.checkSite"
            type="textarea"
            placeholder="请输入点巡检位置"
            :autosize="{ minRows: 4, maxRows: 8 }"
            class="form-textarea"
        />
      </div>
      <!-- 点巡检内容 -->
      <div class="form-item">
        <label class="form-label">点巡检内容：</label>
        <el-input
            v-model="addPlan.checkContent"
            type="textarea"
            placeholder="请输入点巡检内容"
            :autosize="{ minRows: 4, maxRows: 8 }"
            class="form-textarea"
        />
      </div>
      <!-- 点巡检描述 -->
      <div class="form-item">
        <label class="form-label">点巡检描述：</label>
        <el-input
            v-model="addPlan.checkDesc"
            type="textarea"
            placeholder="请输入点巡检描述"
            :autosize="{ minRows: 4, maxRows: 8 }"
            class="form-textarea"
        />
      </div>

      <div id="equipInfo">
      <div class="formTitle">点巡检项目明细</div>
      <div id="equipTable">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column label="检查项名称" align="center">
            <template #default="scope">
              <el-input v-model="scope.row.checkitemName" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="检查项方法" align="center">
            <template #default="scope">
              <el-input v-model="scope.row.checkitemWay" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-popconfirm title="确定删除吗?" @confirm="deleteRow(scope.$index)" confirm-button-text="确定" cancel-button-text="取消">
                <template #reference>
                  <el-button link size="mini" color="rgb(159.5, 206.5, 255)" style="width: 50px;" type="danger"><el-icon><Delete /></el-icon>删除</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <!-- 添加检查项按钮 -->
        <el-button @click="addCheckItem">添加检查项</el-button>
      </div>
    </div>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="confirm">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, watch } from "vue";
import moment from 'moment';
import { ElNotification } from "element-plus";
import { Delete } from "@element-plus/icons-vue";
import { getEquipItem } from "@/api/checkitemEquip/index.js";
import { createCheckitem } from "@/api/checkitem/index.js";

const props = defineProps({
  checkInfoAddVisible: {
    type: Boolean,
    default: false,
  },
  equipmentInfoList: {
    type: Array,
    default: () => [],
  },
});

const dialogVisible = ref(props.checkInfoAddVisible);
const emit = defineEmits(['closeDialog', 'addCheckInfo']);

const planDate = ref([]);
const tableData = ref([]);

const addPlan = ref({
  equipId: '',
  checkName: '',
  checkDesc: '',
  checkSite: '',
  checkContent: '',
  startTime: null,
  endTime: null,
  createTime: undefined,
  checkId: null // 假设这是点巡检计划的ID
});

// 生成唯一的checkitemId
function generateCheckitemId() {
  return Date.now() + Math.random();
}

// 监听 addPlan.equipId 的变化
watch(() => addPlan.value.equipId, async (newValue) => {
  if (newValue) { // 如果 newValue 不为空，则调用 getEquipItem
    const res = await getEquipItem(newValue);
    console.log(res);
    if (res && res.data && res.data.data) {
      // 为每个检查项添加 checkitemId
      res.data.data.forEach(item => {
        item.checkitemId = generateCheckitemId(); // 使用生成的唯一ID
      });
      tableData.value = res.data.data;
    }
  }
});

const clear = () => {
  addPlan.value = {
    equipId: '',
    checkName: '',
    checkDesc: '',
    checkSite: '',
    checkContent: '',
    startTime: null,
    endTime: null,
    createTime: null,
    checkId: null,
  };
  planDate.value = [];
  tableData.value = [];
};

const closeDialog = () => {
  clear();
  dialogVisible.value = false;
  emit('closeDialog');
};

// 获取当前时间的方法
const getNowFormatDateTime = () => {
  return moment(new Date()).format('YYYY/MM/DD HH:mm:ss');
};

const confirm = async () => {
  const now = getNowFormatDateTime(); // 获取当前时间

  addPlan.value.startTime = planDate.value[0];
  addPlan.value.endTime = planDate.value[1];
  addPlan.value.createTime = now;
  addPlan.value.checkId = generateCheckitemId(); // 设置 checkId 为当前时间戳

  // 空值校验逻辑
  const isEmpty = Object.values(addPlan.value).some(value => {
    if (typeof value === 'string') {
      return value.trim() === '';
    }
    return value === null || value === undefined;
  });
  if (isEmpty) {
    ElNotification({
      message: "不能存在空值,请重试",
      type: 'error'
    });
    return;
  }

  // 校验检查项信息
  const checkItemsValid = tableData.value.every(item => item.checkitemName && item.checkitemWay);
  if (!checkItemsValid) {
    ElNotification({
      message: "所有检查项必须填写名称和方法",
      type: 'error'
    });
    return;
  }

  // 只传递所需的字段
  const cleanedData = tableData.value.map(item => ({
    checkitemName: item.checkitemName,
    checkitemWay: item.checkitemWay,
    checkId: addPlan.value.checkId, // 确保使用相同的checkId
    checkitemId: item.checkitemId,
    checkitemTime: now
  }));

  emit("addCheckInfo", addPlan.value);
  await createCheckitem(cleanedData);
  closeDialog();
};

const addCheckItem = () => {
  // 添加一个新的检查项到 tableData
  const newCheckItem = {
    checkitemId: generateCheckitemId(), // 使用生成的唯一ID
    checkitemName: '', // 默认为空，用户需要填写
    checkitemWay: '', // 默认为空，用户需要填写
  };
  tableData.value.push(newCheckItem);
};

const deleteRow = (index) => {
  // 从 tableData 中删除指定索引的检查项
  tableData.value.splice(index, 1);
};

watch(
    () => props.checkInfoAddVisible,
    (newValue) => {
      dialogVisible.value = newValue;
      if (newValue) {
        clear(); // 每次打开对话框时清空数据
      }
    }
);
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
.demo-form-inline .el-input {
  --el-input-width: 220px;
}

.demo-form-inline .el-select {
  --el-select-width: 220px;
}

/* 样式调整 */

.formTitle {
  height: 26px;
  line-height: 26px;
  padding: 0 10px;
  border-left: 4px solid #409eff;
  margin-bottom: 15px;
}

.demo-form-inline {
  text-align: center;
}

/* 分割线 */
#separate {
  position: absolute;
  left: 0;
  top: 48px;
  width: 100%;
  border-bottom: 1px solid #ccc;
}

/* 调整表头为淡灰色并添加盒阴影 */
.el-table .el-table__header th {
  background-color: #000 !important;
  /* 调不到 */
}

#equipTable {
  box-shadow: 1px 3px 3px 1px #eee;
}
</style>
