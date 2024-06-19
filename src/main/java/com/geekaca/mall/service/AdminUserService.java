package com.geekaca.mall.service;

import com.geekaca.mall.controller.param.AdminParam;
import com.geekaca.mall.domain.AdminUser;

public interface AdminUserService {
    AdminUser getAdminUser(Long uid);

    int changeUerLoginName(AdminUser adminUser);

    String login(AdminParam adminParam);
}
