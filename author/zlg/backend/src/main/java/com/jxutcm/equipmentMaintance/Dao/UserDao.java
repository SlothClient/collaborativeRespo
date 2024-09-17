package com.jxutcm.equipmentMaintance.Dao;

import com.jxutcm.equipmentMaintance.Bean.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    public User doLogin(User user);
}
