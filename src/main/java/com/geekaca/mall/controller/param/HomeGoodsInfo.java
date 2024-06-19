package com.geekaca.mall.controller.param;

import lombok.Data;

@Data
public class HomeGoodsInfo {
    private Long goodsId;
    private String goodsName;
    private String goodsIntro;
    private String goodsCoverImg;
    private Integer sellingPrice;
}
