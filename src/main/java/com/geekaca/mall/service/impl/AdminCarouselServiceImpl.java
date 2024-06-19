package com.geekaca.mall.service.impl;

import com.geekaca.mall.controller.param.PageParam;
import com.geekaca.mall.domain.Carousel;
import com.geekaca.mall.mapper.CarouselMapper;
import com.geekaca.mall.service.AdminCarouselService;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCarouselServiceImpl implements AdminCarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public PageResult getCarouselPage(PageParam pageParam) {
        pageParam.setStartIndex((pageParam.getPageNO() - 1) * pageParam.getPageSize());
        List<Carousel> carousels = carouselMapper.findCarouselList(pageParam);
        int total = carouselMapper.getTotalCarousels();
        PageResult pageResult = new PageResult(carousels, total, pageParam.getPageSize(), pageParam.getPageNO());
        return pageResult;
    }

    @Override
    public Boolean saveCarousel(Carousel carousel) {
        if (carouselMapper.insertSelective(carousel) > 0) {
            return  true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteBatch(Long[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除轮播图数据
        return carouselMapper.deleteBatch(ids) > 0;
    }
}
