package com.geekaca.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.geekaca.mall.constants.MallConstants;
import com.geekaca.mall.controller.param.UserOrderParam;
import com.geekaca.mall.controller.vo.*;
import com.geekaca.mall.domain.Order;
import com.geekaca.mall.domain.OrderAddress;
import com.geekaca.mall.domain.OrderItem;
import com.geekaca.mall.domain.UserAddress;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.mapper.*;
import com.geekaca.mall.service.OrderService;
import com.geekaca.mall.utils.NumberUtil;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.geekaca.mall.constants.MallConstants.ORDER_STATUS_MAP;
import static com.geekaca.mall.constants.MallConstants.PAY_STATUS_MAP;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ShoppingCartItemMapper cartItemMapper;
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private OrderAddressMapper orderAddressMapper;

    /**
     * 生成订单
     *
     * order表
     * order_item
     * order_address
     * @param orderDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Order createOrder(OrderDTO orderDTO) {
        Long generateNo = NumberUtil.generateId();

        /**
         * 需要计算商品总价格
         * 需要查询DB
         * 根据userId和 购物车itemId 查询  goods_id
         * 根据goods_id 查询  goods_name, selling_price, goods_cover_img
         */
        List<GoodsDTO> goodsDTOList = orderMapper.selectGoodsListByUidAndItemIds(orderDTO.getUserId(), orderDTO.getCartItemIds());
        /**
         * 判断是否包含 已经下架的商品
         */
        // 计算总价
        Integer totalPrice = 0;
        for (int i = 0; i < goodsDTOList.size(); i++) {
            GoodsDTO goodsDTO = goodsDTOList.get(i);
            totalPrice += (goodsDTO.getSellingPrice() * goodsDTO.getGoodsCount());
            if (goodsDTO.getGoodsSellStatus() == MallConstants.GOODS_SELLING_STATUS_OFF){
                throw new MallException("订单包含已经下架的商品: " +goodsDTO.getGoodsName() + "，无法结算 "  );
            }
        }
        // 保存进订单表 tb_newbee_mall_order
        Order order = new Order();
        order.setUserId(Long.valueOf(orderDTO.getUserId()));
        order.setTotalPrice(totalPrice);
        order.setOrderNo(String.valueOf(generateNo));
        int inserted = orderMapper.insert(order);
        if (inserted == 0){
            throw new MallException("订单增加失败");
        }
        /**
         * order item
         * tb_newbee_mall_order_item
         * 插入多条数据
         */
        List<OrderItem> orderItemList = BeanUtil.copyToList(goodsDTOList, OrderItem.class);
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem item = orderItemList.get(i);
            item.setOrderId(order.getOrderId());
        }
        int insertBatched = orderItemMapper.insertBatchItems(orderItemList);
        if (insertBatched == 0){
            throw new MallException("订单商品列表增加失败");
        }

        /**
         * 增加订单地址
         * 手头有的： addressId
         * 需要
         */
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(Long.valueOf(orderDTO.getAddressId()));
        OrderAddress orderAddr  = BeanUtil.copyProperties(userAddress, OrderAddress.class);
        orderAddr.setOrderId(order.getOrderId());
        int addrInserted = orderAddressMapper.insertSelective(orderAddr);
        if (addrInserted == 0){
            throw new MallException("订单地址增加失败");
        }

        /**
         * 把对应的商品从购物车中删除
         *
         */
        int deleted = cartItemMapper.deleteByIds(orderDTO.getCartItemIds());
        if (deleted == 0){
            throw new MallException("删除购物车商品失败");
        }
        return order;
    }

    @Override
    public Order findById(Integer orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public int updateOrderStatus(String orderNo, Integer orderStatus, int payType) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setOrderStatus(orderStatus);
        order.setPayType(payType);
        order.setPayStatus(MallConstants.ORDER_PAYED);
        order.setUpdateTime(new Date());
        order.setPayTime(new Date());
        return orderMapper.updateStatusByOrderNo(order);
    }

    @Override
    public PageResult getOrderList(UserOrderParam userOrderParam) {
        int limit = (userOrderParam.getPageNum() - 1) * userOrderParam.getPageSize();
        Integer pageNO = userOrderParam.getPageNum();
        userOrderParam.setPageNum(limit);
        /**
         * 分页查询
         * 1，总数量
         * 2，这一页数据
         */
        List<OrderAndItemDTO> orderAndItemDTOS = orderMapper.selectByStatus(userOrderParam);
        if (orderAndItemDTOS == null){
            orderAndItemDTOS = Collections.emptyList();
        }
        // 根据支付状态码，返回给前端支付状态信息
        for (int i = 0; i < orderAndItemDTOS.size(); i++){
            OrderAndItemDTO orDTO = orderAndItemDTOS.get(i);
            Integer orderStatus = orDTO.getOrderStatus();
            String str = ORDER_STATUS_MAP.get(orderStatus);
            orDTO.setOrderStatusString(str);
        }
        int count = orderMapper.selectCountByStatus(userOrderParam);
        PageResult pageResult = new PageResult(orderAndItemDTOS, count, userOrderParam.getPageSize(), pageNO);
        return pageResult;
    }

    @Override
    public MallOrderDetailVO getOrderDetailByOrderId(Integer orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new MallException("获取订单详情失败");
        }
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(order.getOrderId().intValue());
        //获取订单内商品数据
        if (!CollectionUtils.isEmpty(orderItems)) {
            List<MallOrderItemVO> newBeeMallOrderItemVOS = BeanUtil.copyToList(orderItems, MallOrderItemVO.class);
            MallOrderDetailVO mallOrderDetailVO = new MallOrderDetailVO();
            BeanUtil.copyProperties(order, mallOrderDetailVO);
            Integer orderStatus = mallOrderDetailVO.getOrderStatus();
            String str = ORDER_STATUS_MAP.get(orderStatus);
            mallOrderDetailVO.setOrderStatusString(str);
            Integer payStatus = mallOrderDetailVO.getPayStatus();
            String str1 = PAY_STATUS_MAP.get(payStatus);
            mallOrderDetailVO.setPayTypeString(str1);
            mallOrderDetailVO.setNewBeeMallOrderItemVOS(newBeeMallOrderItemVOS);
            return mallOrderDetailVO;
        } else {
            throw new MallException("获取商品信息失败");
        }
    }

    @Override
    public int cancelUserOrder(Long orderNo) {
        return orderMapper.deleteByOrderNo(orderNo);
    }

    @Override
    public int finishUserOrder(Long orderNo) {
        return orderMapper.finishUserOrder(orderNo);
    }

    @Override
    public OrderAndItemDTO getUserOrder(Long orderNo) {
        OrderAndItemDTO order = orderMapper.getOrderByOrderNo(orderNo);
        order.setOrderStatusString(ORDER_STATUS_MAP.get(order.getOrderStatus()));
        return order;
    }
}
