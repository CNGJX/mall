package com.geekaca.mall.controller.admin;

import com.geekaca.mall.controller.param.CategoryParam;
import com.geekaca.mall.service.CategoryService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage-api/v1")
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    @ApiOperation(value = "类型查询接口")
    public Result userLogin(@RequestParam("pageNumber")Integer pageNumber,
                                @RequestParam("pageSize")Integer pageSize,
                                @RequestParam("categoryLevel")Integer categoryLevel,
                                @RequestParam("parentId")Integer parentId) {
        CategoryParam param = new CategoryParam(pageNumber,pageSize,categoryLevel,parentId);
        PageResult result = categoryService.getFirst(param);
        return ResultGenerator.genSuccessResult(result);
    }
}
