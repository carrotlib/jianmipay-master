package com.carrotlib.jianmipay.mapper.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "goods_order")
public class GoodsOrder {
    /**
     * 商品订单ID
     */
    @Id
    @Column(name = "goods_order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String goodsOrderId;

    /**
     * 商品ID
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 金额,单位分
     */
    private Long amount;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 订单状态,订单生成(0),支付成功(1),处理完成(2),处理失败(-1)
     */
    private Byte status;

    /**
     * 支付订单号
     */
    @Column(name = "pay_order_id")
    private String payOrderId;

    /**
     * 渠道ID
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 支付渠道用户ID(微信openID或支付宝账号等第三方支付账号)
     */
    @Column(name = "channel_user_id")
    private String channelUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取商品订单ID
     *
     * @return goods_order_id - 商品订单ID
     */
    public String getGoodsOrderId() {
        return goodsOrderId;
    }

    /**
     * 设置商品订单ID
     *
     * @param goodsOrderId 商品订单ID
     */
    public void setGoodsOrderId(String goodsOrderId) {
        this.goodsOrderId = goodsOrderId;
    }

    /**
     * 获取商品ID
     *
     * @return goods_id - 商品ID
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品ID
     *
     * @param goodsId 商品ID
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取商品名称
     *
     * @return goods_name - 商品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置商品名称
     *
     * @param goodsName 商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取金额,单位分
     *
     * @return amount - 金额,单位分
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * 设置金额,单位分
     *
     * @param amount 金额,单位分
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取订单状态,订单生成(0),支付成功(1),处理完成(2),处理失败(-1)
     *
     * @return status - 订单状态,订单生成(0),支付成功(1),处理完成(2),处理失败(-1)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置订单状态,订单生成(0),支付成功(1),处理完成(2),处理失败(-1)
     *
     * @param status 订单状态,订单生成(0),支付成功(1),处理完成(2),处理失败(-1)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取支付订单号
     *
     * @return pay_order_id - 支付订单号
     */
    public String getPayOrderId() {
        return payOrderId;
    }

    /**
     * 设置支付订单号
     *
     * @param payOrderId 支付订单号
     */
    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    /**
     * 获取渠道ID
     *
     * @return channel_id - 渠道ID
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * 设置渠道ID
     *
     * @param channelId 渠道ID
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * 获取支付渠道用户ID(微信openID或支付宝账号等第三方支付账号)
     *
     * @return channel_user_id - 支付渠道用户ID(微信openID或支付宝账号等第三方支付账号)
     */
    public String getChannelUserId() {
        return channelUserId;
    }

    /**
     * 设置支付渠道用户ID(微信openID或支付宝账号等第三方支付账号)
     *
     * @param channelUserId 支付渠道用户ID(微信openID或支付宝账号等第三方支付账号)
     */
    public void setChannelUserId(String channelUserId) {
        this.channelUserId = channelUserId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}