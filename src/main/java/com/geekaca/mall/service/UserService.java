package com.geekaca.mall.service;

import com.geekaca.mall.controller.param.UserParam;
import com.geekaca.mall.domain.User;

public interface UserService {
    boolean register(String loginName, String password);

    User login(UserParam userParam);

    User getUserInfo(Long uid);

    Boolean lockUsers(Long[] ids, int lockStatus);

    int changeUserInfo(User user);
}
