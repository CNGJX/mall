package com.geekaca.mall.controller.param;

/**
 * 传递参数的载体
 * 仅仅包含必要的属性
 */
public class UserParam {
    private String loginName;
    private String passwordMd5;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPasswordMd5() {
        return passwordMd5;
    }

    public void setPasswordMd5(String passwordMd5) {
        this.passwordMd5 = passwordMd5;
    }
}
