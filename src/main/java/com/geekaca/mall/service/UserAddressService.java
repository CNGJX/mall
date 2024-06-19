package com.geekaca.mall.service;

import com.geekaca.mall.domain.UserAddress;

import java.util.List;

/**
 * 用户地址管理
 * tb_newbee_mall_user_address
 */
public interface UserAddressService {

    //获取用户地址
    List<UserAddress> getUserAddressList(Integer userId);

    UserAddress getUserAddressById(Integer addressId);

    int addUserAddress(UserAddress orderAddress);

    UserAddress getUserDefaultAddress(Integer uid);

    int updateUserAddress(UserAddress userAddress);

    int deleteUserAddress(Long id);
}
