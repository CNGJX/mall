package com.geekaca.mall;

import com.geekaca.mall.controller.param.UserParam;
import com.geekaca.mall.domain.User;
import com.geekaca.mall.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void testReg(){
        boolean register = userService.register("guest", "qwerty");
        Assertions.assertFalse(register);
    }

    @Test
    public void testLogin(){
        UserParam userParam = new UserParam();
        userParam.setLoginName("tom");
        //abcd
        userParam.setPasswordMd5("E2FC714C4727EE9395F324CD2E7F331F");
        User user = userService.login(userParam);
        Assertions.assertNotNull(user);
    }
}
