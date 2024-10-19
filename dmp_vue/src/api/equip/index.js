import {request} from "@/utils/request.js";


export const getEquipmentInfo = () => {
    return request.post("/equip/getEquipmentInfo")
}

export const getEquipmentMaintenanceType = () => {
    return request.post("/equip/getEquipmentMaintenanceType")
}

export const getEquipStatusName = () => {
    return request.post("/equipStatus/getEquipStatus")
}
export const getDepartment = () => {
    return request.post("/department/getDepartment")
}
export const getType = () => {
    return request.post("/type/getType")
}
export const getSite = () => {
    return request.post("/site/getSite")
}
export const getEquipInfoList = (EquipInfoReq) => {
    return request.post('/equip/getEquipInfoList',EquipInfoReq)
}

export const addEquipInfo = (EquipInfo) => {
    return request.post("/equip/addEquipInfo", EquipInfo)
}
export const updateEquipInfo = (newEquipInfo) => {
    return request.post('/equip/updateEquipInfo', newEquipInfo)
}

export const deleteEquipInfo = (equipId) =>{
    return request.get(`/equip/deleteEquipInfo?equipId=${equipId}`)

}