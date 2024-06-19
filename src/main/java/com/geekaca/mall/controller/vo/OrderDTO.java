package com.geekaca.mall.controller.vo;

import lombok.Data;

@Data
public class OrderDTO {
    private Integer userId;
    private Integer[] cartItemIds;
    private Long orderNo;
    private Integer totalPrice;
    private Integer addressId;
}
