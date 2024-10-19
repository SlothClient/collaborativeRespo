package com.example.springboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.ApprovalInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.request.FiltersReq;
import com.example.springboot.response.ApprovalDetailResp;
import com.example.springboot.response.ApprovalResp;
import com.example.springboot.utils.Result;


/**
* @author Lenovo
* @description 针对表【approval_info】的数据库操作Service
* @createDate 2024-09-24 11:16:44
*/
public interface ApprovalInfoService extends IService<ApprovalInfo> {

    Result<IPage<ApprovalResp>> getApprovalList(FiltersReq filters);

    Result<ApprovalDetailResp>  getApprovalDetail(String planID);

    Result<String> approve(String planId,String approvalRemark);

    Result<String> reject(String planId, String approvalRemark);

    Result<String> delete(String planId);
}
