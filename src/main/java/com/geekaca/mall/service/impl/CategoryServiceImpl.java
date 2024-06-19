package com.geekaca.mall.service.impl;

import com.geekaca.mall.controller.param.CategoryParam;
import com.geekaca.mall.domain.GoodsCategory;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.mapper.GoodsCategoryMapper;
import com.geekaca.mall.service.CategoryService;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private GoodsCategoryMapper categoryMapper;

    @Override
    public PageResult getFirst(CategoryParam param) {
        int pageNO =  param.getPageNumber();
        Integer start = (param.getPageNumber() - 1) * param.getPageSize();
        param.setPageNumber(start);
        List<GoodsCategory> first = categoryMapper.getFirst(param);
        if (first.size() == 0){
            throw new MallException("获取类型失败");
        }
        int firstCount = categoryMapper.getFirstCount();
        param.setPageNumber(pageNO);
        PageResult result = new PageResult(first,firstCount, param.getPageSize(), param.getPageNumber());
        return result;
    }

    @Override
    public List<GoodsCategory> getFirstCategory() {
        return categoryMapper.getFirstCategory();
    }
}
