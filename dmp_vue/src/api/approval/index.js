import {request} from "@/utils/request.js";

export const getApprovalList = (filtersReq) => {
    return request.post('/approval/getApprovalList',filtersReq)
}

export const getApprovalDetail = (planId) =>{
    return request.get(`/approval/getApprovalDetail?planId=${planId}`)
}