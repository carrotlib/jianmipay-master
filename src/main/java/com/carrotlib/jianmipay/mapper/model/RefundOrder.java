package com.carrotlib.jianmipay.mapper.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "refund_order")
public class RefundOrder {
    /**
     * 退款订单号
     */
    @Id
    @Column(name = "refund_order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String refundOrderId;

    /**
     * 支付订单号
     */
    @Column(name = "pay_order_id")
    private String payOrderId;

    /**
     * 渠道支付单号
     */
    @Column(name = "channel_pay_order_no")
    private String channelPayOrderNo;

    /**
     * 商户ID
     */
    @Column(name = "mch_id")
    private String mchId;

    /**
     * 商户退款单号
     */
    @Column(name = "mch_refund_no")
    private String mchRefundNo;

    /**
     * 渠道ID
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 支付金额,单位分
     */
    @Column(name = "pay_amount")
    private Long payAmount;

    /**
     * 退款金额,单位分
     */
    @Column(name = "refund_amount")
    private Long refundAmount;

    /**
     * 三位货币代码,人民币:cny
     */
    private String currency;

    /**
     * 退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成
     */
    private Byte status;

    /**
     * 退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败
     */
    private Byte result;

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
     * 备注
     */
    @Column(name = "remark_info")
    private String remarkInfo;

    /**
     * 渠道用户标识,如微信openId,支付宝账号
     */
    @Column(name = "channel_user")
    private String channelUser;

    /**
     * 用户姓名
     */
    @Column(name = "user_name")
    private String userName;

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
     * 渠道错误码
     */
    @Column(name = "channel_err_code")
    private String channelErrCode;

    /**
     * 渠道错误描述
     */
    @Column(name = "channel_err_msg")
    private String channelErrMsg;

    /**
     * 特定渠道发起时额外参数
     */
    private String extra;

    /**
     * 通知地址
     */
    @Column(name = "notify_url")
    private String notifyUrl;

    /**
     * 扩展参数1
     */
    private String param1;

    /**
     * 扩展参数2
     */
    private String param2;

    /**
     * 订单失效时间
     */
    @Column(name = "expire_time")
    private Date expireTime;

    /**
     * 订单退款成功时间
     */
    @Column(name = "refund_succ_time")
    private Date refundSuccTime;

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
     * 获取退款订单号
     *
     * @return refund_order_id - 退款订单号
     */
    public String getRefundOrderId() {
        return refundOrderId;
    }

    /**
     * 设置退款订单号
     *
     * @param refundOrderId 退款订单号
     */
    public void setRefundOrderId(String refundOrderId) {
        this.refundOrderId = refundOrderId;
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
     * 获取渠道支付单号
     *
     * @return channel_pay_order_no - 渠道支付单号
     */
    public String getChannelPayOrderNo() {
        return channelPayOrderNo;
    }

    /**
     * 设置渠道支付单号
     *
     * @param channelPayOrderNo 渠道支付单号
     */
    public void setChannelPayOrderNo(String channelPayOrderNo) {
        this.channelPayOrderNo = channelPayOrderNo;
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
     * 获取商户退款单号
     *
     * @return mch_refund_no - 商户退款单号
     */
    public String getMchRefundNo() {
        return mchRefundNo;
    }

    /**
     * 设置商户退款单号
     *
     * @param mchRefundNo 商户退款单号
     */
    public void setMchRefundNo(String mchRefundNo) {
        this.mchRefundNo = mchRefundNo;
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
     * @return pay_amount - 支付金额,单位分
     */
    public Long getPayAmount() {
        return payAmount;
    }

    /**
     * 设置支付金额,单位分
     *
     * @param payAmount 支付金额,单位分
     */
    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 获取退款金额,单位分
     *
     * @return refund_amount - 退款金额,单位分
     */
    public Long getRefundAmount() {
        return refundAmount;
    }

    /**
     * 设置退款金额,单位分
     *
     * @param refundAmount 退款金额,单位分
     */
    public void setRefundAmount(Long refundAmount) {
        this.refundAmount = refundAmount;
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
     * 获取退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成
     *
     * @return status - 退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成
     *
     * @param status 退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败
     *
     * @return result - 退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败
     */
    public Byte getResult() {
        return result;
    }

    /**
     * 设置退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败
     *
     * @param result 退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败
     */
    public void setResult(Byte result) {
        this.result = result;
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
     * 获取备注
     *
     * @return remark_info - 备注
     */
    public String getRemarkInfo() {
        return remarkInfo;
    }

    /**
     * 设置备注
     *
     * @param remarkInfo 备注
     */
    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    /**
     * 获取渠道用户标识,如微信openId,支付宝账号
     *
     * @return channel_user - 渠道用户标识,如微信openId,支付宝账号
     */
    public String getChannelUser() {
        return channelUser;
    }

    /**
     * 设置渠道用户标识,如微信openId,支付宝账号
     *
     * @param channelUser 渠道用户标识,如微信openId,支付宝账号
     */
    public void setChannelUser(String channelUser) {
        this.channelUser = channelUser;
    }

    /**
     * 获取用户姓名
     *
     * @return user_name - 用户姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户姓名
     *
     * @param userName 用户姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * 获取渠道错误码
     *
     * @return channel_err_code - 渠道错误码
     */
    public String getChannelErrCode() {
        return channelErrCode;
    }

    /**
     * 设置渠道错误码
     *
     * @param channelErrCode 渠道错误码
     */
    public void setChannelErrCode(String channelErrCode) {
        this.channelErrCode = channelErrCode;
    }

    /**
     * 获取渠道错误描述
     *
     * @return channel_err_msg - 渠道错误描述
     */
    public String getChannelErrMsg() {
        return channelErrMsg;
    }

    /**
     * 设置渠道错误描述
     *
     * @param channelErrMsg 渠道错误描述
     */
    public void setChannelErrMsg(String channelErrMsg) {
        this.channelErrMsg = channelErrMsg;
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
     * 获取订单失效时间
     *
     * @return expire_time - 订单失效时间
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * 设置订单失效时间
     *
     * @param expireTime 订单失效时间
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 获取订单退款成功时间
     *
     * @return refund_succ_time - 订单退款成功时间
     */
    public Date getRefundSuccTime() {
        return refundSuccTime;
    }

    /**
     * 设置订单退款成功时间
     *
     * @param refundSuccTime 订单退款成功时间
     */
    public void setRefundSuccTime(Date refundSuccTime) {
        this.refundSuccTime = refundSuccTime;
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