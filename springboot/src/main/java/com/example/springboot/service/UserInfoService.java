package com.example.springboot.service;

import com.example.springboot.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.request.UserReq;
import com.example.springboot.response.MenuResp;
import com.example.springboot.response.UserInfoResp;
import com.example.springboot.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【user_info】的数据库操作Service
* @createDate 2024-09-23 20:36:48
*/
public interface UserInfoService extends IService<UserInfo> {

    String login(UserInfo user);

    String logout();

    List<MenuResp> getUserMenu();

    UserInfoResp getUserInfo();

    Result<IPage<UserInfoResp>> getUserInfoList(UserReq userReq);

    Result<String> updateUserInfoList(UserReq userInfoResp);

    Result<String>  addUserInfoList(UserReq userReq);

    Result<String>  deleteUserInfo(UserReq userReq);

    Result<String> updateAvatar(MultipartFile file);

}
