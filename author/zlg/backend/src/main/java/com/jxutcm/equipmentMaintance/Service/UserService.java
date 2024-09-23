package com.jxutcm.equipmentMaintance.Service;

import com.jxutcm.equipmentMaintance.Bean.User;

public interface UserService {
    public User doLogin(String uname, String upwd);
}
