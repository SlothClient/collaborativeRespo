package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.request.UserReq;
import com.example.springboot.response.MenuResp;
import com.example.springboot.response.UserInfoResp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【user_info】的数据库操作Mapper
* @createDate 2024-09-23 20:36:48
* @Entity com.example.springboot.entity.UserInfo
*/

@Mapper
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    List<MenuResp> getUserMenu(String userId);

    IPage<UserInfoResp> getUserInfoList(IPage<UserInfoResp> page, UserReq userReq);

}




