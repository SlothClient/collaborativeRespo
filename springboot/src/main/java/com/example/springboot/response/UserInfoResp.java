package com.example.springboot.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ys
 * @date： 2024/8/18 16:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResp {

    /**
     * 用户id
     */
    private String Id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色
     */

    private List<String> roles;

    /**
     * 权限
     */

    private List<String>permissions;
}
