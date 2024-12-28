import {request} from "@/utils/request.js";

export const getEquipItem = (equipId) =>{
    return request.get(`/checkitemEquip/getcheckitem?equipId=${equipId}`)

}