import {request} from "@/utils/request.js";

export const getCheckitem = (checkId) => {
    return request.get(`/checkitem/getCheckitem?checkId=${checkId}`)
}
export const createCheckitem = (checkitemInfos) => {
    return request.post("/checkitem/create",checkitemInfos)
}
export const updateCheckitem = (checkitemInfos) => {
    return request.post("/checkitem/update",checkitemInfos)
}
export const deleteCheckitem = (checkitemIds) => {
    return request.post("/checkitem/delete",checkitemIds)
}
