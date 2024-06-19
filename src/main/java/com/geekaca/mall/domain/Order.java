package com.geekaca.mall.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 订单整体信息
 * @TableName tb_newbee_mall_order
 */
@Data
public class Order implements Serializable {
    /**
     * 订单表主键id
     */
    private Long orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户主键id
     */
    private Long userId;

    /**
     * 订单总价
     */
    private Integer totalPrice;

    /**
     * 支付状态:0.未支付,1.支付成功,-1:支付失败
     */
    private Integer payStatus;

    /**
     * 0.无 1.支付宝支付 2.微信支付
     */
    private Integer payType;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;

    /**
     * 订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
     */
    private Integer orderStatus;

    /**
     * 订单body
     */
    private String extraInfo;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 最新修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}