package com.carrotlib.jianmipay.service.impl;

import com.carrotlib.jianmipay.consts.PayConstant;
import com.carrotlib.jianmipay.consts.RetEnum;
import com.carrotlib.jianmipay.mapper.model.PayChannel;
import com.carrotlib.jianmipay.mapper.model.PayOrder;
import com.carrotlib.jianmipay.model.domain.BaseParam;
import com.carrotlib.jianmipay.model.wechat.WxPayProperties;
import com.carrotlib.jianmipay.model.wechat.WxPayUtil;
import com.carrotlib.jianmipay.service.BasePayService;
import com.carrotlib.jianmipay.service.PayChannel4WxService;
import com.carrotlib.jianmipay.utils.BeanConvertUtil;
import com.carrotlib.jianmipay.utils.GsonUtil;
import com.carrotlib.jianmipay.utils.ObjectValidUtil;
import com.carrotlib.jianmipay.utils.RPCUtil;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.binarywang.wxpay.util.SignUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付渠道接口
 * @author fenghaitao on 2019/10/28
 */
@Service
public class PayChannel4WxServiceImpl extends BasePayService implements PayChannel4WxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayChannel4WxServiceImpl.class);

    @Resource
    private WxPayProperties wxPayProperties;

    public Map<String, Object> doWxPayReq(String paramJson) {

        String logPrefix = "[微信支付统一下单]";

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();

        try {
            if (ObjectValidUtil.isInvalid(bizParamMap)) {
                LOGGER.debug("{} fail, {} jsonParam={}", logPrefix, RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
                return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
            }

            JSONObject payOrderObj = baseParam.isNullValue("payOrder") ? null : JSONObject.fromObject(bizParamMap.get("payOrder").toString());
            String tradeType = baseParam.isNullValue("tradeType") ? null : bizParamMap.get("tradeType").toString();
            PayOrder payOrder = BeanConvertUtil.map2Bean(payOrderObj, PayOrder.class);
            if (ObjectValidUtil.isInvalid(payOrder, tradeType)) {
                LOGGER.debug("{} fail, {} jsonParam={}", logPrefix, RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
                return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
            }

            String mchId = payOrder.getMchId();
            String channelId = payOrder.getChannelId();
            PayChannel payChannel = super.baseSelectPayChannel(mchId, channelId);

            WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(payChannel.getParam(), tradeType, wxPayProperties.getCertRootPath(), wxPayProperties.getNotifyUrl());

            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);

            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = buildUnifiedOrderRequest(payOrder, wxPayConfig);

            String payOrderId = payOrder.getPayOrderId();

            WxPayUnifiedOrderResult wxPayUnifiedOrderResult;

            try {
                wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
                LOGGER.debug("{} >>> success", logPrefix);
                Map<String, Object> map = new HashMap<>();
                map.put("payOrderId", payOrderId);
                map.put("prepayId", wxPayUnifiedOrderResult.getPrepayId());
                int result = super.baseUpdateStatus4Ing(payOrderId, wxPayUnifiedOrderResult.getPrepayId());
                LOGGER.info("更新第三方支付订单号:payOrderId={},prepayId={},result={}", payOrderId, wxPayUnifiedOrderResult.getPrepayId(), result);

                switch (tradeType) {
                    case PayConstant.WxConstant.TRADE_TYPE_NATIVE : {
                        //二维码支付链接
                        map.put("codeUrl", wxPayUnifiedOrderResult.getCodeURL());
                        break;
                    }
                    case PayConstant.WxConstant.TRADE_TYPE_APP : {
                        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                        String nonceStr = String.valueOf(System.currentTimeMillis());
                        //APP支付绑定的是微信开放平台上的帐号，APPID为开放平台上绑定app后发放的参数
                        String appId = wxPayConfig.getAppId();

                        //此map用于参与调起sdk支付的二次签名，格式全小写，timestamp只能是十位，格式固定，切勿修改
                        Map<String, String> configMap = new HashMap<>();
                        String partnerId = wxPayConfig.getMchId();
                        configMap.put("prepayid", wxPayUnifiedOrderResult.getPrepayId());
                        configMap.put("partnerid", partnerId);
                        configMap.put("timestamp", timestamp);
                        String packageValue = "Sign=WxPay";
                        configMap.put("package", packageValue);
                        configMap.put("noncestr", nonceStr);
                        configMap.put("appid", appId);

                        //此map用户客户端与微信服务器交互
                        Map<String, String> payInfo = new HashMap<>();
                        payInfo.put("sign", SignUtils.createSign(configMap, wxPayConfig.getMchKey()));
                        payInfo.put("prepayid", wxPayUnifiedOrderResult.getPrepayId());
                        payInfo.put("parterid", partnerId);
                        payInfo.put("appid", appId);
                        payInfo.put("package", packageValue);
                        payInfo.put("timestamp", timestamp);
                        payInfo.put("noncestr", nonceStr);

                        map.put("payParams", payInfo);
                        break;
                    }
                    case PayConstant.WxConstant.TRADE_TYPE_JSPAI : {
                        Map<String, String> payInfo = new HashMap<>();
                        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                        String nonceStr = String.valueOf(System.currentTimeMillis());
                        payInfo.put("appId", wxPayUnifiedOrderResult.getAppid());
                        // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                        payInfo.put("timeStamp", timestamp);
                        payInfo.put("nonceStr", nonceStr);
                        payInfo.put("package", "prepay_id=" + wxPayUnifiedOrderResult.getPrepayId());
                        payInfo.put("signType", WxPayConstants.SignType.MD5);
                        payInfo.put("paySign", SignUtils.createSign(payInfo, wxPayConfig.getMchKey()));

                        map.put("payParams", payInfo);
                        break;
                    }
                    case PayConstant.WxConstant.TRADE_TYPE_MWEB : {
                        // h5支付链接地址
                        map.put("playUrl", wxPayUnifiedOrderResult.getMwebUrl());
                    }
                }
                return  RPCUtil.createBizResult(baseParam, map);
            } catch (WxPayException e) {
                LOGGER.error("下单失败", e);
                //出现业务错误
                LOGGER.debug("{}下单返回失败", logPrefix);
                LOGGER.debug("err_code:{}", e.getErrCode());
                LOGGER.debug("err_code_des:{}", e.getErrCodeDes());

                return RPCUtil.createFailResult(baseParam, RetEnum.RET_BIZ_WX_PAY_CREATE_FAIL);
            }
        } catch (Exception e) {
            LOGGER.error("微信支付统一下单异常", e);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_BIZ_WX_PAY_CREATE_FAIL);
        }
    }


    public WxPayUnifiedOrderRequest buildUnifiedOrderRequest(PayOrder payOrder, WxPayConfig wxPayConfig) {
        String tradeType = wxPayConfig.getTradeType();
        String payOrderId = payOrder.getPayOrderId();
        //支付金额，单位分
        Integer totalFee = payOrder.getAmount().intValue();
        String deviceInfo = payOrder.getDevice();
        String body = payOrder.getBody();
        String detail = null;
        String attach = null;
        String outTradeNo = payOrderId;
        String feeType = "CNY";
        String spBillCreateIP = payOrder.getClientIp();
        String timeStart = null;
        String timeExpire = null;
        String goodsTag = null;
        String notifyUrl = wxPayConfig.getNotifyUrl();
        String productId = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_NATIVE)) {
            productId = JSONObject.fromObject(payOrder.getExtra()).getString("productId");
        }
        String limitPay = null;
        String openId = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_JSPAI)) {
            openId = JSONObject.fromObject(payOrder.getExtra()).getString("openId");
        }
        String sceneInfo = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_MWEB)) {
            sceneInfo = JSONObject.fromObject(payOrder.getExtra()).getString("sceneInfo");
        }
        // 微信统一下单请求对象
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setDeviceInfo(deviceInfo);
        request.setBody(body);
        request.setDetail(detail);
        request.setAttach(attach);
        request.setOutTradeNo(outTradeNo);
        request.setFeeType(feeType);
        request.setTotalFee(totalFee);
        request.setSpbillCreateIp(spBillCreateIP);
        request.setTimeStart(timeStart);
        request.setTimeExpire(timeExpire);
        request.setGoodsTag(goodsTag);
        request.setNotifyUrl(notifyUrl);
        request.setTradeType(tradeType);
        request.setProductId(productId);
        request.setLimitPay(limitPay);
        request.setOpenid(openId);
        request.setSceneInfo(sceneInfo);

        return request;
    }
}
