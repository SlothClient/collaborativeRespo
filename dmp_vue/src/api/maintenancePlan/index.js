import {request} from "@/utils/request.js";

export const getMaintenancePlan = (maintenancePlanReq) =>{
    return request.post("/maintenance/getMaintenancePlan",maintenancePlanReq);
}

export const getMaintenancePlanSize = () =>{
    return request.get("/maintenance/getMaintenancePlanSize");
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