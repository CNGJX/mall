package com.geekaca.mall.utils;

//前后端code代表的含义要统一，通常都是后端定义好发给前端
public class Code {
    public static final Integer SAVE_OK = 20011;//通常不需要这么麻烦 返回200即可
    public static final Integer DELETE_OK = 20021;
    public static final Integer UPDATE_OK = 20031;
    public static final Integer GET_OK = 20041;
    public static final Integer SAVE_ERR = 20010;
    public static final Integer DELETE_ERR = 20020;
    public static final Integer UPDATE_ERR = 20030;
    public static final Integer GET_ERR = 20040;

    public static final Integer SYSTEM_UNKNOWN_ERROR = 50001;
    public static final Integer SYSTEM_TIMEOUT_ERROR = 50002;
    public static final Integer PROJECT_VALIDATE_ERROR = 60001;
    public static final Integer PROJECT_BUSINESS_ERROR = 60002;

    public static final Integer RESULT_OK = 200;
    public static final Integer RESULT_FAIL = 500;
    public static final String UPLOAD_PATH = "C:\\dev\\codes\\newbee-mall-api\\static-files\\upload\\";
}
