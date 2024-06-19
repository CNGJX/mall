package com.geekaca.mall.service;

import com.geekaca.mall.controller.param.PageParam;
import com.geekaca.mall.domain.Carousel;
import com.geekaca.mall.utils.PageResult;

import java.util.List;

public interface AdminCarouselService {

    PageResult getCarouselPage(PageParam pageParam);

    Boolean saveCarousel(Carousel carousel);

    Boolean deleteBatch(Long[] ids);
}
