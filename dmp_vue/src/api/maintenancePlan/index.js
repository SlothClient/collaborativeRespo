import {request} from "@/utils/request.js";

export const getMaintenancePlan = () =>{
    return request.post("/maintenance/getMaintenancePlan");
}