package com.carrotlib.jianmipay.service;

import com.carrotlib.jianmipay.consts.PayConstant;
import com.carrotlib.jianmipay.mapper.model.MchInfo;
import com.carrotlib.jianmipay.mapper.model.PayOrder;
import com.carrotlib.jianmipay.service.mq.Mq4PayNotify;
import com.carrotlib.jianmipay.utils.PayDigestUtil;
import com.carrotlib.jianmipay.utils.PayUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付通知处理基类
 *
 * @author fenghaitao on 2019/10/28
 */
@Component
public class Notify4BasePay extends BasePayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Notify4BasePay.class);

    @Autowired
    private Mq4PayNotify mq4PayNotify;

    /**
     *  创建相应URL
     * @param payOrder
     * @param backType  1：前台页面；2：后台接口
     * @return
     */
    public String createNotifyUrl(PayOrder payOrder, String backType) {
        String mchId = payOrder.getMchId();
        MchInfo mchInfo = super.baseSelectMchInfo(mchId);
        String resKey = mchInfo.getResKey();

        Map<String, Object> paramMap = new HashMap<>();
        //支付订单号
        paramMap.put("payOrderId", payOrder.getPayOrderId() == null ? "" : payOrder.getPayOrderId());
        //商户ID
        paramMap.put("mchId", payOrder.getMchId() == null ? "" : payOrder.getMchId());
        //商户订单号
        paramMap.put("mchOrderNo", payOrder.getMchOrderNo() == null ? "" : payOrder.getMchOrderNo());
        //渠道ID
        paramMap.put("channelId", payOrder.getChannelId() == null ? "" : payOrder.getChannelId());
        //支付金额
        paramMap.put("amount", payOrder.getAmount() == null ? "" : payOrder.getAmount());
        //货币类型
        paramMap.put("currency", payOrder.getCurrency() == null ? "" : payOrder.getCurrency());
        //支付状态
        paramMap.put("status", payOrder.getStatus() == null ? "" : payOrder.getStatus());
        //客户端IP
        paramMap.put("clientIp", payOrder.getClientIp() == null ? "" : payOrder.getClientIp());
        //设备
        paramMap.put("device", payOrder.getDevice() == null ? "" : payOrder.getDevice());
        //商品标题
        paramMap.put("subject", payOrder.getSubject() == null ? "" : payOrder.getSubject());
        //渠道订单号
        paramMap.put("channelOrderNo", payOrder.getChannelOrderNo() == null ? "" : payOrder.getChannelOrderNo());
        //扩展参数1
        paramMap.put("param1", payOrder.getParam1() == null ? "" : payOrder.getParam1());
        //扩展参数2
        paramMap.put("param2", payOrder.getParam2() == null ? "" : payOrder.getParam2());
        //支付成功时间
        paramMap.put("paySuccTime", payOrder.getPaySuccTime() == null ? "" : payOrder.getPaySuccTime());
        paramMap.put("backType", backType == null ? "" : backType);

        //先对原文签名
        String reqSign = PayDigestUtil.getSign(paramMap, resKey);
        //签名
        paramMap.put("sign", reqSign);

        //签名后再对中文参数编码
        try {
            paramMap.put("device", URLEncoder.encode(payOrder.getDevice() == null ? "" : payOrder.getDevice(), PayConstant.RESP_UTF8));
            paramMap.put("subject", URLEncoder.encode(payOrder.getSubject() == null ? "" : payOrder.getSubject(), PayConstant.RESP_UTF8));
            paramMap.put("param1", URLEncoder.encode(payOrder.getParam1() == null ? "" : payOrder.getParam1(), PayConstant.RESP_UTF8));
            paramMap.put("param2", URLEncoder.encode(payOrder.getParam2() == null ? "" : payOrder.getParam2(), PayConstant.RESP_UTF8));
        }catch (UnsupportedEncodingException e) {
            LOGGER.error("URL Encode exception.", e);
            return null;
        }

        String param = PayUtil.genUrlParams(paramMap);

        StringBuffer sb = new StringBuffer();
        sb.append(payOrder.getNotifyUrl()).append("?").append(param);

        return sb.toString();
    }

    public boolean doPage(PayOrder payOrder) {
        String redirectUrl = createNotifyUrl(payOrder, "1");
        LOGGER.debug("redirect to response url={}", redirectUrl);

        return true;
    }

    public void doNotify(PayOrder payOrder) {
        LOGGER.info(">>>>>> PAY开始回调通知业务系统 <<<<<<");
        // 发起后台通知业务系统
        JSONObject object = createNotifyInfo(payOrder);
        try {
            mq4PayNotify.send(object.toString());
        } catch (Exception e) {
            LOGGER.error("payOrderId={},sendMessage error.", payOrder != null ? payOrder.getPayOrderId() : "", e);
        }
        LOGGER.info(">>>>>> PAY回调通知业务系统完成 <<<<<<");
    }

    public JSONObject createNotifyInfo(PayOrder payOrder) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("method", "GET");
        jsonObject.put("url", createNotifyUrl(payOrder, "2"));
        jsonObject.put("orderId", payOrder.getPayOrderId());
        jsonObject.put("count", payOrder.getNotifyCount());
        jsonObject.put("createTime", System.currentTimeMillis());
        return jsonObject;
    }
}
