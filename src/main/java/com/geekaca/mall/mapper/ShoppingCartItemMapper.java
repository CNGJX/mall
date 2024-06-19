package com.geekaca.mall.mapper;

import com.geekaca.mall.controller.vo.GoodsDTO;
import com.geekaca.mall.domain.ShoppingCartItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author gjx
* @description 针对表【tb_newbee_mall_shopping_cart_item】的数据库操作Mapper
* @createDate 2024-01-16 15:48:54
* @Entity com.geekaca.mall.domain.ShoppingCartItem
*/
// 表明这个类是数据操作相关的类DAO
@Repository
public interface ShoppingCartItemMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ShoppingCartItem record);

    int insertSelective(ShoppingCartItem record);

    List<ShoppingCartItem> selectItemsByUid(Integer uid);

    int updateByPrimaryKeySelective(ShoppingCartItem record);

    int updateByPrimaryKey(ShoppingCartItem record);

    ShoppingCartItem selectItemByUidAndItemId(@Param("uid") Integer uid, @Param("cartItemId") Integer cartItemId);

    int updateGoodsCount(Long cartItemId, Integer GoodsCount);

    // 生成订单时删除购物车中的商品
    int deleteByIds(Integer[] ids);

    List<ShoppingCartItem> selectItemsByUidAndPage(@Param("uid") Integer uid, @Param("limit") int limit, @Param("pageSize") Integer pageSize);

    int selectItemsCountByUid(Integer uid);

    List<ShoppingCartItem> getOrderItemList(@Param("uid") Integer uid, @Param("cartItemIds") Integer[] cartItemIds);
}
