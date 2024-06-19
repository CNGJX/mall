package com.geekaca.mall;

import com.geekaca.mall.domain.GoodsCategory;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.mapper.GoodsCategoryMapper;
import com.geekaca.mall.mapper.GoodsInfoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MallApplicationTests {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Test
    // 获取商品信息
    public void testGetGoods() {
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(10003l);
        Assertions.assertNotNull(goodsInfo);
    }

    @Test
    // 获取商品类别
    public void testGetGoodsCategory() {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(15l);
        Assertions.assertNotNull(goodsCategory);
    }

}
