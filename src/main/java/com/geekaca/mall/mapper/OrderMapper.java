package com.geekaca.mall.mapper;

import com.geekaca.mall.controller.param.UserOrderParam;
import com.geekaca.mall.controller.vo.GoodsDTO;
import com.geekaca.mall.controller.vo.OrderAndItemDTO;
import com.geekaca.mall.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author gjx
* @description 针对表【tb_newbee_mall_order(订单整体信息)】的数据库操作Mapper
* @createDate 2024-01-21 23:32:24
* @Entity com.geekaca.mall.domain.Order
*/
@Mapper
public interface OrderMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    //根据userId ，cartItemIds[] 查询 对应每个商品  销售价格，名字，封面图片
    List<GoodsDTO> selectGoodsListByUidAndItemIds(@Param("uid") Integer uid, @Param("cartItemIds") Integer[] cartItemIds);

    /**
     * 根据订单编号，更新订单状态和支付类型
     * @param order
     * @return
     */
    int updateStatusByOrderNo(Order order);


    List<OrderAndItemDTO> selectByStatus(UserOrderParam userOrderParam);

    int selectCountByStatus(UserOrderParam userOrderParam);

    int deleteByOrderNo(Long orderNo);

    int finishUserOrder(Long orderNo);

    OrderAndItemDTO getOrderByOrderNo(Long orderNo);
}
