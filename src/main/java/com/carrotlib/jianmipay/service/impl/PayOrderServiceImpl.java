package com.carrotlib.jianmipay.service.impl;

import com.carrotlib.jianmipay.consts.PayConstant;
import com.carrotlib.jianmipay.consts.RetEnum;
import com.carrotlib.jianmipay.mapper.model.PayOrder;
import com.carrotlib.jianmipay.model.domain.BaseParam;
import com.carrotlib.jianmipay.service.BasePayService;
import com.carrotlib.jianmipay.service.PayOrderService;
import com.carrotlib.jianmipay.utils.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fenghaitao on 2019/10/28
 */
@Service
public class PayOrderServiceImpl extends BasePayService implements PayOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayOrderServiceImpl.class);

    @Autowired
    private NotifyPayServiceImpl notifyPayService;

    @Autowired
    private PayChannel4WxServiceImpl payChannel4WxService;

    @Autowired
    private PayChannel4AliServiceImpl payChannel4AliService;

    @Override
    public int createPayOrder(JSONObject payOrder) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("payOrder", payOrder);
        String paramJson = RPCUtil.createBaseParam(paramMap);
        Map<String, Object> result = createPayOrder(paramJson);

        String s = RPCUtil.mkRet(result);
        if (null == s) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    @Override
    public Map createPayOrder(String paramJson) {

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();
        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("新增支付订单失败, {}, paramJson={}", RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        JSONObject payOrderObj = baseParam.isNullValue("payOrder") ? null : JSONObject.fromObject(bizParamMap.get("payOrder").toString());

        if (null == payOrderObj) {
            LOGGER.debug("新增支付订单失败, {}, paramJson={}", RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        PayOrder payOrder = BeanConvertUtil.map2Bean(payOrderObj, PayOrder.class);

        if (null == payOrder) {
            LOGGER.debug("新增支付订单失败, {}, paramJson={}", RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        int result = super.baseCreatePayOrder(payOrder);

        return RPCUtil.createBizResult(baseParam, result);
    }

    @Override
    public Map selectPayOrder(String paramJson) {

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();

        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("根据支付订单号查询支付订单失败， {}， paramJson={}", RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        String payOrderId = baseParam.isNullValue("payOrderId") ? null : bizParamMap.get("payOrderId").toString();

        if (ObjectValidUtil.isInvalid(payOrderId)) {
            LOGGER.debug("根据支付订单号查询支付订单失败， {}， paramJson={}", RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        PayOrder payOrder = super.baseSelectPayOrder(payOrderId);

        if (null == payOrder) {
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_BIZ_DATA_NOT_EXISTS);
        }

        String jsonResult = GsonUtil.getInstance().toJson(payOrder);

        return RPCUtil.createBizResult(baseParam, jsonResult);
    }

    @Override
    public JSONObject queryPayOrder(String mchId, String payOrderId, String mchOrderNo, String executeNotify) {

        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> result;

        if (StringUtils.isNotBlank(payOrderId)) {
            paramMap.put("mchId", mchId);
            paramMap.put("payOrderId", payOrderId);
            String paramJson = RPCUtil.createBaseParam(paramMap);
            result = selectPayOrderByMchIdAndPayOrderId(paramJson);
        } else {
            paramMap.put("mchId", mchId);
            paramMap.put("mchOrderNo", mchOrderNo);
            String paramJson = RPCUtil.createBaseParam(paramMap);
            result = selectPayOrderByMchIdAndMchOrderNo(paramJson);
        }

        String s = RPCUtil.mkRet(result);

        if (null == s) {
            return null;
        }

        boolean isNotify = Boolean.parseBoolean(executeNotify);

        JSONObject payOrder = JSONObject.fromObject(s);

        if (isNotify) {
            paramMap = new HashMap<>();
            paramMap.put("payOrderId", payOrderId);
            String paramJson = RPCUtil.createBaseParam(paramMap);
            result = notifyPayService.sendBizPayNotify(paramJson);
            s = RPCUtil.mkRet(result);
            LOGGER.debug("业务查单完成，并再次发送业务支付通知，发送结果:{}", s);
        }

        return payOrder;
    }

    @Override
    public Map selectPayOrderByMchIdAndPayOrderId(String paramJson) {

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();

        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("根据商户号和支付订单号查询支付订单失败， {}， paramJson={}", RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        String mchId = baseParam.isNullValue("mchId") ? null : bizParamMap.get("mchId").toString();
        String payOrderId = baseParam.isNullValue("payOrderId") ? null : bizParamMap.get("payOrderId").toString();

        if (ObjectValidUtil.isInvalid(mchId, payOrderId)) {
            LOGGER.debug("根据商户号和支付订单号查询支付订单失败， {}， paramJson={}", RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        PayOrder payOrder = super.baseSelectPayOrderByMchIdAndPayOrderId(mchId, payOrderId);

        if (null == payOrder) {
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_BIZ_DATA_NOT_EXISTS);
        }

        String jsonResult = GsonUtil.getInstance().toJson(payOrder);

        return RPCUtil.createBizResult(baseParam, jsonResult);
    }

    @Override
    public Map selectPayOrderByMchIdAndMchOrderNo(String paramJson) {

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();

        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("根据商户号和商户订单号查询支付订单失败， {}， paramJson={}", RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        String mchId = baseParam.isNullValue("mchId") ? null : bizParamMap.get("mchId").toString();
        String mchpOrderNo = baseParam.isNullValue("mchOrderNo") ? null : bizParamMap.get("mchOrderNo").toString();


        if (ObjectValidUtil.isInvalid(mchId, mchpOrderNo)) {
            LOGGER.debug("根据商户号和商户订单号查询支付订单失败， {}， paramJson={}", RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        PayOrder payOrder = super.baseSelectpayOrderByMchIdAndMchOrderNo(mchId, mchpOrderNo);

        if (null == payOrder) {
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_BIZ_DATA_NOT_EXISTS);
        }

        String jsonResult = GsonUtil.getInstance().toJson(payOrder);

        return RPCUtil.createBizResult(baseParam, jsonResult);
    }

    @Override
    public Map updateStatus4Ing(String paramJson) {

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();

        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("修改支付订单状态为支付中失败， {}， paramJson={}", RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        String payOrderNo  = baseParam.isNullValue("payOrderNo") ? null : bizParamMap.get("payOrderNo").toString();
        String channelOrderNo = baseParam.isNullValue("channelOrderNo") ? null : bizParamMap.get("channelOrderNo").toString();

        if (ObjectValidUtil.isInvalid(payOrderNo, channelOrderNo)) {
            LOGGER.debug("修改支付订单状态为支付中失败， {}， paramJson={}", RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        int result = super.baseUpdateStatus4Ing(payOrderNo, channelOrderNo);

        return RPCUtil.createBizResult(baseParam, result);
    }

    @Override
    public Map updateStatus4Success(String paramJson) {

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();

        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("修改支付订单状态为支付成功失败， {}， paramJson={}", RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        String payOrderId = baseParam.isNullValue("payOrderId") ? null : bizParamMap.get("payOrderId").toString();

        if (ObjectValidUtil.isInvalid(payOrderId)) {
            LOGGER.debug("修改支付订单状态为支付成功失败， {}， paramJson={}", RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        int result = super.baseUpdateStatus4Success(payOrderId, null);

        return RPCUtil.createBizResult(baseParam, result);
    }

    @Override
    public Map updateStatus4Complete(String paramJson) {

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();

        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("修改支付订单状态为支付完成失败， {}， paramJson={}", RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        String payOrderId  = baseParam.isNullValue("payOrderId") ? null : bizParamMap.get("payOrderId").toString();

        if (ObjectValidUtil.isInvalid(payOrderId)) {
            LOGGER.debug("修改支付订单状态为支付完成失败， {}， paramJson={}", RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        int result = super.baseUpdateStatus4Complete(payOrderId);

        return RPCUtil.createBizResult(baseParam, result);
    }

    @Override
    public Map updateNotify(String paramJson) {

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();

        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("修改支付订单通知次数失败， {}， paramJson={}", RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        String payOrderId = baseParam.isNullValue("payOrderId") ? null : bizParamMap.get("payOrderId").toString();
        Byte count = baseParam.isNullValue("count") ? null : Byte.parseByte(bizParamMap.get("count").toString());

        if (ObjectValidUtil.isInvalid(payOrderId)) {
            LOGGER.debug("修改支付订单通知次数失败， {}， paramJson={}", RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        int result = super.baseUpdateNotify(payOrderId, count);

        return RPCUtil.createBizResult(baseParam, result);
    }

    @Override
    public String doWxPayReq(String tradeType, JSONObject payOrder, String resKey) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tradeType", tradeType);
        paramMap.put("payOrder", payOrder);

        String paramJson = RPCUtil.createBaseParam(paramMap);

        Map<String, Object> result = payChannel4WxService.doWxPayReq(paramJson);

        String s = RPCUtil.mkRet(result);

        if (null == s) {
            return PayUtil.genRetData(
                    PayUtil.genRetMap(PayConstant.RETURN_VALUE_SUCCESS, "", PayConstant.RETURN_VALUE_FAIL, "0111", "调用微信支付失败"), resKey);
        }

        Map<String, Object> map = PayUtil.genRetMap(PayConstant.RETURN_VALUE_SUCCESS, "", PayConstant.RETURN_VALUE_SUCCESS, null);
        map.putAll((Map) result.get("bizResult"));

        return PayUtil.genRetData(map, resKey);
    }

    @Override
    public String doAliPayReq(String channelId, JSONObject payOrder, String resKey) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("payOrder", payOrder);

        String paramJson = RPCUtil.createBaseParam(paramMap);

        Map<String, Object> result;
        switch (channelId) {
            case PayConstant.PAY_CHANNEL_ALIPAY_MOBILE :
                result = payChannel4AliService.doAliPayMobileReq(paramJson);
                break;
            case PayConstant.PAY_CHANNEL_ALIPAY_PC :
                result = payChannel4AliService.doAliPayPcReq(paramJson);
                break;
            case PayConstant.PAY_CHANNEL_ALIPAY_WAP :
                result = payChannel4AliService.doAliPayWapReq(paramJson);
                break;
            case PayConstant.PAY_CHANNEL_ALIPAY_QR :
                result = payChannel4AliService.doAliPayQrReq(paramJson);
                break;
            default:
                result = null;
                break;
        }

        String s = RPCUtil.mkRet(result);

        if(s == null) {
            return PayUtil.genRetData(PayUtil.genRetMap(PayConstant.RETURN_VALUE_SUCCESS, "", PayConstant.RETURN_VALUE_FAIL, "0111", "调用支付宝支付失败"), resKey);
        }

        Map<String, Object> map = PayUtil.genRetMap(PayConstant.RETURN_VALUE_SUCCESS, "", PayConstant.RETURN_VALUE_SUCCESS, null);
        map.putAll((Map) result.get("bizResult"));

        return PayUtil.genRetData(map, resKey);
    }
}
