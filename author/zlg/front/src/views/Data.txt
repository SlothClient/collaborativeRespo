<template>
    <div id="Data">
        <h1 id="marker">Data:{{ page }}</h1>
        <div class="higher">
            <div class="searchBlock">
                <input type="text" id="kwInput">
                <div id="filterBtn" @click="getData">
                    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor"
                        class="bi bi-search" viewBox="0 0 16 16">
                        <path
                            d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
                    </svg>
                </div>
            </div>
            <button id="addBtn" @click="openAddDialog">增加数据</button>
        </div>
        <div class="tableBlock">
            <table>
                <thead>
                    <tr>
                        <th v-for="key in Object.keys(dataSample)" :key="key">{{ key }}</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="row in data" :key="row.id">
                        <td v-for="key in Object.keys(dataSample)" :key="key">
                            {{ row[key] }}
                        </td>
                        <!-- 普通的信息管理，不传page或穿空，默认添加编辑和删除功能 -->
                        <td v-if="!props.page">
                            <button @click="editData(row)">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#fff"
                                    class="bi bi-pencil-square" viewBox="0 0 16 16">
                                    <path
                                        d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
                                    <path fill-rule="evenodd"
                                        d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z" />
                                </svg>
                            </button>
                            <button @click="deleteData(row.id)">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#fff"
                                    class="bi bi-trash-fill" viewBox="0 0 16 16">
                                    <path
                                        d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z" />
                                </svg>
                            </button>
                        </td>
                        <!-- 保养管理，指已经通过审批的保养维护计划，所需功能是派单 -->
                        <td v-if="props.page === 'getMaintance'">
                            <button @click="openDeliverDialog(row)">派单</button>
                        </td>
                        <!-- 工单管理，指已经通过派单的保养维护计划，所需功能是工作记录 -->
                        <td v-if="props.page === 'getOrder'">
                            <button @click="openRecordDialog(row)">工作记录</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div id="deliverDialog" v-if="props.page === 'getMaintance'">
            <el-dialog v-model="outerVisible" title="派单" width="800">
                <el-form :model="planForm" label-width="auto" style="max-width: 600px">
                    <el-form-item label="计划编号">
                        <el-input v-model="planForm.id" disabled />
                    </el-form-item>
                    <el-form-item label="计划名称">
                        <el-input v-model="planForm.name" disabled />
                    </el-form-item>
                    <el-form-item label="设备编号">
                        <el-input v-model="planForm.equipmentId" disabled />
                    </el-form-item>
                    <el-form-item label="计划类型">
                        <el-select v-model="planForm.type" placeholder="选择保养类型" disabled>
                            <el-option label="小修" value="小修" />
                            <el-option label="中修" value="中修" />
                            <el-option label="大修" value="大修" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="开始时间">
                        <el-col :span="24">
                            <el-date-picker v-model="planForm.startTime" type="datetime" placeholder="选择计划开始时间"
                                style="width: 100%" disabled />
                        </el-col>
                    </el-form-item>
                    <el-form-item label="结束时间">
                        <el-col :span="24">
                            <el-date-picker v-model="planForm.endTime" type="datetime" placeholder="选择计划结束时间"
                                style="width: 100%" disabled />
                        </el-col>
                    </el-form-item>
                    <el-form-item label="负责人">
                        <el-select v-model="planForm.targetPerson" placeholder="选择负责人" required>
                            <el-option v-for="person in personnel" :key="person.编号" :label="`${person.编号}-${person.姓名}`"
                                :value="person.编号" />
                        </el-select>
                    </el-form-item>
                    <!-- 可以添加备注栏 -->
                    <!-- <el-form-item label="Activity form">
                        <el-input v-model="form.desc" type="textarea" />
                    </el-form-item> -->
                </el-form>
                <el-dialog v-model="innerVisible" width="500" title="确认派单" append-to-body>
                    <span>请检查信息</span>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">确认</el-button>
                        <el-button @click="innerVisible = false">取消</el-button>
                    </el-form-item>
                </el-dialog>
                <template #footer>
                    <div class="dialog-footer">
                        <el-button @click="outerVisible = false">取消</el-button>
                        <el-button type="primary" @click="goToCheck">
                            派单
                        </el-button>
                    </div>
                </template>
            </el-dialog>
        </div>
        <div id="recordDialog" v-if="props.page === 'getOrder'">
            <el-dialog v-model="recordDialogVisible" title="工作记录" width="800">
                <el-form label-width="auto" style="max-width: 600px">
                    <el-form-item label="工作记录">
                        <el-input type="textarea" v-model="workRecordInfo.description" placeholder="请输入工作记录描述" />
                    </el-form-item>
                    <el-form-item label="上传图片">
                        <el-upload class="upload-dragger" action="#" :on-preview="handlePreview"
                            :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3"
                            :on-exceed="handleExceed" :file-list="workRecordInfo.images">
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">
                                将图片拖到此处，或<em>点击上传</em>
                            </div>
                            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="上传文件">
                        <el-upload class="upload-dragger" action="#" :on-preview="handlePreview"
                            :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3"
                            :on-exceed="handleExceed" :file-list="workRecordInfo.files">
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">
                                将文件拖到此处，或<em>点击上传</em>
                            </div>
                            <div class="el-upload__tip" slot="tip">只能上传pdf/docx文件，且不超过500kb</div>
                        </el-upload>
                    </el-form-item>
                </el-form>
                <template #footer>
                    <div class="dialog-footer">
                        <el-button @click="recordDialogVisible = false">取消</el-button>
                        <el-button type="primary" @click="submitWorkRecord">提交</el-button>
                    </div>
                </template>
            </el-dialog>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';

