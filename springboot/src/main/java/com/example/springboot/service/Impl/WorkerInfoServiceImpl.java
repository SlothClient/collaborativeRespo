package com.example.springboot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.CheckInfoDetail;
import com.example.springboot.entity.MaintanceInfoDetail;
import com.example.springboot.entity.OrderInfo;
import com.example.springboot.entity.WorkerInfo;
import com.example.springboot.mapper.CheckInfoMapper;
import com.example.springboot.mapper.MaintanceInfoMapper;
import com.example.springboot.mapper.OrderInfoMapper;
import com.example.springboot.request.WorkOrderReq;
import com.example.springboot.service.WorkerInfoService;
import com.example.springboot.mapper.WorkerInfoMapper;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.springboot.enums.maintenanceCodeEnum.PLAN_DISPATCHED;

/**
 * @author Lenovo
 * @description 针对表【worker_info】的数据库操作Service实现
 * @createDate 2024-10-07 17:24:27
 */
@Service
public class WorkerInfoServiceImpl extends ServiceImpl<WorkerInfoMapper, WorkerInfo>
        implements WorkerInfoService {


    @Autowired
    WorkerInfoMapper workerInfoMapper;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    MaintanceInfoMapper maintanceInfoMapper;
    @Autowired
    CheckInfoMapper checkInfoMapper;

    @Override
    public Result<List<WorkerInfo>> getWorkerInfo() {
        List<WorkerInfo> workerInfos = workerInfoMapper.selectList(
                new LambdaQueryWrapper<WorkerInfo>()
                        .select(WorkerInfo::getWorkerId, WorkerInfo::getWorkerName)
        );
        return Result.success(workerInfos);
    }

    @Override
    public Result<String> addWorkOrder(List<WorkOrderReq> workOrderReqList) {
        for (WorkOrderReq workOrderReq : workOrderReqList) {
            OrderInfo orderInfo = OrderInfo.
                    builder()
                    .workerId(workOrderReq.getWorkerId())
                    .equipId(workOrderReq.getEquipId())
                    .orderDesc(workOrderReq.getOrderDesc())
                    .startTime(workOrderReq.getDeliverTime())
                    .planId(workOrderReq.getPlanId())
//                    .checkId(workOrderReq.getCheckId())
                    .build();
            orderInfoMapper.insert(orderInfo);
            if(workOrderReq.getCheckId()!=null){
                CheckInfoDetail checkInfoDetail = checkInfoMapper.selectById(workOrderReq.getCheckId());
                checkInfoDetail.setStatus(PLAN_DISPATCHED.getCode());
                checkInfoMapper.updateById(checkInfoDetail);
            }
            if(workOrderReq.getPlanId()!=null){
                MaintanceInfoDetail maintanceInfoDetail = maintanceInfoMapper.selectById(workOrderReq.getPlanId());
                maintanceInfoDetail.setStatus(PLAN_DISPATCHED.getCode());
                maintanceInfoMapper.updateById(maintanceInfoDetail);
            }
        }
        return Result.success("派单成功，等待工人开始保养");
    }
}




