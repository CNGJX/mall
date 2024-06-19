package com.geekaca.mall.service;

import com.geekaca.mall.controller.param.UserOrderParam;
import com.geekaca.mall.controller.vo.MallOrderDetailVO;
import com.geekaca.mall.controller.vo.OrderAndItemDTO;
import com.geekaca.mall.controller.vo.OrderDTO;
import com.geekaca.mall.domain.Order;
import com.geekaca.mall.utils.PageResult;

public interface OrderService {

    Order createOrder(OrderDTO orderDTO);

    Order findById(Integer orderId);

    int updateOrderStatus(String orderNo, Integer orderStatus, int payType);

    PageResult getOrderList(UserOrderParam userOrderParam);

    // 后台获取订单详情
    MallOrderDetailVO getOrderDetailByOrderId(Integer orderId);

    int cancelUserOrder(Long orderNo);

    int finishUserOrder(Long orderNo);

    OrderAndItemDTO getUserOrder(Long orderNo);
}
