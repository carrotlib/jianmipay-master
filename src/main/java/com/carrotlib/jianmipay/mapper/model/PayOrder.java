package com.carrotlib.jianmipay.mapper.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "pay_order")
public class PayOrder {
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
     * 商户订单号
     */
    @Column(name = "mch_order_no")
    private String mchOrderNo;

    /**
     * 渠道ID
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 支付金额,单位分
     */
    private Long amount;

    /**
     * 三位货币代码,人民币:cny
     */
    private String currency;

    /**
     * 支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成
     */
    private Byte status;

    /**
     * 客户端IP
     */
    @Column(name = "client_ip")
    private String clientIp;

    /**
     * 设备
     */
    private String device;

    /**
     * 商品标题
     */
    private String subject;

    /**
     * 商品描述信息
     */
    private String body;

    /**
     * 特定渠道发起时额外参数
     */
    private String extra;

    /**
     * 渠道商户ID
     */
    @Column(name = "channel_mch_id")
    private String channelMchId;

    /**
     * 渠道订单号
     */
    @Column(name = "channel_order_no")
    private String channelOrderNo;

    /**
     * 渠道支付错误码
     */
    @Column(name = "err_code")
    private String errCode;

    /**
     * 渠道支付错误描述
     */
    @Column(name = "err_msg")
    private String errMsg;

    /**
     * 扩展参数1
     */
    private String param1;

    /**
     * 扩展参数2
     */
    private String param2;

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
     * 最后一次通知时间
     */
    @Column(name = "last_notify_time")
    private Long lastNotifyTime;

    /**
     * 订单失效时间
     */
    @Column(name = "expire_time")
    private Long expireTime;

    /**
     * 订单支付成功时间
     */
    @Column(name = "pay_succ_time")
    private Long paySuccTime;

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
     * 获取支付金额,单位分
     *
     * @return amount - 支付金额,单位分
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * 设置支付金额,单位分
     *
     * @param amount 支付金额,单位分
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * 获取三位货币代码,人民币:cny
     *
     * @return currency - 三位货币代码,人民币:cny
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置三位货币代码,人民币:cny
     *
     * @param currency 三位货币代码,人民币:cny
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 获取支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成
     *
     * @return status - 支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成
     *
     * @param status 支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取客户端IP
     *
     * @return client_ip - 客户端IP
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * 设置客户端IP
     *
     * @param clientIp 客户端IP
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * 获取设备
     *
     * @return device - 设备
     */
    public String getDevice() {
        return device;
    }

    /**
     * 设置设备
     *
     * @param device 设备
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * 获取商品标题
     *
     * @return subject - 商品标题
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 设置商品标题
     *
     * @param subject 商品标题
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 获取商品描述信息
     *
     * @return body - 商品描述信息
     */
    public String getBody() {
        return body;
    }

    /**
     * 设置商品描述信息
     *
     * @param body 商品描述信息
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 获取特定渠道发起时额外参数
     *
     * @return extra - 特定渠道发起时额外参数
     */
    public String getExtra() {
        return extra;
    }

    /**
     * 设置特定渠道发起时额外参数
     *
     * @param extra 特定渠道发起时额外参数
     */
    public void setExtra(String extra) {
        this.extra = extra;
    }

    /**
     * 获取渠道商户ID
     *
     * @return channel_mch_id - 渠道商户ID
     */
    public String getChannelMchId() {
        return channelMchId;
    }

    /**
     * 设置渠道商户ID
     *
     * @param channelMchId 渠道商户ID
     */
    public void setChannelMchId(String channelMchId) {
        this.channelMchId = channelMchId;
    }

    /**
     * 获取渠道订单号
     *
     * @return channel_order_no - 渠道订单号
     */
    public String getChannelOrderNo() {
        return channelOrderNo;
    }

    /**
     * 设置渠道订单号
     *
     * @param channelOrderNo 渠道订单号
     */
    public void setChannelOrderNo(String channelOrderNo) {
        this.channelOrderNo = channelOrderNo;
    }

    /**
     * 获取渠道支付错误码
     *
     * @return err_code - 渠道支付错误码
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * 设置渠道支付错误码
     *
     * @param errCode 渠道支付错误码
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    /**
     * 获取渠道支付错误描述
     *
     * @return err_msg - 渠道支付错误描述
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * 设置渠道支付错误描述
     *
     * @param errMsg 渠道支付错误描述
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    /**
     * 获取扩展参数1
     *
     * @return param1 - 扩展参数1
     */
    public String getParam1() {
        return param1;
    }

    /**
     * 设置扩展参数1
     *
     * @param param1 扩展参数1
     */
    public void setParam1(String param1) {
        this.param1 = param1;
    }

    /**
     * 获取扩展参数2
     *
     * @return param2 - 扩展参数2
     */
    public String getParam2() {
        return param2;
    }

    /**
     * 设置扩展参数2
     *
     * @param param2 扩展参数2
     */
    public void setParam2(String param2) {
        this.param2 = param2;
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
     * 获取最后一次通知时间
     *
     * @return last_notify_time - 最后一次通知时间
     */
    public Long getLastNotifyTime() {
        return lastNotifyTime;
    }

    /**
     * 设置最后一次通知时间
     *
     * @param lastNotifyTime 最后一次通知时间
     */
    public void setLastNotifyTime(Long lastNotifyTime) {
        this.lastNotifyTime = lastNotifyTime;
    }

    /**
     * 获取订单失效时间
     *
     * @return expire_time - 订单失效时间
     */
    public Long getExpireTime() {
        return expireTime;
    }

    /**
     * 设置订单失效时间
     *
     * @param expireTime 订单失效时间
     */
    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 获取订单支付成功时间
     *
     * @return pay_succ_time - 订单支付成功时间
     */
    public Long getPaySuccTime() {
        return paySuccTime;
    }

    /**
     * 设置订单支付成功时间
     *
     * @param paySuccTime 订单支付成功时间
     */
    public void setPaySuccTime(Long paySuccTime) {
        this.paySuccTime = paySuccTime;
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