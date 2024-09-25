import {request} from "@/utils/request.js";

export const getMaintenancePlan = (maintenancePlanReq) =>{
    return request.post("/maintenance/getMaintenancePlan",maintenancePlanReq);
}

export const getMaintenancePlanSize = () =>{
    return request.get("/maintenance/getMaintenancePlanSize");
}