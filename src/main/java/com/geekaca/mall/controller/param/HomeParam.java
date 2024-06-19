package com.geekaca.mall.controller.param;

import com.geekaca.mall.domain.Carousel;
import lombok.Data;

import java.util.List;

@Data
public class HomeParam {
    List<HomeGoodsInfo> newGoodses;
    List<HomeGoodsInfo> hotGoodses;
    List<HomeGoodsInfo> recommendGoodses;
    List<Carousel> carousels;
}
