package com.geekaca.mall.controller.fore;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.controller.param.OrderParam;
import com.geekaca.mall.controller.param.UserOrderParam;
import com.geekaca.mall.controller.vo.OrderAndItemDTO;
import com.geekaca.mall.controller.vo.OrderDTO;
import com.geekaca.mall.domain.Order;
import com.geekaca.mall.exceptions.UserNotLoginException;
import com.geekaca.mall.service.OrderService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.geekaca.mall.constants.MallConstants.CODE_USER_NOT_LOGIN;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     *
     * @param orderParam
     * @return
     */
    @PostMapping("/saveOrder")
    public Result saveOrder(@RequestBody OrderParam orderParam, HttpServletRequest req) {
        String token = req.getHeader("token");
        if (token == null) {
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        String sid = claim.asString();
        if (sid == null) {
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Integer uid = Integer.parseInt(sid);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(uid);
        orderDTO.setCartItemIds(orderParam.getCartItemIds());
        orderDTO.setAddressId(orderParam.getAddressId());
        Order order = orderService.createOrder(orderDTO);
        if (order != null) {
            Result rs = new Result();
            rs.setResultCode(ResultGenerator.RESULT_CODE_SUCCESS);
            rs.setData(order.getOrderNo());
            return rs;
        } else {
            return ResultGenerator.genFailResult("订单生成失败");
        }
    }

    /**
     * JS 可以调用，人工
     * java也可以调用
     * Spring RestTemplate
     * @param orderNo
     * @param payType
     * @return
     */
    @ApiOperation(value = "模拟支付成功回调的接口", notes = "传参为订单号和支付方式")
    @GetMapping("/paySuccess")
    public Result paySuccess(@ApiParam(value = "订单编号") @RequestParam("orderNo") String orderNo, @ApiParam(value = "支付方式") @RequestParam("payType") int payType){
        /**
         * 把对应的订单状态更新成已支付
         * 订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
         */
        Integer orderStatus = 1;
        int updated = orderService.updateOrderStatus(orderNo, orderStatus, payType);
        if (updated > 0){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("支付失败");
        }
    }

    /**
     * uid
     * 根据订单状态查询订单列表
     * @param pageNumber
     * @param status
     * @return
     */
    @GetMapping("/order")
    public Result orderList(HttpServletRequest req, @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                            @RequestParam(value = "status", required = false) Integer status){
        String token = req.getHeader("token");
        if (token == null) {
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        String sid = claim.asString();
        if (sid == null) {
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Integer uid = Integer.parseInt(sid);
        if (pageNumber == null){
            pageNumber = 1;
        }
        Integer pageSize = 10;
        UserOrderParam userOrderParam = new UserOrderParam();
        userOrderParam.setUid(uid);
        userOrderParam.setOrderStatus(status);
        userOrderParam.setPageNum(pageNumber);
        userOrderParam.setPageSize(pageSize);
        PageResult pageResult = orderService.getOrderList(userOrderParam);
        return ResultGenerator.genSuccessResult(pageResult);
    }

    @GetMapping("/order/{orderNo}")
    public Result getUserOrder(@PathVariable("orderNo") Long orderNo) {
        OrderAndItemDTO goodsInfo = orderService.getUserOrder(orderNo);
        if (goodsInfo == null) {
            return ResultGenerator.genFailResult("获取订单失败");
        }
        return ResultGenerator.genSuccessResult(goodsInfo);
    }

    @PutMapping("/order/{orderNo}/cancel")
    public Result cancelUserOrder(@PathVariable("orderNo") Long orderNo) {
        int deleted = orderService.cancelUserOrder(orderNo);
        if (deleted > 0){
            return ResultGenerator.genSuccessResult("删除成功");
        }
        return ResultGenerator.genFailResult("删除失败");
    }

    @PutMapping("/order/{orderNo}/finish")
    public Result finishUserOrder(@PathVariable("orderNo") Long orderNo) {
        int updated = orderService.finishUserOrder(orderNo);
        if (updated > 0){
            return ResultGenerator.genSuccessResult("交易成功");
        }
        return ResultGenerator.genFailResult("交易失败");
    }
}
