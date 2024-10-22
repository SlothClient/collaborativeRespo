package com.example.springboot.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ys
 * @date： 2024/10/18 19:07
 */

@Data
@AllArgsConstructor
public class UserInfoReq {

    private String username;
    private String userpwd;
    private String roleId;

}
