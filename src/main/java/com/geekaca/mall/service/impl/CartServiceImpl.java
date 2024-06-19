package com.geekaca.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.geekaca.mall.controller.vo.GoodsDTO;
import com.geekaca.mall.domain.ShoppingCartItem;
import com.geekaca.mall.mapper.ShoppingCartItemMapper;
import com.geekaca.mall.service.CartService;
import com.geekaca.mall.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Resource
    private ShoppingCartItemMapper cartItemMapper;
    @Override
    public int addToCart(ShoppingCartItem cartItem) {

        return cartItemMapper.insertSelective(cartItem);
    }

    @Override
    public List<GoodsDTO> getGoodsList(Integer uid) {
        List<ShoppingCartItem> cartItemList = cartItemMapper.selectItemsByUid(uid);
        List<GoodsDTO> goodsDTOList = BeanUtil.copyToList(cartItemList, GoodsDTO.class);

        return goodsDTOList;
    }

    @Override
    public int deleteCartItemById(Integer cartItemId) {

        return cartItemMapper.deleteByPrimaryKey(cartItemId);
    }

    /**
     * 该商品是否是此uid用户购物车中的
     * @param uid
     * @param cartItemId
     * @return
     */
    @Override
    public boolean isUserCartItem(Integer uid, Integer cartItemId) {
        ShoppingCartItem item = cartItemMapper.selectItemByUidAndItemId(uid, cartItemId);
        /**
         * 如果能够查到item数据
         * 说明这个商品是uid 购物车中的
         */
        return item != null;
    }

    @Override
    public int updateGoodsCount(Long cartItemId, Integer GoodsCount) {
        return cartItemMapper.updateGoodsCount(cartItemId, GoodsCount);
    }

    @Override
    public int removeByItemIds(Integer[] ids) {
        return cartItemMapper.deleteByIds(ids);
    }

    @Override
    public PageResult getCartItemList(Integer uid, Integer pageNo, Integer pageSize) {
        /**
         * 带有分页
         * 1, 查询总记录条数
         * 2， 查询某一页的数据
         */
        int limit = (pageNo - 1) * pageSize;
        int totalCount = cartItemMapper.selectItemsCountByUid(uid);
        List<ShoppingCartItem> cartItemList = cartItemMapper.selectItemsByUidAndPage(uid, limit, pageSize);
        PageResult pageResult = new PageResult(cartItemList, totalCount, pageSize, pageNo);
        return pageResult;
    }

    @Override
    public List<ShoppingCartItem> getOrderGoodsList(Integer uid, Integer[] cartItemIds) {
        return cartItemMapper.getOrderItemList(uid, cartItemIds);
    }
}
