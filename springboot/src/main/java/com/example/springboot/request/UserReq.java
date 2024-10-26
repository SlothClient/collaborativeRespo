package com.example.springboot.request;

import lombok.Data;

@Data
public class UserReq extends PageReq{

    private String userId;
    private String username;

    private String userpwd;
    private String roleId;
    private String roleName;
}
