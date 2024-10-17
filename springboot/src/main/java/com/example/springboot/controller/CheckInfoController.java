package com.example.springboot.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.CheckInfoDetail;
import com.example.springboot.entity.MaintanceInfoDetail;
import com.example.springboot.entity.WorkerInfo;
import com.example.springboot.request.CheckInfoReq;
import com.example.springboot.request.WorkOrderReq;
import com.example.springboot.response.CheckInfo;
import com.example.springboot.response.CheckInfoResp;
import com.example.springboot.service.CheckInfoService;
import com.example.springboot.service.WorkerInfoService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-10-13
 */
@RestController
@RequestMapping("/check")
public class CheckInfoController {

    @Autowired
    CheckInfoService checkInfoService;

    @Autowired
    WorkerInfoService workerInfoService;
    /**
     * 查询所有点巡检计划列表
     *
     * @param checkInfoReq
     * @return {@link List <  CheckInfo  >}
     */
    @SaCheckLogin
    @ResponseBody
    @PostMapping("/getCheckInfo")
    public Result<IPage<CheckInfo>> getCheckInfo(@RequestBody(required = false) CheckInfoReq checkInfoReq) {
        System.out.println(checkInfoReq);
        return checkInfoService.getCheckInfo(checkInfoReq);
    }
    /**
     * 添加保养计划
     * @param checkInfo
     * @return
     */
    @SaCheckLogin
    @ResponseBody
    @PostMapping("/addCheckInfo")
    public Result addCheckInfo(@RequestBody CheckInfo checkInfo) {
        System.out.println(checkInfo);
        return checkInfoService.addCheckInfo(checkInfo);
    }

    /**
     * 撤销保养计划
     * @param checkId
     * @return
     */
    @SaCheckLogin
    @ResponseBody
    @GetMapping("/undoCheckInfo")
    public Result undoCheckInfo(@RequestParam("checkId") String checkId){
        System.out.println(checkId);
        return checkInfoService.undoCheckInfo(checkId);
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/getCheckInfoDetail")
    public Result getCheckInfoDetail(@RequestParam("checkId") String checkId){
        System.out.println(checkId);
        return checkInfoService.getCheckInfoDetail(checkId);
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/updateCheck")
    public Result updateCheck(@RequestBody CheckInfoDetail checkInfoDetail){
        System.out.println(checkInfoDetail);
        return checkInfoService.updateCheck(checkInfoDetail);
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/getWorkerInfo")
    public Result<List<WorkerInfo>>getWorkerInfo(){
        return workerInfoService.getWorkerInfo();
    }

    @ResponseBody
    @PostMapping("/addWorkOrder")
    public Result<String> addWorkOrder(@RequestBody List<WorkOrderReq> workOrderReqList){
        return workerInfoService.addWorkOrder(workOrderReqList);
    }
}
