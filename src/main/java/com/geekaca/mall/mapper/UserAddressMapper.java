package com.geekaca.mall.mapper;

import com.geekaca.mall.domain.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author gjx
* @description 针对表【tb_newbee_mall_user_address(收货地址表)】的数据库操作Mapper
* @createDate 2024-01-22 17:31:25
* @Entity com.geekaca.mall.domain.UserAddress
*/
@Mapper
public interface UserAddressMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);

    List<UserAddress> selectAddressListByUserId(Integer userId);

    UserAddress selectDefaultAddressByUserId(Integer uid);

}
