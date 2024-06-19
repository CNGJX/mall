package com.geekaca.mall.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName tb_newbee_mall_order_item
 */
@Data
public class OrderItem implements Serializable {
    /**
     * 订单关联购物项主键id
     */
    private Long orderItemId;

    /**
     * 订单主键id
     */
    private Long orderId;

    /**
     * 关联商品id
     */
    private Long goodsId;

    /**
     * 下单时商品的名称(订单快照)
     */
    private String goodsName;

    /**
     * 下单时商品的主图(订单快照)
     */
    private String goodsCoverImg;

    /**
     * 下单时商品的价格(订单快照)
     */
    private Integer sellingPrice;

    /**
     * 数量(订单快照)
     */
    private Integer goodsCount;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OrderItem other = (OrderItem) that;
        return (this.getOrderItemId() == null ? other.getOrderItemId() == null : this.getOrderItemId().equals(other.getOrderItemId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
            && (this.getGoodsName() == null ? other.getGoodsName() == null : this.getGoodsName().equals(other.getGoodsName()))
            && (this.getGoodsCoverImg() == null ? other.getGoodsCoverImg() == null : this.getGoodsCoverImg().equals(other.getGoodsCoverImg()))
            && (this.getSellingPrice() == null ? other.getSellingPrice() == null : this.getSellingPrice().equals(other.getSellingPrice()))
            && (this.getGoodsCount() == null ? other.getGoodsCount() == null : this.getGoodsCount().equals(other.getGoodsCount()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderItemId() == null) ? 0 : getOrderItemId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getGoodsName() == null) ? 0 : getGoodsName().hashCode());
        result = prime * result + ((getGoodsCoverImg() == null) ? 0 : getGoodsCoverImg().hashCode());
        result = prime * result + ((getSellingPrice() == null) ? 0 : getSellingPrice().hashCode());
        result = prime * result + ((getGoodsCount() == null) ? 0 : getGoodsCount().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderItemId=").append(orderItemId);
        sb.append(", orderId=").append(orderId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsCoverImg=").append(goodsCoverImg);
        sb.append(", sellingPrice=").append(sellingPrice);
        sb.append(", goodsCount=").append(goodsCount);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}