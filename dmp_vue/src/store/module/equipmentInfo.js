import {defineStore} from "pinia";
import {ref} from "vue";
import {getEquipmentInfo, getEquipmentMaintenanceType} from "@/api/maintenancePlan/index.js";


export const useEquipmentInfoStore = defineStore("equipmentInfo", () => {
    const equipmentInfo = ref([]);
    const equipmentMaintenanceType = ref([]);

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

    return {
        equipmentInfo,
        equipmentMaintenanceType,
        getEquipmentInfoList,
        getEquipmentMaintenanceTypeList
    };
}, {
    persist: true // 开启持久化
});