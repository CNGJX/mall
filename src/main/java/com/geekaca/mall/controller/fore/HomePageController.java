package com.geekaca.mall.controller.fore;

import com.geekaca.mall.controller.param.HomeGoodsInfo;
import com.geekaca.mall.controller.param.HomeParam;
import com.geekaca.mall.domain.Carousel;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.domain.Order;
import com.geekaca.mall.service.HomePageService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class HomePageController {
    @Autowired
    private HomePageService homePageService;

    @GetMapping("/index-infos")
    public Result getAll(){
        HomeParam state = new HomeParam();
        List<Carousel> swiperList = homePageService.goodsCarousels();
        List<HomeGoodsInfo> newGoodses = homePageService.goodsList(4);//魔法数字 最好定义常量
        List<HomeGoodsInfo> hotGoodses = homePageService.goodsList(3);
        List<HomeGoodsInfo> recommendGoodses = homePageService.goodsList(5);
        state.setNewGoodses(newGoodses);
        state.setHotGoodses(hotGoodses);
        state.setRecommendGoodses(recommendGoodses);
        state.setCarousels(swiperList);
        return ResultGenerator.genSuccessResult(state);
    }

    @GetMapping("/search")
    public Result getGoods(@RequestParam("pageNumber") Integer pageNum,
                           @RequestParam(value = "goodsCategoryId",required = false) Integer goodsCategoryId,
                           @RequestParam("keyword") String keyword,
                           @RequestParam("orderBy") String orderBy){

        PageResult<List<HomeGoodsInfo>> goodsList = homePageService.goodsListByKeyword(keyword,
                                                                    orderBy,pageNum,goodsCategoryId);
        return ResultGenerator.genSuccessResult(goodsList);
    }

    @GetMapping("goods/detail/{id}")
    public Result getGoodsById(@PathVariable("id") Long id){
        GoodsInfo goods = homePageService.goodsById(id);
        return ResultGenerator.genSuccessResult(goods);
    }
}
