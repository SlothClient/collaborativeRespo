import {request} from "@/utils/request.js";

export const getCheckInfo= (checkInfoReq) => {
    return request.post("/check/getCheckInfo", checkInfoReq);
}

export const getEquipmentInfo = () => {
    return request.post("/equipment/getEquipmentInfo")
}

export const addCheckInfo = (checkInfo) => {
    return request.post("/check/addCheckInfo", checkInfo)
}

export const undoCheckInfo = (checkId) => {
    return request.get(`/check/undoCheckInfo?checkId=${checkId}`)
}

export const getCheckInfoDetail = (checkId) => {
    return request.post(`/check/getCheckInfoDetail?checkId=${checkId}`)
}

export const updateCheck = (newCheck) => {
    return request.post('/check/updateCheck', newCheck)
}

export const getWorkerList = () => {
    return request.post('/check/getWorkerInfo')
}

export const addWorkOrder = (param) => {
    return request.post('/check/addWorkOrder',param)
}