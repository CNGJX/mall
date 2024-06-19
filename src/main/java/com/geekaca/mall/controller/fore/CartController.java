package com.geekaca.mall.controller.fore;

import cn.hutool.core.bean.BeanUtil;
import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.constants.MallConstants;
import com.geekaca.mall.controller.vo.GoodsDTO;
import com.geekaca.mall.controller.vo.OrderDTO;
import com.geekaca.mall.domain.Order;
import com.geekaca.mall.domain.ShoppingCartItem;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.exceptions.UserNotLoginException;
import com.geekaca.mall.service.CartService;
import com.geekaca.mall.service.OrderService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geekaca.mall.constants.MallConstants.CODE_USER_NOT_LOGIN;

@RestController
@RequestMapping("/api/v1")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @PostMapping("/shop-cart")
    public Result addToCart(@RequestBody @Valid ShoppingCartItem cartItem, HttpServletRequest req){
        String token = req.getHeader("token");
        if (token == null){
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        // 把获取的id转换为字符串
        String sid = claim.asString();
        if (sid == null) {
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        // 因为cartItem里的userId是长整型，所以把获取的id转换为Long类型
        // String --> Long
        cartItem.setUserId(Long.parseLong(sid));
        // 要拿到user_id ，需要接收token然后解析
        int added = cartService.addToCart(cartItem);
        if (added > 0){
            return ResultGenerator.genSuccessResult("加入购车成功");
        }
        return  ResultGenerator.genFailResult("加入购物车失败");
    }

    @ApiOperation(value = "购物车商品列表和总价格")
    @GetMapping("/shop-cart")
    public Result shopCart(HttpServletRequest req){
        /**
         * 根据用户的id 查询 该用户的购物车 中
         * 商品列表
         */
        String token = req.getHeader("token");
        if (token == null){
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        // 把获取的id转换为字符串
        String sid = claim.asString();
        if (sid == null) {
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Integer uid = Integer.parseInt(sid);
        List<GoodsDTO> goodsList = cartService.getGoodsList(uid);
        if (goodsList != null) {
            return ResultGenerator.genSuccessResult(goodsList);
        } else {
            return ResultGenerator.genFailResult("获取购物车商品失败");
        }
    }

    @DeleteMapping("/shop-cart/{cartItemId}")
    public Result deleteShopCartItem(@PathVariable("cartItemId") Integer cartItemId, HttpServletRequest request) {
        //保护校验，当前用户在删除的是自己的购物车
        String token = request.getHeader("token");
        if (token == null){
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        String sid = claim.asString();
        if (sid == null) {
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Integer uid = Integer.parseInt(sid);
        //判断是否是用户自己购物车里的商品
        boolean isUserHave = cartService.isUserCartItem(uid, cartItemId);
        if (!isUserHave){
            /**
             * 1，抛出自定义异常
             * 2，返回失败结果
             */
            Result rs = new Result();
            rs.setResultCode(MallConstants.CODE_NOT_USER_CART_ITEM);
            return rs;
        }
        int deleted = cartService.deleteCartItemById(cartItemId);
        if (deleted > 0){
            return ResultGenerator.genSuccessResult("删除成功");
        }else{
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @GetMapping("/shop-cart/page")
    @ApiOperation(value = "购物车列表(每页默认5条)", notes = "传参为页码")
    public Result cartItemList(Integer pageNumber, HttpServletRequest req) {
        //保护校验，当前用户在删除的是自己的购物车
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
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        PageResult pageResult = cartService.getCartItemList(uid, pageNumber, 5);
        return ResultGenerator.genSuccessResult(pageResult);
    }

    @GetMapping("/shop-cart/settle")
    public Result orderItemList(Integer[] cartItemIds, HttpServletRequest req){
        /**
         * 根据用户的id 查询 该用户的购物车 中
         * 商品列表
         */
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
        if (cartItemIds == null || cartItemIds.length == 0){
                    throw new MallException("参数异常, 购物车item不能为空");
                }
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setUserId(uid);
                orderDTO.setCartItemIds(cartItemIds);
                // Order order = orderService.createOrder(orderDTO);
        List<ShoppingCartItem> cartItemList = cartService.getOrderGoodsList(uid, cartItemIds);
        List<GoodsDTO> goodsDTOList = BeanUtil.copyToList(cartItemList, GoodsDTO.class);
        if (goodsDTOList != null) {
            return ResultGenerator.genSuccessResult(goodsDTOList);
        } else {
            return ResultGenerator.genFailResult("获取订单商品信息失败");
        }
    }

    @ApiOperation(value = "购物车商品数量修改")
    @PutMapping("/shop-cart")
    public Result shopCount(@RequestBody GoodsDTO goodsDTO, HttpServletRequest req){

        /**
         * 根据用户的id 查询 该用户的购物车 中
         * 商品列表
         */
        String token = req.getHeader("token");
        if (token == null){
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        // 把获取的id转换为字符串
        String sid = claim.asString();
        if (sid == null) {
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Integer uid = Integer.parseInt(sid);

        Long cartItemId = goodsDTO.getCartItemId();
        Integer goodsCount = goodsDTO.getGoodsCount();
        int i = cartService.updateGoodsCount(cartItemId, goodsCount);

        List<GoodsDTO> goodsList = cartService.getGoodsList(uid);

        if (i > 0 && goodsList!=null) {
            return ResultGenerator.genSuccessResult(goodsList);
        }else{
            return ResultGenerator.genFailResult("修改数量失败");
        }
    }
}
