import {request} from "@/utils/request.js";

export const getMaintenancePlan = (maintenancePlanReq) =>{
    return request.post("/maintenance/getMaintenancePlan",maintenancePlanReq);
}


export  const getEquipmentInfo = () =>{
    return request.post("/equipment/getEquipmentInfo")
}

export const getEquipmentMaintenanceType = () =>{
    return request.post("/equipment/getEquipmentMaintenanceType")
}


export const addPlan = (plan) =>{
    return request.post("/maintenance/addMaintenancePlan",plan)
}

export const undoPlan = (planId) =>{
    return request.get(`/maintenance/undoMaintenancePlan?planId=${planId}`)
}

export const getPlanDetail = (planId) =>{
    return request.post(`/maintenance/getMaintenancePlanDetail?planId=${planId}`)
}

export const updateMaintenance = (newPlan) =>{
    return request.post('/maintenance/updateMaintenance',newPlan)
}