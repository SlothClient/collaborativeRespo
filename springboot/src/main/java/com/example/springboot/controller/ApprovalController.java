package com.example.springboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.request.FiltersReq;
import com.example.springboot.response.ApprovalResp;
import com.example.springboot.service.ApprovalInfoService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author ys
 * @date： 2024/9/26 19:26
 */
@RestController
@RequestMapping("/approval")
public class ApprovalController{

    @Autowired
    private ApprovalInfoService approvalInfoService;

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/getApprovalList")
    public Result<IPage<ApprovalResp>>getApprovalList(@RequestBody(required = false)FiltersReq filters){
        System.out.println(filters);
        return approvalInfoService.getApprovalList(filters);
    }


    @SaCheckLogin
    @ResponseBody
    @GetMapping("/getApprovalDetail")
    public Result getApprovalDetail(@RequestParam("planId") String planID){
        System.out.println(planID);
        return approvalInfoService.getApprovalDetail(planID);
    }

}
