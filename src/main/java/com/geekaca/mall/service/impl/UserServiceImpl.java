package com.geekaca.mall.service.impl;

import com.geekaca.mall.controller.param.UserParam;
import com.geekaca.mall.domain.User;
import com.geekaca.mall.exceptions.LoginNameExsistsException;
import com.geekaca.mall.mapper.UserMapper;
import com.geekaca.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.geekaca.mall.constants.MallConstants.CODE_LOGIN_NAME_EXISTS;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean register(String loginName, String password) {
        /**
         * 1.检查用户名是否重复
         * 2.执行注册
         */
        int cnt = userMapper.selectCountByLoginName(loginName);
        if (cnt > 0){
//            用户名已占用
            throw new LoginNameExsistsException(CODE_LOGIN_NAME_EXISTS, "用户名" + loginName + "已经被占用");
        }
        User user = new User();
        user.setLoginName(loginName);
        user.setPasswordMd5(password);
        int insert = userMapper.insert(user);
        return insert > 0;
    }

    @Override
    public User login(UserParam userParam) {
        return userMapper.selectByLoginNamePasswd(userParam);
    }

    @Override
    public User getUserInfo(Long uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    @Override
    public Boolean lockUsers(Long[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return userMapper.lockUserBatch(ids, lockStatus) > 0;
    }

    @Override
    public int changeUserInfo(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
