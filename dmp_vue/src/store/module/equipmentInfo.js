import {defineStore} from "pinia";
import {ref} from "vue";
import {getWorkerList} from "@/api/maintenancePlan/index.js";
import {getEquipmentInfo, getEquipmentMaintenanceType, getEquipStatusName,getDepartment,getType,getSite} from "@/api/equip/index.js"



export const useEquipmentInfoStore = defineStore("equipmentInfo", () => {
    const equipmentInfo = ref([]);
    const equipmentMaintenanceType = ref([]);
    const equipStatusName=ref([]);
    const department = ref([]);
    const type = ref([]);
    const site = ref([]);
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

    const getEquipStatusNameList = async() =>{
        if(equipStatusName.value.length === 0){
            const res = await getEquipStatusName();
            console.log(res)
            equipStatusName.value=res.data.data;
        }
    }
    const getDepartmentList = async ()=>{
        if(department.value.length === 0){
            const res = await getDepartment();
            department.value=res.data.data;
        }
    }

    const getTypeList = async ()=>{
        if(type.value.length === 0){
            const res = await  getType();
            type.value=res.data.data;
        }
    }

    const getSiteList = async ()=>{
        const res = await getSite();
        site.value=res.data.data;
    }

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
        equipStatusName,
        department,
        type,
        site,
        getEquipmentInfoList,
        getEquipmentMaintenanceTypeList,
        getWorkerInfo,
        getEquipStatusNameList,
        getDepartmentList,
        getTypeList,
        getSiteList
    };
}, {
    persist: true // 开启持久化
});