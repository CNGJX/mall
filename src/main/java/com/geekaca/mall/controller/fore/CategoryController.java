package com.geekaca.mall.controller.fore;

import com.geekaca.mall.domain.GoodsCategory;
import com.geekaca.mall.service.CategoryService;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public Result getAllCategory(){
        List<GoodsCategory> allCategory = categoryService.getFirstCategory();
        if (allCategory.size() > 0){
            return ResultGenerator.genSuccessResult(allCategory);
        }
        return ResultGenerator.genFailResult("获取商品类型失败");
    }
}
