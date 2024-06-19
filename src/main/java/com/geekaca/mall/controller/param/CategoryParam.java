package com.geekaca.mall.controller.param;

import lombok.Data;

@Data
public class CategoryParam {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer categoryLevel;
    private Integer parentId;

    public CategoryParam() {
    }

    public CategoryParam(Integer pageNumber, Integer pageSize, Integer categoryLevel, Integer parentId) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.categoryLevel = categoryLevel;
        this.parentId = parentId;
    }
}