const props = defineProps(['page']);
const ths: any = ref([]);
const data = ref([]);
const dataSample = ref({}); // 用于存储单个数据样本，以便获取键名

const getData = async () => {
    let formData = new FormData();
    formData.append("conditionJson", JSON.stringify({}));

    let option = {
        url: `http://localhost:8889/equip/${props.page}`,
        method: "post",
        data: formData
    };

    try {
        const response = await axios(option);
        if (response.data.status) {
            data.value = response.data.list;
            dataSample.value = response.data.list[0] || {}; // 假设列表至少有一个元素
            ths.value = Object.keys(dataSample.value); // 设置表头
            console.log(data.value);
        } else {
            ElMessage.error(response.data.msg);
        }
    } catch (error) {
        ElMessage.error('请求失败，请检查网络或服务器状态');
    }
};

watch(() => props.page, (newPage) => {
    console.log(`Page changed: ${newPage}`);
    getData();
});

onMounted(() => {
    getData();
});

// ========================================================派单逻辑========================================================
// import deliverDialog from '@/components/DeliverOrder.vue'
const outerVisible = ref(false)
const innerVisible = ref(false)
import { reactive } from 'vue'

// do not use same name with ref
const planForm = reactive({
    id: '',
    equipmentId: '',
    name: '',
    targetPerson: '',
    type: '',
    startTime: '',
    endTime: '',
})


const personnel = ref([]); // 用于存储负责人列表
/**
 * @description: 获取员工
 */
const fetchManagers = async () => {
    try {
        const response = await axios.get('http://localhost:8889/equip/getEmployees');
        if (response.data.status) {
            personnel.value = response.data.list;
            console.log(personnel.value);

        } else {
            ElMessage.error(response.data.msg);
        }
    } catch (error) {
        ElMessage.error('获取负责人列表失败，请重试');
        console.error('获取负责人列表失败:', error);
    }
};
/**
 * @description: 打开派单对话框
 * @param rowData
 */
const openDeliverDialog = (rowData: any) => {
    console.log("Received data:", rowData); // 确保这里能接收到正确的数据
    planForm.id = rowData.计划编号;
    planForm.equipmentId = rowData.设备编号;
    planForm.name = rowData.计划名称;
    planForm.startTime = rowData.开始时间;
    planForm.endTime = rowData.结束时间;
    planForm.type = rowData.保养类型;
    fetchManagers();
    outerVisible.value = true;
};
// 验证规则：负责人必选
const rules = {
    targetPerson: [
        { required: true, message: '请选择负责人', trigger: 'change' },
    ],
};

const validateForm = () => {
    let valid = true;
    // 这里可以添加其他表单验证逻辑
    if (!planForm.targetPerson) {
        ElMessage.error('请选择负责人');
        valid = false;
    }
    return valid;
};
const goToCheck = () => {
    if (validateForm()) {
        innerVisible.value = true;
    }
};

import { v4 as uuidv4 } from 'uuid';

