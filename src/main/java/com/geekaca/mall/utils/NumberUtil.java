package com.geekaca.mall.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class NumberUtil {
    public static Long generateId(){
        Snowflake snowflake = IdUtil.getSnowflake(1);
        long nextId = snowflake.nextId();
        return nextId;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            Long aLong = generateId();
            System.out.println(aLong);
        }

    }
}
