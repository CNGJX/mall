package com.geekaca.mall.controller.param;

import lombok.Data;

@Data
public class PageParam {
    public Integer pageNO;
    public Integer pageSize;
    // 起始索引 limit的第一个参数
    private Integer startIndex;
}
