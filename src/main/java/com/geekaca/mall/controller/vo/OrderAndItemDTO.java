package com.geekaca.mall.controller.vo;

import com.geekaca.mall.domain.Order;
import com.geekaca.mall.domain.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderAndItemDTO extends Order {
    //此订单中包含的商品列表
    private List<OrderItem> newBeeMallOrderItemVOS;
    private String orderStatusString;
}
