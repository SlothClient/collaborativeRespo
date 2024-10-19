package com.example.springboot.request;

import lombok.Data;

@Data
public class UserReq extends PageReq{
    private String username;

    private String roleName;
}