const onSubmit = async () => {
    if (validateForm()) {
        try {
            // 构造 orderInfoJson 对象
            const orderInfoJson = {
                工单编号: uuidv4(),
                设备编号: planForm.equipmentId,
                派单时间: new Date().toISOString(), // 当前时间作为派单时间
                结束时间: planForm.endTime,
                标准工时: calculateStandardWorkingHours(), // 需要实现此函数来计算标准工时
                负责人编号: planForm.targetPerson,
                工单备注: planForm.type, // 假设 planForm.type 是工单备注
                工作记录: "" // 初始为空，可能后续会添加
            };

            // 将对象转换为 JSON 字符串
            const orderInfoJsonString = JSON.stringify(orderInfoJson);
            // json注入FormData
            const formData = new FormData();
            formData.append('orderJson', orderInfoJsonString);
            // 自定义请求接口
            const option = {
                url: 'http://localhost:8889/equip/addOrder',
                method: 'post',
                data: formData
            }
            // 发送派单请求
            const response = await axios(option);

            if (response.data.status) {
                ElMessage.success('派单成功');
                innerVisible.value = false;
                outerVisible.value = false;
                // getData();
            } else {
                ElMessage.error(response.data.msg);
            }
        } catch (error) {
            ElMessage.error('派单失败，请重试');
            console.error('派单失败:', error);
            innerVisible.value = false;
            outerVisible.value = false;
        }
    }
};

// 计算标准工时的示例函数
function calculateStandardWorkingHours() {
    // 假设每天工作 8 小时，计算两个日期之间的工作日数量
    const startDate = new Date(planForm.startTime);
    const endDate = new Date(planForm.endTime);
    const differenceInTime = endDate.getTime() - startDate.getTime();
    const differenceInHours = differenceInTime / (1000 * 3600);
    return (differenceInHours / 8).toFixed(2); // 保留两位小数
}

// ========================================================派单逻辑========================================================
// 添加工作记录逻辑
const recordDialogVisible = ref(false);
const workRecordInfo = reactive({
    description: '',
    images: [], // 存储图片文件列表
    files: [] // 存储文件文件列表
});

const openRecordDialog = (rowData: any) => {
    // 可以在这里设置初始数据
    recordDialogVisible.value = true;
};

// 提交工作记录

</script>
<style scoped>
#Data {
    width: 100%;
    height: 100%;
    padding: 20px;
}

#marker {
    position: absolute;
    top: 100px;
    right: 30px;
    opacity: .3;
}

.higher {
    display: flex;
    width: 100%;
    justify-content: space-between;
}

.searchBlock {
    display: flex;
    position: relative;
}

#kwInput {
    border: none;
    width: 119px;
    height: 49px;
    background-color: rgba(255, 136, 0, 1);
    opacity: .3;
    border-radius: 8px;
    padding: 0 15px;
    box-shadow: 5px 4px 6px 2px rgba(0, 0, 0, .4);
    transition: all .6s;
}

#kwInput:focus {
    outline: none;
    width: 319px;
}

#filterBtn {
    width: 49px;
    height: 49px;
    background-color: rgba(255, 136, 0, .6);
    border: none;
    border-top-right-radius: 8px;
    border-bottom-right-radius: 8px;
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    right: 0;
    cursor: pointer;
}

#addBtn {
    margin-right: 200px;
    background-color: rgba(255, 136, 0, .6);
    border: none;
    border-radius: 5px;
    padding: 0 10px;
    font-weight: 900;
    font-family: "幼圆";
    box-shadow: inset 0 0 2px 3px rgba(255, 136, 0, .6);
    cursor: pointer;
    z-index: 3;
}

.tableBlock table {
    margin-top: 60px;
}

table,
th,
td {
    /* border: 1px solid #000; */
    border-collapse: collapse;
}

th,
td {
    width: 169px;
    height: 33px;
    background-color: rgba(135, 211, 248, .9);
    text-align: center;
    padding: 5px 0;
}

td {
    background-color: rgba(135, 211, 248, .5);
}

td button {
    background-color: rgba(255, 136, 0, .6);
    border: none;
    /* width: 36px; */
    padding: 0 10px;
    height: 36px;
    border-radius: 6px;
    cursor: pointer;
}

td button:last-child {
    margin-left: 10px;
}
</style>