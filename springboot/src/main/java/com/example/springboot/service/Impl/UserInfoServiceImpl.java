package com.example.springboot.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.response.MenuResp;
import com.example.springboot.response.UserInfoResp;
import com.example.springboot.service.UserInfoService;
import com.example.springboot.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author Lenovo
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2024-09-23 20:36:48
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public String login(UserInfo user) {
        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .select(UserInfo::getUserId)
                .eq(UserInfo::getUsername, user.getUsername())
                .eq(UserInfo::getUserpwd, user.getUserpwd()));
        if(userInfo != null){
            StpUtil.login(userInfo.getUserId());
            return StpUtil.getTokenValue();
        }
        else{
            throw new RuntimeException();
        }
    }


    @Override
    public UserInfoResp getUserInfo() {
        String userId =(String) StpUtil.getLoginId();
        //查询用户信息
        UserInfo user = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUserId,userId));

        //查询用户角色
        List<String> roles = StpUtil.getRoleList();

        System.out.println(roles);

        //用户权限
        List<String> permission =StpUtil.getPermissionList().stream()
                .filter(string -> !string.isEmpty())
                .distinct()
                .collect(Collectors.toList());
        UserInfoResp userInfoResp = UserInfoResp.builder()
                .username(user.getUsername())
                .Id(userId)
                .roles(roles)
                .permissions(permission)
                .build();
        System.out.println(userInfoResp);
        return  userInfoResp;
    }


    //构建菜单树
    public List<MenuResp> buildMenuTree(List<MenuResp> menuList) {
        List<MenuResp> treeMenus = new ArrayList<>();
        Map<String, MenuResp> menuMap = new HashMap<>();

        // 将所有菜单项放入map中，方便查找
        for (MenuResp menu : menuList) {
            menuMap.put(menu.getMenuId(), menu);
        }

        // 构建树形结构
        for (MenuResp menu : menuList) {
            if (menu.getParentId() == null || menu.getParentId().isEmpty()) {
                // 这是一个顶级菜单
                treeMenus.add(menu);
            } else {
                // 这是一个子菜单
                MenuResp parentMenu = menuMap.get(menu.getParentId());
                if (parentMenu != null) {
                    parentMenu.addChild(menu);
                }
            }
        }

        // 对顶级菜单进行排序
        treeMenus.sort(Comparator.comparing(MenuResp::getMenuOrder));

        // 对每个菜单的子菜单进行排序
        for (MenuResp menu : menuList) {
            if (menu.getChildren() != null) {
                menu.getChildren().sort(Comparator.comparing(MenuResp::getMenuOrder));
            }
        }

        return treeMenus;
    }

    @Override
    public List<MenuResp> getUserMenu() {
        //当前用户id
        String userId = (String) StpUtil.getLoginId();
        List<MenuResp> userMenu =  userInfoMapper.getUserMenu(userId);
        return  buildMenuTree(userMenu);
    }

    @Override
    public String logout() {
        String userId = (String) StpUtil.getLoginId();
        System.out.println(userId);
        StpUtil.logout(userId);
        return "退出成功";
    }


}




