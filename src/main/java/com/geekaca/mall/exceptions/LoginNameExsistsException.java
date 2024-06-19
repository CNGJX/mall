package com.geekaca.mall.exceptions;

public class LoginNameExsistsException extends RuntimeException{
    //错误编码值
    private int code;

    public LoginNameExsistsException(int code, String message) {
        super(message);
        this.code = code;
    }
}
