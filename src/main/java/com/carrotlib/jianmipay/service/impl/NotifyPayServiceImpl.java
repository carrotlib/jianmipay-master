package com.carrotlib.jianmipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.carrotlib.jianmipay.consts.PayConstant;
import com.carrotlib.jianmipay.consts.RetEnum;
import com.carrotlib.jianmipay.mapper.model.PayChannel;
import com.carrotlib.jianmipay.mapper.model.PayOrder;
import com.carrotlib.jianmipay.model.alipay.AlipayConfig;
import com.carrotlib.jianmipay.model.domain.BaseParam;
import com.carrotlib.jianmipay.service.Notify4BasePay;
import com.carrotlib.jianmipay.service.NotifyPayService;
import com.carrotlib.jianmipay.utils.GsonUtil;
import com.carrotlib.jianmipay.utils.ObjectValidUtil;
import com.carrotlib.jianmipay.utils.RPCUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fenghaitao on 2019/10/28
 */
@Service
public class NotifyPayServiceImpl extends Notify4BasePay implements NotifyPayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyPayServiceImpl.class);

    @Autowired
    private AlipayConfig alipayConfig;

    @Override
    public Map doAliPayNotify(String paramJson) {

        String logPrefix = "【处理支付宝支付回调】";
        LOGGER.debug("====== 开始处理支付宝支付回调通知 ======");

        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();

        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("处理支付宝支付回调失败, {}. paramJson={}", RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }

        Map params = baseParam.isNullValue("params") ? null : (Map) bizParamMap.get("params");

        if (ObjectValidUtil.isInvalid(params)) {
            LOGGER.debug("处理支付宝支付回调失败, {}. paramJson={}", RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        Map<String, Object> payContext = new HashMap<>();
        PayOrder payOrder;
        payContext.put("parameters", params);

        if (!verifyAliPayParams(payContext)) {
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_BIZ_PAY_NOTIFY_VERIFY_FAIL);
        }
        LOGGER.debug("{}验证支付通知数据及签名通过", logPrefix);

        //交易状态
        String tradeStatus = String.valueOf(params.get("trade_status"));
        //渠道订单号
        String tradeNo = String.valueOf(params.get("trade_no"));

        //支付状态成功或完成
        if (PayConstant.AlipayConstant.TRADE_STATUS_SUCCESS.equals(tradeStatus)
                || PayConstant.AlipayConstant.TRADE_STATUS_FINISHED.equals(tradeStatus)) {
            int updatePayOrderRows;
            payOrder = (PayOrder) payContext.get("payOrder");
            // 0：订单生成，1：支付中，-1：支付失败，2：支付成功，3：业务处理完成，-2：订单过期
            byte payStatus = payOrder.getStatus();
        }

    }

    @Override
    public Map doWxPayNotify(String paramJson) {
        return new HashMap();
    }

    @Override
    public Map sendBizPayNotify(String paramJson) {
        {
            return new HashMap();
        }
    }

    public boolean verifyAliPayParams(Map<String, Object> payContext) {

        Map<String, String> params = (Map<String, String>) payContext.get("parameters");

        //商户订单号
        String outTradeNo = params.get("out_trade_no");
        //支付金额
        String totalAmount = params.get("total_amount");

        if (StringUtils.isBlank(outTradeNo)) {
            LOGGER.error("Alipay Notify parameter out_trade_no is empty. out_trade_no={}", outTradeNo);
            payContext.put("retMsg", "out_trade_no is empty");
            return false;
        }

        if (StringUtils.isBlank(totalAmount)) {
            LOGGER.error("Alipay Notify parameter totoal_amount is empty. total_amount={}", totalAmount);
            payContext.put("retMsg", "total_amount is empty");
            return false;
        }

        String errorMessage;

        //查询payOrder记录
        String payOrderId = outTradeNo;

        PayOrder payOrder = super.baseSelectPayOrder(payOrderId);

        if (null == payOrder) {
            LOGGER.error("cannot find payOrder from db. payOrderId={}", payOrderId);
            payContext.put("retMsg", "cannot find payOrder");
            return false;
        }

        //查询payChannel记录
        String mchId = payOrder.getMchId();
        String channelId = payOrder.getChannelId();

        PayChannel payChannel = super.baseSelectPayChannel(mchId, channelId);

        if (null == payChannel) {
            LOGGER.error("cannot find payChannel from db. mchId={}, channelId={}", mchId, channelId);
            payContext.put("retMsg", "cannot find payChannel");
            return false;
        }

        boolean verifyResult = false;

        try {
            verifyResult = AlipaySignature.rsaCheckV1(params, alipayConfig.init(payChannel.getParam()).getAlipayPublicKey(), AlipayConfig.CHARSET, "RSA2");
        } catch (AlipayApiException e) {
            LOGGER.error("AlipaySignature.rsaCheckV1 error.");
        }

        //验证签名
        if (!verifyResult) {
            errorMessage = "rsaCheckV1 failed.";
            LOGGER.error("Alipay Notify parameter {}", errorMessage);
            payContext.put("retMsg", errorMessage);
            return false;
        }

        //核对金额
        long aliPayAmt = new BigDecimal(totalAmount).movePointRight(2).longValue();
        long dbPayAmt = payOrder.getAmount().longValue();

        if (aliPayAmt != dbPayAmt) {
            LOGGER.error("db payOrder record payPrice not equals total_amount. total_amount={}, payOrderId={}", totalAmount, payOrderId);
            payContext.put("retMsg", "db payOrder record payPrice not equals total_amount.");
            return false;
        }

        payContext.put("payOrder", payOrder);

        return true;
    }

    @Override
    public String handleAliPayNotify(Map params) {
        return "";
    }

    @Override
    public String handleWxPayNotify(String xmlResult) {
        return "";
    }
}
