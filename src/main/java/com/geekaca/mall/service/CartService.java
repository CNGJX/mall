package com.geekaca.mall.service;

import com.geekaca.mall.controller.vo.GoodsDTO;
import com.geekaca.mall.domain.ShoppingCartItem;
import com.geekaca.mall.utils.PageResult;

import java.util.List;

public interface CartService {

    int addToCart(ShoppingCartItem cartItem);

    List<GoodsDTO> getGoodsList(Integer uid);

    int deleteCartItemById(Integer cartItemId);

    boolean isUserCartItem(Integer uid, Integer cartItemId);

    int updateGoodsCount(Long cartItemId, Integer GoodsCount);

    int removeByItemIds(Integer[] ids);

    PageResult getCartItemList(Integer uid, Integer pageNo, Integer pageSize);

    List<ShoppingCartItem> getOrderGoodsList(Integer uid, Integer[] cartItemIds);
}
