package com.geekaca.mall.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.geekaca.mall.controller.param.BatchIdParam;
import com.geekaca.mall.controller.param.CarouselAddParam;
import com.geekaca.mall.controller.param.PageParam;
import com.geekaca.mall.domain.Carousel;
import com.geekaca.mall.service.AdminCarouselService;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(value = "v1", tags = "8-1.后台管理系统轮播图模块接口")
@RequestMapping("/manage-api/v1")
public class AdminCarouselController {
    @Autowired
    AdminCarouselService adminCarouselService;

    /**
     * 列表
     */
    @RequestMapping(value = "/carousels", method = RequestMethod.GET)
    @ApiOperation(value = "轮播图列表", notes = "轮播图列表")
    public Result list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize) {
        if (pageNumber == null || pageNumber < 0){
            pageNumber = 1;
        }
        if (pageSize == null || pageSize < 0){
            pageSize = 10;
        }
        PageParam pageParam = new PageParam();
        pageParam.setPageNO(pageNumber);
        pageParam.setPageSize(pageSize);

        return ResultGenerator.genSuccessResult(adminCarouselService.getCarouselPage(pageParam));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/carousels", method = RequestMethod.POST)
    @ApiOperation(value = "新增轮播图", notes = "新增轮播图")
    public Result save(@RequestBody @Valid CarouselAddParam carouselAddParam) {
        Carousel carousel = new Carousel();
        BeanUtil.copyProperties(carouselAddParam, carousel);
        Boolean isFind = adminCarouselService.saveCarousel(carousel);
        if (isFind) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("添加失败");
        }
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/carousels", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除轮播图信息", notes = "批量删除轮播图信息")
    public Result delete(@RequestBody BatchIdParam batchIdParam) {
        if (batchIdParam == null || batchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (adminCarouselService.deleteBatch(batchIdParam.getIds())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}
