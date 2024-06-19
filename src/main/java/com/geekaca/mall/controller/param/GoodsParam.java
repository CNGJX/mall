package com.geekaca.mall.controller.param;

import lombok.Data;

@Data
public class GoodsParam extends PageParam{
    private String goodsName;
    //状态，上架，下架
    private Integer goodsSellStatus;

    public GoodsParam() {
    }

    public GoodsParam(String goodsName, Integer goodsSellStatus, Integer pageNo, Integer pageSize) {
        this.goodsName = goodsName;
        this.goodsSellStatus = goodsSellStatus;
        this.setPageNO(pageNo);
        this.setPageSize(pageSize);
    }
}
