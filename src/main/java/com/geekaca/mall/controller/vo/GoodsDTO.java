package com.geekaca.mall.controller.vo;

import lombok.Data;

/**
 * 商品信息载体
 */
@Data
public class GoodsDTO {
    private Long cartItemId;
    private Long goodsId;
    private String goodsName;
    private Integer sellingPrice;
    private String goodsCoverImg;
    private Integer goodsCount;
    private Integer userId;
    private Integer goodsSellStatus;
}
