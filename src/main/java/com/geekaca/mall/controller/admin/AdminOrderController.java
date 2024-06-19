package com.geekaca.mall.controller.admin;

import com.geekaca.mall.controller.param.UserOrderParam;
import com.geekaca.mall.controller.vo.MallOrderDetailVO;
import com.geekaca.mall.service.OrderService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/manage-api/v1")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public Result orders(HttpServletRequest req,
                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                         @RequestParam(value = "orderStatus", required = false) Integer status,
                         @RequestParam(value = "orderNo", required = false) Integer orderNo ){
        if (pageNumber == null){
            pageNumber = 1;
        }
        Integer pageSize = 10;
        UserOrderParam userOrderParam = new UserOrderParam();
        userOrderParam.setOrderStatus(status);
        userOrderParam.setPageNum(pageNumber);
        userOrderParam.setPageSize(pageSize);
        PageResult pageResult = orderService.getOrderList(userOrderParam);
        return ResultGenerator.genSuccessResult(pageResult);
    }

    @GetMapping("/orders/{orderId}")
    @ApiOperation(value = "订单详情接口", notes = "传参为订单号")
    public Result<MallOrderDetailVO> orderDetailPage(@ApiParam(value = "订单号") @PathVariable("orderId") Integer orderId) {
        return ResultGenerator.genSuccessResult(orderService.getOrderDetailByOrderId(orderId));
    }

}
