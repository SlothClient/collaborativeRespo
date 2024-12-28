package com.example.springboot.service;

import com.example.springboot.entity.WorkerInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.request.WorkOrderReq;
import com.example.springboot.utils.Result;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【worker_info】的数据库操作Service
* @createDate 2024-10-07 17:24:27
*/
public interface WorkerInfoService extends IService<WorkerInfo> {

    Result<List<WorkerInfo>> getWorkerInfo();

    Result<String> addWorkOrder(List<WorkOrderReq> workOrderReqList);
}
