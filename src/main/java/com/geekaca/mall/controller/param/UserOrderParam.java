package com.geekaca.mall.controller.param;

import lombok.Data;

@Data
public class UserOrderParam {
    private Integer uid;
    private Integer orderStatus;
    private Integer pageNum;
    private Integer pageSize;
}
