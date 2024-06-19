package com.geekaca.mall.service;

import com.geekaca.mall.controller.param.CategoryParam;
import com.geekaca.mall.domain.GoodsCategory;
import com.geekaca.mall.utils.PageResult;

import java.util.List;

public interface CategoryService {

    PageResult getFirst(CategoryParam param);

    List<GoodsCategory> getFirstCategory();
}
