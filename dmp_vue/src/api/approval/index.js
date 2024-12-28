import {request} from "@/utils/request.js";

export const getApprovalList = (filtersReq) => {
    return request.post('/approval/getApprovalList',filtersReq)
}

export const getApprovalDetail = (planId) =>{
    return request.get(`/approval/getApprovalDetail?planId=${planId}`)
}

export const approvalPass = (planId,approvalRemark) => {
    return request.get(`/approval/approve?planId=${planId}&approvalRemark=${approvalRemark}`)
}

export const approvalReject= (planId,approvalRemark) => {
    return request.get(`/approval/reject?planId=${planId}&approvalRemark=${approvalRemark}`)
}

export const deleteApproval = (planId) =>{
    return request.get(`approval/deleteApproval?planId=${planId}`)
}