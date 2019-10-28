package com.carrotlib.jianmipay.mapper.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "iap_receipt")
public class IapReceipt {
    /**
     * 支付订单号
     */
    @Id
    @Column(name = "pay_order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String payOrderId;

    /**
     * 商户ID
     */
    @Column(name = "mch_id")
    private String mchId;

    /**
     * IAP业务号
     */
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * 处理状态:0-未处理,1-处理成功,-1-处理失败
     */
    private Byte status;

    /**
     * 处理次数
     */
    @Column(name = "handle_count")
    private Byte handleCount;

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
     * 渠道ID
     */
    @Column(name = "receipt_data")
    private String receiptData;

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
     * 获取IAP业务号
     *
     * @return transaction_id - IAP业务号
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * 设置IAP业务号
     *
     * @param transactionId IAP业务号
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * 获取处理状态:0-未处理,1-处理成功,-1-处理失败
     *
     * @return status - 处理状态:0-未处理,1-处理成功,-1-处理失败
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置处理状态:0-未处理,1-处理成功,-1-处理失败
     *
     * @param status 处理状态:0-未处理,1-处理成功,-1-处理失败
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取处理次数
     *
     * @return handle_count - 处理次数
     */
    public Byte getHandleCount() {
        return handleCount;
    }

    /**
     * 设置处理次数
     *
     * @param handleCount 处理次数
     */
    public void setHandleCount(Byte handleCount) {
        this.handleCount = handleCount;
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

    /**
     * 获取渠道ID
     *
     * @return receipt_data - 渠道ID
     */
    public String getReceiptData() {
        return receiptData;
    }

    /**
     * 设置渠道ID
     *
     * @param receiptData 渠道ID
     */
    public void setReceiptData(String receiptData) {
        this.receiptData = receiptData;
    }
}