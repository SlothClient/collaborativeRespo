package com.example.springboot.config.saToken;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.example.springboot.mapper.MenuInfoMapper;
import com.example.springboot.mapper.RoleInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限验证接口拓展
 *
 * @author ys
 * @date： 2024/8/18 17:04
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private MenuInfoMapper menusMapper;

    @Autowired
    private RoleInfoMapper roleMapper;

    /**
     * 返回一个账号所拥有的权限码集合
     *
     * @param loginId   登入用户ID
     * @param loginType 登录账号类型
     * @return
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 声明权限码集合
        List<String> permissions = new ArrayList<>();
        for (String roleId : getRoleList(loginId, loginType)) {
            SaSession roleSession = SaSessionCustomUtil.getSessionById("role-" + roleId);
            List<String> list = roleSession.get(SaSession.PERMISSION_LIST, () -> menusMapper.selectPermissionByRoleId(roleId));
            permissions.addAll(list);
        }
        System.out.println(permissions);
        return permissions;

    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {

        SaSession session = StpUtil.getSessionByLoginId(loginId);
        return session.get("Role_List", () -> roleMapper.selectRoleListByUserId(loginId));
    }
}
