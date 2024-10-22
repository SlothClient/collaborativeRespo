package com.example.springboot.service.Impl;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lenovo
 * @description 针对表【user_info】的数据库操作Service实现
 * @createDate 2024-09-23 20:36:48
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {


    @Value("${gorit.file.root.path}")
    private String filePath;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public String login(UserInfo user) {
        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .select(UserInfo::getUserId)
                .eq(UserInfo::getUsername, user.getUsername())
                .eq(UserInfo::getUserpwd, user.getUserpwd()));
        if (userInfo != null) {
            StpUtil.login(userInfo.getUserId());
            return StpUtil.getTokenValue();
        } else {
            throw new RuntimeException();
        }
    }


    @Override
    public UserInfoResp getUserInfo() {
        String userId = (String) StpUtil.getLoginId();
        //查询用户信息
        UserInfo user = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUserId, userId));

        //查询用户角色
        List<String> roles = StpUtil.getRoleList();

        //用户权限
        List<String> permission = StpUtil.getPermissionList().stream()
                .filter(string -> !string.isEmpty())
                .distinct()
                .collect(Collectors.toList());
        return UserInfoResp.builder()
                .username(user.getUsername())
                .Id(userId)
                .roles(roles)
                .permissions(permission)
                .avatar(user.getAvatarUrl())
                .build();
    }

    @Override
    public Result<IPage<UserInfoResp>> getUserInfoList(UserReq userReq) {
        IPage<UserInfoResp> page = new Page<>(userReq.getCurrentPage(), userReq.getPageSize());

        IPage<UserInfoResp> userInfoList = userInfoMapper.getUserInfoList(page, userReq);
        return Result.success(userInfoList);
    }

    @Override
    public Result<String> updateUserInfoList(UserReq user) {
        UserInfo userInfo = userInfoMapper.selectById(user.getUserId());
        System.out.println("userId=" + userInfo.getUserId());
        System.out.println("user=" + user);
        UserRole userRole = userRoleMapper.selectOne(
                new LambdaQueryWrapper<UserRole>()
                        .eq(UserRole::getUserId, user.getUserId())
        );
        System.out.println("userRole = ");
        userInfo.setUsername(user.getUsername());
        userInfo.setUserpwd(user.getUserpwd());
        userRole.setRoleId(user.getRoleId());
        int userRows = userInfoMapper.updateById(userInfo);
        int userRoleRows = userRoleMapper.updateById(userRole);

        if (userRows < 0 || userRoleRows < 0) {
            return Result.fail("修改失败");
        }
        return Result.success("修改成功");
    }

    @Override
    public Result<String> addUserInfoList(UserReq user) {
        // 创建并设置UserInfo对象
        System.out.println("add user = " + user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(user.getUsername());
        userInfo.setUserpwd(user.getUserpwd());
        int userInfoRows = userInfoMapper.insert(userInfo);
        UserRole userRole = new UserRole();
        userRole.setUserId(userInfo.getUserId());
        userRole.setRoleId(user.getRoleId());

        int userRoleRows = userRoleMapper.insert(userRole);

        if (userInfoRows < 0 || userRoleRows < 0) {
            return Result.fail("添加失败");
        }
        return Result.success("添加成功");
    }

    @Override
    public Result<String> deleteUserInfo(UserReq user) {
        System.out.println("delete user = " + user);
        int userInfoRows = userInfoMapper.deleteById(user.getUserId());
        UserRole userRole = userRoleMapper.selectOne(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, user.getUserId())
                .eq(UserRole::getRoleId, user.toString()));
        int userRoleRows = userRoleMapper.deleteById(userRole);

        if (userInfoRows < 0 || userRoleRows < 0) {
            return Result.fail("删除失败");
        }
        return Result.success("删除成功");
    }

    @Override
    public Result<String> updateAvatar(MultipartFile file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 得到格式化后的日期
        String format = sdf.format(new Date());
        // 获取上传的文件名称
        String fileName = file.getOriginalFilename();

        // 进行文件类型校验（如限制只能上传图片）
        String fileExtension = null;
        if (fileName != null) {
            fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        }
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
        if (!allowedExtensions.contains(fileExtension)) {
            return Result.fail("不支持的文件类型");
        }

        // 使用正则表达式去除文件名中的特殊字符，仅保留字母、数字、下划线和点
        String sanitizedFileName = null;
        if (fileName != null) {
            sanitizedFileName = fileName.replaceAll("[^a-zA-Z0-9\\.]", "_");
        }

        // 时间戳（或UUID）和文件名拼接，确保唯一性
        String newFileName = format + "_" + System.currentTimeMillis() + "_" + sanitizedFileName;

        // 得到文件保存的位置以及新文件名
        File dest = new File(filePath + newFileName);

        try {
            // 上传的文件被保存了
            file.transferTo(dest);
            // 打印日志
            System.out.println("上传成功，当前上传的文件保存在 " + filePath + newFileName);

            String userId = (String) StpUtil.getLoginId();
            UserInfo userInfo = userInfoMapper.selectById(userId);

            //删除原有文件
            String userAvatar = userInfo.getAvatarUrl();
            if (userAvatar != null){
                String oldAvatarPath = userInfo.getAvatarUrl().split("/")[userInfo.getAvatarUrl().split("/").length - 1];
                if (oldAvatarPath != null && !oldAvatarPath.isEmpty()) {
                    File oldFile = new File(filePath + oldAvatarPath);
                    if (oldFile.exists()) {
                        if (oldFile.delete()) {
                            System.out.println("旧头像文件删除成功：" + oldAvatarPath);
                        } else {
                            System.out.println("旧头像文件删除失败：" + oldAvatarPath);
                        }
                    }
                }
            }
            userInfo.setAvatarUrl("/avatar/" + newFileName);
            int row = userInfoMapper.updateById(userInfo);

            if (row > 0) {
                // 返回成功结果
                return Result.success("更新成功");
            }
            return Result.fail("出现未知错误");
        } catch (IOException e) {
            // 捕获异常并返回失败结果
            System.out.println("上传失败：" + e.getMessage());
            return Result.fail("文件上传失败，请重试");
        }
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
        List<MenuResp> userMenu = userInfoMapper.getUserMenu(userId);
        return buildMenuTree(userMenu);
    }

    @Override
    public String logout() {
        String userId = (String) StpUtil.getLoginId();
        System.out.println(userId);
        StpUtil.logout(userId);
        return "退出成功";
    }


}




