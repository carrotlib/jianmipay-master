package com.carrotlib.jianmipay.controller.api;

import com.carrotlib.jianmipay.biz.PayOrderBiz;
import com.carrotlib.jianmipay.consts.PayConstant;
import com.carrotlib.jianmipay.service.PayOrderService;
import com.carrotlib.jianmipay.utils.GsonUtil;
import com.carrotlib.jianmipay.utils.PayUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 支付订单,包括:统一下单,订单查询,补单等接口
 *
 * @author fenghaitao on 2019/10/28
 */
@RestController
public class PayOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayOrderController.class);

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private PayOrderBiz payOrderBiz;

    /**
     * 统一下单接口:
     * 1)先验证接口参数以及签名信息
     * 2)验证通过创建支付订单
     * 3)根据商户选择渠道,调用支付服务进行下单
     * 4)返回下单数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/api/pay/create_order")
    public String payOrder(@RequestParam String params) {
        JSONObject po = JSONObject.fromObject(params);
        return payOrder(po);
    }

    @RequestMapping(value = "/api/pay/create_order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String payOrder(@RequestBody JSONObject params) {

        LOGGER.debug("###### 开始接收商户统一下单请求 ######");
        String logPrefix = "【商户统一下单】";

        try {
            JSONObject payContext = new JSONObject();
            JSONObject payOrder = null;
            //验证参数
            Object object = payOrderBiz.validateParamsForCreateOrder(params, payContext);

            if (object instanceof String) {
                LOGGER.debug("{}参数校验不通过， more info：{}", logPrefix, object);
                return PayUtil.genRetFail(PayUtil.genRetMap(PayConstant.RETURN_VALUE_FAIL, "创建支付订单失败", null, null));
            }

            if (object instanceof JSONObject) {
                payOrder = (JSONObject) object;
            }

            if (null == payOrder) {
                return PayUtil.genRetFail(PayUtil.genRetMap(PayConstant.RETURN_VALUE_FAIL, "创建支付订单失败", null, null));
            }

            int result = payOrderService.createPayOrder(payOrder);
            LOGGER.debug("{}创建订单结果，result={}", logPrefix, object);
            if (result != 1) {
                return PayUtil.genRetFail(PayUtil.genRetMap(PayConstant.RETURN_VALUE_FAIL, "创建支付订单失败", null, null));
            }

            String channelId = payOrder.optString("channelId");

            switch (channelId) {
                case PayConstant.PAY_CHANNEL_WX_APP :
                    return payOrderService.doWxPayReq(PayConstant.WxConstant.TRADE_TYPE_APP, payOrder, payContext.optString("resKey"));
                case PayConstant.PAY_CHANNEL_WX_JSAPI :
                    return payOrderService.doWxPayReq(PayConstant.WxConstant.TRADE_TYPE_JSPAI, payOrder, payContext.optString("resKey"));
                case PayConstant.PAY_CHANNEL_WX_NATIVE :
                    return payOrderService.doWxPayReq(PayConstant.WxConstant.TRADE_TYPE_NATIVE, payOrder, payContext.optString("resKey"));
                case PayConstant.PAY_CHANNEL_WX_MWEB :
                    return payOrderService.doWxPayReq(PayConstant.WxConstant.TRADE_TYPE_MWEB, payOrder, payContext.optString("resKey"));
                case PayConstant.PAY_CHANNEL_ALIPAY_MOBILE :
                    return payOrderService.doAliPayReq(channelId, payOrder, payContext.optString("resKey"));
                case PayConstant.PAY_CHANNEL_ALIPAY_PC :
                    return payOrderService.doAliPayReq(channelId, payOrder, payContext.optString("resKey"));
                case PayConstant.PAY_CHANNEL_ALIPAY_WAP :
                    return payOrderService.doAliPayReq(channelId, payOrder, payContext.optString("resKey"));
                case PayConstant.PAY_CHANNEL_ALIPAY_QR :
                    return payOrderService.doAliPayReq(channelId, payOrder, payContext.optString("resKey"));
                default:
                    return PayUtil.genRetFail(PayUtil.genRetMap(PayConstant.RETURN_VALUE_FAIL, "不支持的支付渠道类型[channelId=" + channelId + "]", null, null));
            }
        } catch (Exception e) {
            LOGGER.error("create order error! params={}", GsonUtil.getInstance().toJson(params), e);
            return PayUtil.genRetFail(PayUtil.genRetMap(PayConstant.RETURN_VALUE_FAIL, "支付中心系统异常", null, null));
        }
    }

    /**
     * 查询支付订单接口:
     * 1)先验证接口参数以及签名信息
     * 2)根据参数查询订单
     * 3)返回订单数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/api/pay/query_order")
    public String queryOrder(@RequestParam String params) {
        JSONObject po = JSONObject.fromObject(params);
        return queryOrder(po);
    }

    @RequestMapping(value = "/api/pay/query_order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String queryOrder(JSONObject params) {

        LOGGER.debug("###### 开始接收商户查询支付订单请求 ######");
        String logPrefix = "【商户支付订单查询】";

        try {
            JSONObject payContext = new JSONObject();

            // 验证参数有效性
            String errorMessage = payOrderBiz.validateParamsForQueryOrder(params, payContext);

            if (!"success".equalsIgnoreCase(errorMessage)) {
                LOGGER.debug(errorMessage);
                return PayUtil.genRetFail(PayUtil.genRetMap(PayConstant.RETURN_VALUE_FAIL, errorMessage, null, null));
            }

            LOGGER.debug("请求参数及签名校验通过");

            String mchId = params.optString("mchId");
            String mchOrderNo = params.optString("mchOrderNo");
            String payOrderId = params.optString("payOrderId");
            String executeNotify = params.optString("executeNotify");

            JSONObject payOrder = payOrderService.queryPayOrder(mchId, payOrderId, mchOrderNo, executeNotify);

            LOGGER.debug("{}查询支付订单,结果:{}", logPrefix, payOrder);

            if (null == payOrder) {
                return PayUtil.genRetFail(PayUtil.genRetMap(PayConstant.RETURN_VALUE_FAIL, "支付订单不存在", null, null));
            }

            Map<String, Object> map = PayUtil.genRetMap(PayConstant.RETURN_VALUE_SUCCESS, "", PayConstant.RETURN_VALUE_SUCCESS, null);
            map.put("result", payOrder);

            LOGGER.debug("###### 商户查询订单处理完成 ######");

            return PayUtil.genRetData(map, payContext.getString("resKey"));
        }catch (Exception e) {
            LOGGER.error("query order error! params={}", GsonUtil.getInstance().toJson(params), e);
            return PayUtil.genRetFail(PayUtil.genRetMap(PayConstant.RETURN_VALUE_FAIL, "支付中心系统异常", null, null));
        }
    }
}
