package com.geekaca.mall.service.impl;

import com.geekaca.mall.controller.param.AdminParam;
import com.geekaca.mall.domain.AdminUser;
import com.geekaca.mall.mapper.AdminUserMapper;
import com.geekaca.mall.service.AdminUserService;
import com.geekaca.mall.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser getAdminUser(Long uid) {
        return adminUserMapper.getUserByUid(uid);
    }

    @Override
    public int changeUerLoginName(AdminUser adminUser) {
        return adminUserMapper.updateByPrimaryKeySelective(adminUser);
    }

    @Override
    public String login(AdminParam adminParam) {
        AdminUser adminUser = adminUserMapper.getUserByAdminParam(adminParam);
        if (adminUser == null){
            //登陆失败
            return null;
        }
        //生成token
        //Long -> String
        String token = JwtUtil.createToken(adminUser.getAdminUserId().toString(), adminUser.getLoginUserName());
        return token;
    }
}
