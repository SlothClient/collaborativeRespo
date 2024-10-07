import {defineStore} from "pinia";
import {ref} from "vue";
import {getEquipmentInfo, getEquipmentMaintenanceType, getWorkerList} from "@/api/maintenancePlan/index.js";


export const useEquipmentInfoStore = defineStore("equipmentInfo", () => {
    const equipmentInfo = ref([]);
    const equipmentMaintenanceType = ref([]);
    const workerInfo = ref([])

    const getEquipmentInfoList = async () => {
        if (equipmentInfo.value.length === 0) {
            const res = await getEquipmentInfo();
            equipmentInfo.value = res.data.data;
        }
    };

    const getEquipmentMaintenanceTypeList = async () => {
        if (equipmentMaintenanceType.value.length === 0) {
            const res = await getEquipmentMaintenanceType();
            equipmentMaintenanceType.value = res.data.data;
        }
    };

    const getWorkerInfo = async () => {
        if (workerInfo.value.length === 0) {
            const res = await getWorkerList()
            workerInfo.value = res.data.data
        }
    }

    return {
        equipmentInfo,
        workerInfo,
        equipmentMaintenanceType,
        getEquipmentInfoList,
        getEquipmentMaintenanceTypeList,
        getWorkerInfo
    };
}, {
    persist: true // 开启持久化
});