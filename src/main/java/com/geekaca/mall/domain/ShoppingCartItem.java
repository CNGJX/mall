package com.geekaca.mall.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName tb_newbee_mall_shopping_cart_item
 */
@Data
public class ShoppingCartItem implements Serializable {
    /**
     * 购物项主键id
     */
    private Long cartItemId;

    /**
     * 用户主键id
     */
    private Long userId;

    /**
     * 关联商品id
     */
    @NotNull(message = "商品id不能为空")
    private Long goodsId;

    /**
     * 数量(最大为5)
     */
    @NotNull(message = "商品数量不能为空")
    private Integer goodsCount;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最新修改时间
     */
    private Date updateTime;

    private String goodsName;
    private Integer sellingPrice;
    private String goodsCoverImg;

    private static final long serialVersionUID = 1L;

}