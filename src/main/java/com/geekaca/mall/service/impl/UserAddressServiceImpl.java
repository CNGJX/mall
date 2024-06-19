package com.geekaca.mall.service.impl;

import com.geekaca.mall.domain.UserAddress;
import com.geekaca.mall.mapper.UserAddressMapper;
import com.geekaca.mall.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 获取用户地址列表，供用户下单时候 选择收货地址
     * @param userId
     * @return
     */
    @Override
    public List<UserAddress> getUserAddressList(Integer userId) {
        List<UserAddress> addressList = userAddressMapper.selectAddressListByUserId(userId);
        return addressList;
    }

    @Override
    public UserAddress getUserAddressById(Integer addressId) {
        return userAddressMapper.selectByPrimaryKey(Long.valueOf(addressId));
    }

    @Override
    public int addUserAddress(UserAddress userAddress) {
        return userAddressMapper.insertSelective(userAddress);
    }

    @Override
    public UserAddress getUserDefaultAddress(Integer uid) {
        return userAddressMapper.selectDefaultAddressByUserId(uid);
    }

    @Override
    public int updateUserAddress(UserAddress userAddress) {
        return userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Override
    public int deleteUserAddress(Long id) {
        return userAddressMapper.deleteByPrimaryKey(id);
    }
}
