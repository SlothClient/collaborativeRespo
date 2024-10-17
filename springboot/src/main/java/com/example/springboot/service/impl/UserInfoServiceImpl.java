package com.example.springboot.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.RoleInfo;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.entity.UserRole;
import com.example.springboot.mapper.RoleInfoMapper;
import com.example.springboot.mapper.UserRoleMapper;
import com.example.springboot.request.UserReq;
import com.example.springboot.response.MenuResp;
import com.example.springboot.response.UserInfoResp;
import com.example.springboot.service.UserInfoService;
import com.example.springboot.mapper.UserInfoMapper;
import com.example.springboot.utils.Result;
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

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleInfoMapper roleInfoMapper;

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

    @Override
    public Result<IPage<UserInfoResp>> getUserInfoList(UserReq userReq) {
        IPage<UserInfoResp> page = new Page<>(userReq.getCurrentPage(), userReq.getPageSize());
        IPage<UserInfoResp> userRespIPage = userInfoMapper.getUserInfoList(page,userReq);
        return Result.success(userRespIPage);
    }

    @Override
    public Result updateUserInfoList(UserInfoResp userInfoResp) {

        int row = userInfoMapper.updateUserInfo(userInfoResp);
        if (row > 0) {
            return Result.success("修改成功");
        } else {
            return Result.fail("修改失败");
        }

    }

    @Override
    public Result addUserInfoList(UserInfoResp userInfoResp) {
        // 创建并设置UserInfo对象
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userInfoResp.getUsername());
        userInfo.setUserpwd(userInfoResp.getUserpwd());
        userInfoMapper.insert(userInfo);

        // 查询角色信息
        RoleInfo roleInfo = roleInfoMapper.selectOne(
                new LambdaQueryWrapper<RoleInfo>()
                        .eq(RoleInfo::getRoleName, userInfoResp.getRoleName())
        );

        // 如果角色信息存在，返回新增用户失败
        if (roleInfo != null) {
            return Result.fail("新增用户失败");
        }

        // 获取角色ID并创建UserRole对象
        String roleId = roleInfo.getRoleId();
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userInfo.getUserId());
        userRoleMapper.insert(userRole);

        // 返回新增用户成功
        return Result.success("新增用户成功");
    }

    @Override
    public Result deleteUserInfo(UserInfoResp userInfoResp) {
        int row = userInfoMapper.delete(
                new LambdaQueryWrapper<UserInfo>()
                        .eq(UserInfo::getUsername,userInfoResp.getUsername())
        );
        RoleInfo roleInfo = roleInfoMapper.selectOne(
                new LambdaQueryWrapper<RoleInfo>()
                        .eq(RoleInfo::getRoleName,userInfoResp.getRoleName())
        );
        String roleId = roleInfo.getRoleId();
        int row1 = userRoleMapper.delete(
                new LambdaQueryWrapper<UserRole>()
                     .eq(UserRole::getRoleId,roleId)
        );
        if(row <= 0 || row1 <= 0){
            return Result.fail("删除失败，请联系管理员处理");
        }else{
            return Result.success("删除成功");
        }
    }
//        if(updateUser == null){
//            return Result.fail("修改失败并未找到该用户，请联系后台管理员解决");
//        }
//        updateUser.setUserpwd(userInfoResp.getUserpwd());
//        updateUser.setUsername(userInfoResp.getUsername());
//        userInfoMapper.updateById(updateUser);
//
//        UserRole userRole = userRoleMapper.selectOne(
//                new LambdaQueryWrapper<UserRole>()
//                        .eq(UserRole::getUserId)
//        )



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




