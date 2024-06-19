package com.geekaca.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.geekaca.mall.controller.param.GoodsParam;
import com.geekaca.mall.controller.param.HomeGoodsInfo;
import com.geekaca.mall.domain.Carousel;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.domain.IndexConfig;
import com.geekaca.mall.domain.Order;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.mapper.CarouselMapper;
import com.geekaca.mall.mapper.GoodsInfoMapper;
import com.geekaca.mall.mapper.IndexConfigMapper;
import com.geekaca.mall.service.HomePageService;
import com.geekaca.mall.utils.Code;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomePageServiceImpl implements HomePageService {
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private CarouselMapper carouselMapper;
    @Autowired
    private IndexConfigMapper configMapper;

    @Override
    public List<HomeGoodsInfo> goodsList(int type) {
        return goodsInfoMapper.getAllGoods(type,10);
    }

    @Override
    public List<Carousel> goodsCarousels() {
        return carouselMapper.getGoodsCarousel();
    }

    @Override
    public GoodsInfo goodsById(Long id) {
        GoodsInfo goodsById = goodsInfoMapper.getGoodsById(id);
        String goodsCarousel = goodsById.getGoodsCarousel();
        goodsById.getGoodsCarouselList().add(goodsCarousel);
        return goodsById;
    }

    @Override
    public PageResult getGoodsList(GoodsParam goodsParam) {

        int count = goodsInfoMapper.selectGoodsCountByCondition(goodsParam);
        if (count > 0){
            int pageNO =  goodsParam.getPageNO();
            goodsParam.setPageNO((goodsParam.getPageNO() - 1) * goodsParam.getPageSize());
            List<GoodsInfo> goodsInfoList = goodsInfoMapper.selectGoodsListByCondition(goodsParam);
            goodsParam.setPageNO(pageNO);
            PageResult pageResult = new PageResult(goodsInfoList, count, goodsParam.getPageSize(), goodsParam.getPageNO());
            return pageResult;
        }
        return null;
    }

    @Override
    public int addGoods(GoodsInfo goodsInfo) {
        return goodsInfoMapper.insertSelective(goodsInfo);
    }

    @Override
    public int goodsCountByPrice(String keyword, Integer goodsCategoryId) {
        return goodsInfoMapper.goodsCountByPrice(keyword,goodsCategoryId);
    }

    @Override
    public PageResult<List<HomeGoodsInfo>> goodsListByKeyword(String keyword, String orderBy, Integer pageNum, Integer goodsCategoryId) {
        Integer pageNumber = (pageNum - 1) * 10;
        List<GoodsInfo> goodsList1 =goodsInfoMapper.goodsListByKeyword(keyword, orderBy, pageNumber, goodsCategoryId, 10);
        List<HomeGoodsInfo> goodsList2 = BeanUtil.copyToList(goodsList1,HomeGoodsInfo.class);
        int count = goodsInfoMapper.goodsCountByKeyword(keyword, orderBy, goodsCategoryId);
        PageResult<List<HomeGoodsInfo>> result = new PageResult(goodsList2,count,10,pageNum);
        return result;
    }

    @Override
    public PageResult getConfigsGoods(Integer pageNumber, Integer pageSize, Integer configType) {
        Integer start = (pageNumber - 1) * pageSize;
        List<IndexConfig> indexConfig = configMapper.getConfigsGoods(configType,start,pageSize);
        if (indexConfig.size() == 0){
            throw new MallException("获取轮播图数据异常");
        }
        int count = configMapper.getConfigsGoodsCount(configType);
        if (count == 0){
            throw new MallException("获取轮播图数量异常");
        }
        PageResult result = new PageResult(indexConfig,count,pageSize,pageNumber);
        return result;
    }

    @Override
    public IndexConfig getGoodsOld(Long id) {
        return configMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteConfigs(Long[] ids) {
        return configMapper.deleteConfigs(ids);
    }

    @Override
    public int addConfigs(IndexConfig indexConfig) {
        return configMapper.insertSelective(indexConfig);
    }

    @Override
    public int updateConfigs(IndexConfig indexConfig) {
        return configMapper.updateByPrimaryKeySelective(indexConfig);
    }
}
