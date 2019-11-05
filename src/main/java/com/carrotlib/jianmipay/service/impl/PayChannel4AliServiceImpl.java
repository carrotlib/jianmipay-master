package com.carrotlib.jianmipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.carrotlib.jianmipay.consts.PayConstant;
import com.carrotlib.jianmipay.consts.RetEnum;
import com.carrotlib.jianmipay.mapper.model.PayChannel;
import com.carrotlib.jianmipay.mapper.model.PayOrder;
import com.carrotlib.jianmipay.model.alipay.AlipayConfig;
import com.carrotlib.jianmipay.model.domain.BaseParam;
import com.carrotlib.jianmipay.service.BasePayService;
import com.carrotlib.jianmipay.service.PayChannel4AliService;
import com.carrotlib.jianmipay.utils.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author fenghaitao on 2019/10/28
 */
@Service
public class PayChannel4AliServiceImpl extends BasePayService implements PayChannel4AliService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayChannel4AliServiceImpl.class);

    @Autowired
    private AlipayConfig alipayConfig;

    @Override
    public Map doAliPayWapReq(String paramJson) {
        String logPrefix = "【支付宝WAP支付下单】";

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();
        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("{} fail, {} paramJson={}", logPrefix, RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        JSONObject payOrderObj = baseParam.isNullValue("payOrder") ? null : JSONObject.fromObject(bizParamMap.get("payOrder").toString());
        PayOrder payOrder = BeanConvertUtil.map2Bean(payOrderObj, PayOrder.class);
        if (ObjectValidUtil.isInvalid(payOrder)) {
            LOGGER.debug("{} fail, {} paramJson={}", logPrefix, RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        String payOrderId = payOrder.getPayOrderId();
        String mchId = payOrder.getMchId();
        String channelId = payOrder.getChannelId();
        PayChannel payChannel = super.baseSelectPayChannel(mchId, channelId);

        alipayConfig.init(payChannel.getParam());

        AlipayClient client =
                new DefaultAlipayClient(alipayConfig.getUrl(),
                        alipayConfig.getAppId(),
                        alipayConfig.getRsaPrivateKey(),
                        AlipayConfig.FORMAT,
                        AlipayConfig.CHARSET,
                        alipayConfig.getAlipayPublicKey(), AlipayConfig.SIGNTYPE);

        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        //封装支付请求信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(payOrderId);
        model.setSubject(payOrder.getSubject());
        model.setTotalAmount(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
        model.setBody(payOrder.getBody());
        model.setProductCode("QUICK_WAP_PAY");

        //获取objParams参数
        String objParams = payOrder.getExtra();
        if (StringUtils.isNotBlank(objParams)) {
            try {
                JSONObject objParamsJson = JSONObject.fromObject(objParams);
                if (StringUtils.isNotBlank(objParamsJson.getString("quit_url"))) {
                    model.setQuitUrl(objParamsJson.getString("quit_url"));
                }
            } catch (Exception e) {
                LOGGER.error("{} objParams参数格式错误!", logPrefix);
            }
        }

        alipayRequest.setBizModel(model);
        //设置异步通知地址
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
        //设置通知地址
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());

        String payUrl = null;

        try {
            payUrl = client.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            LOGGER.error("alipay page execute error!", e);
        }

        LOGGER.debug("{} gen payUrl: payUrl={}", logPrefix, payUrl);

        super.baseUpdateStatus4Ing(payOrderId, null);
        LOGGER.debug("{}生成请求支付宝数据,req={}", logPrefix, alipayRequest.getBizModel());
        LOGGER.debug("###### 商户统一下单处理完成 ######");
        Map<String, Object> map = PayUtil.genRetMap(PayConstant.RETURN_VALUE_SUCCESS, "", PayConstant.RETURN_VALUE_SUCCESS, null);
        map.put("payOrderId", payOrderId);
        map.put("payUrl", payUrl);
        return RPCUtil.createBizResult(baseParam, map);
    }

    @Override
    public Map doAliPayPcReq(String paramJson) {
        String logPrefix = "【支付宝PC支付下单】";

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();
        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("{} fail, {} paramJson={}", logPrefix, RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        JSONObject payOrderObj = baseParam.isNullValue("payOrder") ? null : JSONObject.fromObject(bizParamMap.get("payOrder").toString());
        PayOrder payOrder = BeanConvertUtil.map2Bean(payOrderObj, PayOrder.class);
        if (ObjectValidUtil.isInvalid(payOrder)) {
            LOGGER.debug("{} fail, {} paramJson={}", logPrefix, RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        String payOrderId = payOrder.getPayOrderId();
        String mchId = payOrder.getMchId();
        String channelId = payOrder.getChannelId();
        PayChannel payChannel = super.baseSelectPayChannel(mchId, channelId);

        alipayConfig.init(payChannel.getParam());

        AlipayClient client =
                new DefaultAlipayClient(alipayConfig.getUrl(),
                        alipayConfig.getAppId(),
                        alipayConfig.getRsaPrivateKey(),
                        AlipayConfig.FORMAT,
                        AlipayConfig.CHARSET,
                        alipayConfig.getAlipayPublicKey(), AlipayConfig.SIGNTYPE);

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        //封装支付请求信息
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(payOrderId);
        model.setSubject(payOrder.getSubject());
        model.setTotalAmount(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
        model.setBody(payOrder.getBody());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");

        //获取objParams参数
        String objParams = payOrder.getExtra();
        String qr_pay_mode = "2";
        String qrcode_width = "200";
        if (StringUtils.isNotBlank(objParams)) {
            try {
                JSONObject objParamsJson = JSONObject.fromObject(objParams);
                qr_pay_mode = ObjectUtils.toString(objParamsJson.getString("qr_pay_mode"), "2");
                qrcode_width = ObjectUtils.toString(objParamsJson.getString("qrcode_width"), "200");
            } catch (Exception e) {
                LOGGER.error("{} objParams参数格式错误!", logPrefix);
            }
        }

        model.setQrPayMode(qr_pay_mode);
        model.setQrcodeWidth(Long.parseLong(qrcode_width));
        alipayRequest.setBizModel(model);
        //设置异步通知地址
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
        //设置通知地址
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());

        String payUrl = null;

        try {
            payUrl = client.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            LOGGER.error("alipay page execute error!", e);
        }

        LOGGER.debug("{} gen payUrl: payUrl={}", logPrefix, payUrl);

        super.baseUpdateStatus4Ing(payOrderId, null);
        LOGGER.debug("{}生成请求支付宝数据,req={}", logPrefix, alipayRequest.getBizModel());
        LOGGER.debug("###### 商户统一下单处理完成 ######");
        Map<String, Object> map = PayUtil.genRetMap(PayConstant.RETURN_VALUE_SUCCESS, "", PayConstant.RETURN_VALUE_SUCCESS, null);
        map.put("payOrderId", payOrderId);
        map.put("payUrl", payUrl);
        return RPCUtil.createBizResult(baseParam, map);
    }

    @Override
    public Map doAliPayMobileReq(String paramJson) {
        String logPrefix = "【支付宝APP支付下单】";

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();
        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("{} fail, {} paramJson={}", logPrefix, RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        JSONObject payOrderObj = baseParam.isNullValue("payOrder") ? null : JSONObject.fromObject(bizParamMap.get("payOrder").toString());
        PayOrder payOrder = BeanConvertUtil.map2Bean(payOrderObj, PayOrder.class);
        if (ObjectValidUtil.isInvalid(payOrder)) {
            LOGGER.debug("{} fail, {} paramJson={}", logPrefix, RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        String payOrderId = payOrder.getPayOrderId();
        String mchId = payOrder.getMchId();
        String channelId = payOrder.getChannelId();
        PayChannel payChannel = super.baseSelectPayChannel(mchId, channelId);

        alipayConfig.init(payChannel.getParam());

        AlipayClient client =
                new DefaultAlipayClient(alipayConfig.getUrl(),
                        alipayConfig.getAppId(),
                        alipayConfig.getRsaPrivateKey(),
                        AlipayConfig.FORMAT,
                        AlipayConfig.CHARSET,
                        alipayConfig.getAlipayPublicKey(), AlipayConfig.SIGNTYPE);

        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();

        //封装支付请求信息
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(payOrderId);
        model.setSubject(payOrder.getSubject());
        model.setTotalAmount(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
        model.setBody(payOrder.getBody());
        model.setProductCode("QUICK_MSECURITY_PAY");

        alipayRequest.setBizModel(model);
        //设置异步通知地址
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
        //设置通知地址
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());

        String payUrl = null;

        try {
            payUrl = client.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            LOGGER.error("alipay page execute error!", e);
        }

        LOGGER.debug("{} gen payUrl: payUrl={}", logPrefix, payUrl);

        super.baseUpdateStatus4Ing(payOrderId, null);
        LOGGER.debug("{}生成请求支付宝数据,req={}", logPrefix, alipayRequest.getBizModel());
        LOGGER.debug("###### 商户统一下单处理完成 ######");
        Map<String, Object> map = PayUtil.genRetMap(PayConstant.RETURN_VALUE_SUCCESS, "", PayConstant.RETURN_VALUE_SUCCESS, null);
        map.put("payOrderId", payOrderId);
        map.put("payUrl", payUrl);
        return RPCUtil.createBizResult(baseParam, map);
    }

    @Override
    public Map doAliPayQrReq(String paramJson) {
        String logPrefix = "【支付宝当面付之扫码下单支付下单】";

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();
        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("{} fail, {} paramJson={}", logPrefix, RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        JSONObject payOrderObj = baseParam.isNullValue("payOrder") ? null : JSONObject.fromObject(bizParamMap.get("payOrder").toString());
        PayOrder payOrder = BeanConvertUtil.map2Bean(payOrderObj, PayOrder.class);
        if (ObjectValidUtil.isInvalid(payOrder)) {
            LOGGER.debug("{} fail, {} paramJson={}", logPrefix, RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        String payOrderId = payOrder.getPayOrderId();
        String mchId = payOrder.getMchId();
        String channelId = payOrder.getChannelId();
        PayChannel payChannel = super.baseSelectPayChannel(mchId, channelId);

        alipayConfig.init(payChannel.getParam());

        AlipayClient client =
                new DefaultAlipayClient(alipayConfig.getUrl(),
                        alipayConfig.getAppId(),
                        alipayConfig.getRsaPrivateKey(),
                        AlipayConfig.FORMAT,
                        AlipayConfig.CHARSET,
                        alipayConfig.getAlipayPublicKey(), AlipayConfig.SIGNTYPE);

        AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest();

        //封装支付请求信息
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(payOrderId);
        model.setSubject(payOrder.getSubject());
        model.setTotalAmount(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
        model.setBody(payOrder.getBody());

        // 获取objParams参数
        String objParams = payOrder.getExtra();
        if (StringUtils.isNotEmpty(objParams)) {
            try {
                JSONObject objParamsJson = JSONObject.fromObject(objParams);
                if(StringUtils.isNotBlank(objParamsJson.getString("discountable_amount"))) {
                    //可打折金额
                    model.setDiscountableAmount(objParamsJson.getString("discountable_amount"));
                }
                if(StringUtils.isNotBlank(objParamsJson.getString("undiscountable_amount"))) {
                    //不可打折金额
                    model.setUndiscountableAmount(objParamsJson.getString("undiscountable_amount"));
                }
            } catch (Exception e) {
                LOGGER.error("{} objParams参数格式错误！", logPrefix);
            }
        }

        alipayRequest.setBizModel(model);
        //设置异步通知地址
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
        //设置通知地址
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());

        String payUrl = null;

        try {
            payUrl = client.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            LOGGER.error("alipay page execute error!", e);
        }

        LOGGER.debug("{} gen payUrl: payUrl={}", logPrefix, payUrl);

        super.baseUpdateStatus4Ing(payOrderId, null);
        LOGGER.debug("{}生成请求支付宝数据,req={}", logPrefix, alipayRequest.getBizModel());
        LOGGER.debug("###### 商户统一下单处理完成 ######");
        Map<String, Object> map = PayUtil.genRetMap(PayConstant.RETURN_VALUE_SUCCESS, "", PayConstant.RETURN_VALUE_SUCCESS, null);
        map.put("payOrderId", payOrderId);
        map.put("payUrl", payUrl);
        return RPCUtil.createBizResult(baseParam, map);
    }
}
