import { ref } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';

/**
 * 获取指定订单的设备信息
 *
 * @param selectedOrder 包含订单信息的响应式引用
 * @returns 包含表格数据和获取设备信息的函数的对象
 */
export function useEquipInfo(selectedOrder) {
    const tableData = ref([]);

    const getSelectedEquipInfo = async () => {
        const condition = {
            planId: selectedOrder.planId,
            equipId: selectedOrder.equipId
        };
        // console.log(condition);
        const formData = new FormData();
        formData.append('conditionJson', JSON.stringify(condition));

        try {
            const response = await axios.post('/api/getSelectedEquipInfo', formData);
            if (response.data.status) {
                tableData.value = response.data.list;
                // console.log(tableData);
                ElMessage.success('获取设备信息成功!');
            } else {
                ElMessage.error(response.data.msg);
            }
        } catch (e) {
            console.error('请求错误!', e);
        }
    };

    return { tableData, getSelectedEquipInfo };
}