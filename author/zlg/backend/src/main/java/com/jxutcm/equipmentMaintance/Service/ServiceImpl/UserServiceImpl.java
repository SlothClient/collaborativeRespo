package com.jxutcm.equipmentMaintance.Service.ServiceImpl;

import com.jxutcm.equipmentMaintance.Bean.User;
import com.jxutcm.equipmentMaintance.Dao.UserDao;
import com.jxutcm.equipmentMaintance.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User doLogin(String uname, String upwd) {
        User user = new User(uname,upwd);
        user = userDao.doLogin(user);
        if(user == null){
            throw new RuntimeException("账号或密码错误！");
        }
        return user;
    }
}
