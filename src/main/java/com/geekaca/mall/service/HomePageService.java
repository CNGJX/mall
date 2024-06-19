package com.geekaca.mall.service;

import com.geekaca.mall.controller.param.GoodsParam;
import com.geekaca.mall.controller.param.HomeGoodsInfo;
import com.geekaca.mall.domain.Carousel;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.domain.IndexConfig;
import com.geekaca.mall.domain.Order;
import com.geekaca.mall.utils.PageResult;

import java.util.List;

public interface HomePageService {
    List<HomeGoodsInfo> goodsList(int type);

    List<Carousel> goodsCarousels();

    GoodsInfo goodsById(Long id);

    // 后台获取商品列表
    PageResult getGoodsList(GoodsParam goodsParam);

    // 后台增加商品
    int addGoods(GoodsInfo goodsInfo);

    int goodsCountByPrice(String keyword, Integer goodsCategoryId);

    PageResult<List<HomeGoodsInfo>> goodsListByKeyword(String keyword, String orderBy, Integer pageNum, Integer goodsCategoryId);

    PageResult getConfigsGoods(Integer pageNumber, Integer pageSize, Integer configType);

    IndexConfig getGoodsOld(Long id);

    int deleteConfigs(Long[] ids);

    int addConfigs(IndexConfig indexConfig);

    int updateConfigs(IndexConfig indexConfig);
}
