package com.carrotlib.jianmipay.mapper.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mch_notify")
public class MchNotify {
    /**
     * 订单ID
     */
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;

    /**
     * 商户ID
     */
    @Column(name = "mch_id")
    private String mchId;

    /**
     * 商户订单号
     */
    @Column(name = "mch_order_no")
    private String mchOrderNo;

    /**
     * 订单类型:1-支付,2-转账,3-退款
     */
    @Column(name = "order_type")
    private String orderType;

    /**
     * 通知地址
     */
    @Column(name = "notify_url")
    private String notifyUrl;

    /**
     * 通知次数
     */
    @Column(name = "notify_count")
    private Byte notifyCount;

    /**
     * 通知响应结果
     */
    private String result;

    /**
     * 通知状态,1-通知中,2-通知成功,3-通知失败
     */
    private Byte status;

    /**
     * 最后一次通知时间
     */
    @Column(name = "last_notify_time")
    private Date lastNotifyTime;

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
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取商户ID
     *
     * @return mch_id - 商户ID
     */
    public String getMchId() {
        return mchId;
    }

    /**
     * 设置商户ID
     *
     * @param mchId 商户ID
     */
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    /**
     * 获取商户订单号
     *
     * @return mch_order_no - 商户订单号
     */
    public String getMchOrderNo() {
        return mchOrderNo;
    }

    /**
     * 设置商户订单号
     *
     * @param mchOrderNo 商户订单号
     */
    public void setMchOrderNo(String mchOrderNo) {
        this.mchOrderNo = mchOrderNo;
    }

    /**
     * 获取订单类型:1-支付,2-转账,3-退款
     *
     * @return order_type - 订单类型:1-支付,2-转账,3-退款
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * 设置订单类型:1-支付,2-转账,3-退款
     *
     * @param orderType 订单类型:1-支付,2-转账,3-退款
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取通知地址
     *
     * @return notify_url - 通知地址
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * 设置通知地址
     *
     * @param notifyUrl 通知地址
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * 获取通知次数
     *
     * @return notify_count - 通知次数
     */
    public Byte getNotifyCount() {
        return notifyCount;
    }

    /**
     * 设置通知次数
     *
     * @param notifyCount 通知次数
     */
    public void setNotifyCount(Byte notifyCount) {
        this.notifyCount = notifyCount;
    }

    /**
     * 获取通知响应结果
     *
     * @return result - 通知响应结果
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置通知响应结果
     *
     * @param result 通知响应结果
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 获取通知状态,1-通知中,2-通知成功,3-通知失败
     *
     * @return status - 通知状态,1-通知中,2-通知成功,3-通知失败
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置通知状态,1-通知中,2-通知成功,3-通知失败
     *
     * @param status 通知状态,1-通知中,2-通知成功,3-通知失败
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取最后一次通知时间
     *
     * @return last_notify_time - 最后一次通知时间
     */
    public Date getLastNotifyTime() {
        return lastNotifyTime;
    }

    /**
     * 设置最后一次通知时间
     *
     * @param lastNotifyTime 最后一次通知时间
     */
    public void setLastNotifyTime(Date lastNotifyTime) {
        this.lastNotifyTime = lastNotifyTime;
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