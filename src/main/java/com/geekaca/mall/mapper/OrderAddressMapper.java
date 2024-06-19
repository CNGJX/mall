package com.geekaca.mall.mapper;

import com.geekaca.mall.domain.OrderAddress;
import org.apache.ibatis.annotations.Mapper;

/**
* @author gjx
* @description 针对表【tb_newbee_mall_order_address(订单收货地址关联表)】的数据库操作Mapper
* @createDate 2024-01-21 23:32:24
* @Entity com.geekaca.mall.domain.OrderAddress
*/
@Mapper
public interface OrderAddressMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OrderAddress record);

    int insertSelective(OrderAddress record);

    OrderAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderAddress record);

    int updateByPrimaryKey(OrderAddress record);

}
