package com.example.springboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.request.UserReq;
import com.example.springboot.response.MenuResp;
import com.example.springboot.response.UserInfoResp;
import com.example.springboot.service.UserInfoService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ys
 * @date： 2024/9/23 20:49
 */

@Controller
public class userController {

    @Autowired
    private UserInfoService userInfoService;

    @ResponseBody
    @PostMapping("/user/login")
    public Result<String> login(@RequestBody UserInfo userInfo) {
        System.out.println(userInfo);
        String res = userInfoService.login(userInfo);
        System.out.println(StpUtil.getRoleList());
        return Result.success(res);
    }

    /**
     * 获取用户个人信息
     */
    @SaCheckLogin
    @GetMapping("/user/getUserInfo")
    @ResponseBody
    public Result<UserInfoResp> getUserInfo() {
        System.out.println("获取用户信息");
        return Result.success(userInfoService.getUserInfo());
    }

    /**
     * 获取用户菜单
     *
     * @return 菜单类
     */
    @SaCheckLogin
    @ResponseBody
    @GetMapping("user/getUserMenu")
    public Result<List<MenuResp>> getUserMenu() {
        return Result.success(userInfoService.getUserMenu());
    }


    @ResponseBody
    @GetMapping("user/logout")
    public Result logout() {
        return Result.success(userInfoService.logout());
    }

    @ResponseBody
    @PostMapping("/getUserInfoList")
    public Result<IPage<UserInfoResp>> getUserInfoList(@RequestBody(required = false)UserReq userReq) {
        return userInfoService.getUserInfoList(userReq);
    }
    @ResponseBody
    @PostMapping("/updateUserInfo")
    public Result updateUserInfoList(@RequestBody UserReq user) {
        System.out.println(user);
        return userInfoService.updateUserInfoList(user);
    }
    @ResponseBody
    @PostMapping("/addUserInfoList")
    public Result<String> addUserInfoList(@RequestBody UserReq user) {
        System.out.println(user);
        return userInfoService.addUserInfoList(user);
    }

    @ResponseBody
    @PostMapping("/deleteUserInfo")
    public Result<String> deleteUserInfo(@RequestBody UserReq user) {
        System.out.println(user);
        return userInfoService.deleteUserInfo(user);
    }

}
