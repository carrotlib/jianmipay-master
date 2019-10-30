package com.carrotlib.jianmipay.biz;

import com.carrotlib.jianmipay.consts.PayConstant;
import com.carrotlib.jianmipay.mapper.model.PayOrder;
import com.carrotlib.jianmipay.service.MchInfoService;
import com.carrotlib.jianmipay.service.PayChannelService;
import com.carrotlib.jianmipay.utils.PaySeqUtil;
import com.carrotlib.jianmipay.utils.PayUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayOrderBiz {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayOrder.class);

    /**
     * 支付订单号
     */
    private static final String PAY_ORDER_ID = "payOrderId";

    /**
     * 商户ID
     */
    private static final String MCH_ID = "mchId";

    /**
     * 商户订单号
     */
    private static final String MCH_ORDER_NO = "mchOrderINo";

    /**
     * 支付渠道ID
     */
    private static final String CHANNEL_ID = "channelId";

    /**
     * 支付金额（单位分）
     */
    private static final String AMOUNT = "amount";

    /**
     * 币种
     */
    private static final String CURRENCY = "currency";

    /**
     * 客户端IP
     */
    private static final String CLIENT_IP = "clientIp";

    /**
     * 设备
     */
    private static final String DEVICE = "device";

    /**
     * 特定渠道发起时的额外参数
     */
    private static final String EXTRA = "extra";

    /**
     * 拓展参数1
     */
    private static final String PARAM1 = "param1";

    /**
     * 拓展参数2
     */
    private static final String PARAM2 = "param2";

    /**
     * 支付结果回调地址
     */
    private static final String NOTIFY_URL = "notifyUrl";

    /**
     * 签名
     */
    private static final String SIGN = "sign";

    /**
     * 商品主题
     */
    private static final String SUBJECT = "subject";

    /**
     * 商品描述信息
     */
    private static final String BODY = "body";

    @Resource
    private MchInfoService mchInfoService;

    @Resource
    private PayChannelService payChannelService;

    /**
     * 验证创建订单请求参数，合法则返回JSONObejct对象，非法则返回对应的错误信息
     * @param params
     * @param payContext
     * @return
     */
    public Object validateParamsForCreateOrder(JSONObject params, JSONObject payContext) {
        //若参数有问题，则返回对应的提示信息
        String errorMessage = null;

        //支付参数
        String mchId = params.optString(MCH_ID);
        String mchOrderNo = params.optString(MCH_ORDER_NO);
        String channelId = params.optString(CHANNEL_ID);
        String amount = params.optString(AMOUNT);
        String currency = params.optString(CURRENCY);
        String clientIp = params.optString(CLIENT_IP);
        String device = params.optString(DEVICE);
        String extra = params.optString(EXTRA);
        String param1 = params.optString(PARAM1);
        String param2 = params.optString(PARAM2);
        String notifyUrl = params.optString(NOTIFY_URL);
        String sign = params.optString(SIGN);
        String subject = params.optString(SUBJECT);
        String body = params.optString(BODY);

        //验证参数有效性(必选项参数)
        if(StringUtils.isBlank(mchId)) {
            errorMessage = "request params[mchId] error.";
            return errorMessage;
        }
        if(StringUtils.isBlank(mchOrderNo)) {
            errorMessage = "request params[mchOrderNo] error.";
            return errorMessage;
        }
        if(StringUtils.isBlank(channelId)) {
            errorMessage = "request params[channelId] error.";
            return errorMessage;
        }
        if(!NumberUtils.isNumber(amount)) {
            errorMessage = "request params[amount] error.";
            return errorMessage;
        }
        if(StringUtils.isBlank(currency)) {
            errorMessage = "request params[currency] error.";
            return errorMessage;
        }
        if(StringUtils.isBlank(notifyUrl)) {
            errorMessage = "request params[notifyUrl] error.";
            return errorMessage;
        }
        if(StringUtils.isBlank(subject)) {
            errorMessage = "request params[subject] error.";
            return errorMessage;
        }
        if(StringUtils.isBlank(body)) {
            errorMessage = "request params[body] error.";
            return errorMessage;
        }

        //根据不同支付渠道判断extra参数
        if (PayConstant.PAY_CHANNEL_WX_JSAPI.equalsIgnoreCase(channelId)) {
            if (StringUtils.isBlank(extra)) {
                errorMessage = "request params[extra] error.";
                return errorMessage;
            }
            JSONObject extraObject = JSONObject.fromObject(extra);
            String openId = extraObject.optString("openId");
            if (StringUtils.isBlank(openId)) {
                errorMessage = "request params[extra.openId] error.";
                return errorMessage;
            }
        } else if (PayConstant.PAY_CHANNEL_WX_NATIVE.equalsIgnoreCase(channelId)) {
            if (StringUtils.isBlank(extra)) {
                errorMessage = "request params[extra] error.";
                return errorMessage;
            }
            JSONObject extraObject = JSONObject.fromObject(extra);
            String productId = extraObject.optString("productId");
            if (StringUtils.isBlank(productId)) {
                errorMessage = "request params[extra.productId] error.";
                return errorMessage;
            }
        } else if (PayConstant.PAY_CHANNEL_WX_MWEB.equalsIgnoreCase(channelId)) {
            if (StringUtils.isBlank(extra)) {
                errorMessage = "request params[extra] error.";
                return errorMessage;
            }
            JSONObject extraObject = JSONObject.fromObject(extra);
            String sceneInfo = extraObject.optString("sceneInfo");
            if (StringUtils.isBlank(sceneInfo)) {
                errorMessage = "request params[extra.sceneInfo] error.";
                return errorMessage;
            }
        }

        // 签名信息
        if (StringUtils.isEmpty(sign)) {
            errorMessage = "request params[sign] error.";
            return errorMessage;
        }

        //查询商户信息
        JSONObject mchInfo = mchInfoService.getByMchId(mchId);
        if (null == mchInfo) {
            errorMessage = "cannot found mchInfo[mchId=" + mchId + "] record in db";
            return errorMessage;
        }
        if (mchInfo.getInt("state") != 1) {
            errorMessage = "mchInfo not available [mchId=" + mchId + "] record in db";
            return errorMessage;
        }
        String reqKey = mchInfo.optString("reqKey");
        if (StringUtils.isBlank(reqKey)) {
            errorMessage = "reqKey is null [mchId=" + mchId + "] record in db";
            return errorMessage;
        }
        payContext.put("reqkey", reqKey);

        //查询商户对应的支付渠道
        JSONObject payChannel = payChannelService.getByMchIdAndChannelId(mchId, channelId);
        if(payChannel == null) {
            errorMessage = "cannot found payChannel[channelId="+channelId+",mchId="+mchId+"] record in db.";
            return errorMessage;
        }
        if(payChannel.getInt("state") != 1) {
            errorMessage = "channel not available [channelId="+channelId+",mchId="+mchId+"]";
            return errorMessage;
        }

        //验证签名数据
        boolean verifyFlag = PayUtil.verifyPaySign(params, reqKey);
        if (!verifyFlag) {
            errorMessage = "Verify Jianmipay sign failed.";
            return errorMessage;
        }

        //参数校验通过，返回构建好的支付参数
        JSONObject payOrder = new JSONObject();

        payOrder.put(PAY_ORDER_ID, PaySeqUtil.getPay());
        payOrder.put(MCH_ID, mchId);
        payOrder.put(MCH_ORDER_NO, mchOrderNo);
        payOrder.put(CHANNEL_ID, channelId);
        payOrder.put(AMOUNT, amount);
        payOrder.put(CURRENCY, currency);
        payOrder.put(CLIENT_IP, clientIp);
        payOrder.put(DEVICE, device);
        payOrder.put(SUBJECT, subject);
        payOrder.put(BODY, body);
        payOrder.put(EXTRA, extra);
        payOrder.put("channelMchId", payChannel.optString("channelMchId"));
        payOrder.put(PARAM1, param1);
        payOrder.put(PARAM2, param2);
        payOrder.put(NOTIFY_URL, notifyUrl);

        return payOrder;
    }

    /**
     * 验证创建订单请求参数,参数通过返回JSONObject对象,否则返回错误文本信息
     * @param params
     * @return
     */
    public String validateParamsForQueryOrder(JSONObject params, JSONObject payContext) {

        // 验证请求参数,参数有问题返回错误提示
        String errorMessage;

        // 支付参数
        String mchId = params.getString("mchId");
        String mchOrderNo = params.getString("mchOrderNo");
        String payOrderId = params.getString("payOrderId");

        String sign = params.getString("sign");

        // 验证请求参数有效性（必选项）
        if(StringUtils.isBlank(mchId)) {
            errorMessage = "request params[mchId] error.";
            return errorMessage;
        }
        if(StringUtils.isBlank(mchOrderNo) && StringUtils.isBlank(payOrderId)) {
            errorMessage = "request params[mchOrderNo or payOrderId] error.";
            return errorMessage;
        }

        // 签名信息
        if (StringUtils.isEmpty(sign)) {
            errorMessage = "request params[sign] error.";
            return errorMessage;
        }

        // 查询商户信息
        JSONObject mchInfo = mchInfoService.getByMchId(mchId);
        if(mchInfo == null) {
            errorMessage = "Can't found mchInfo[mchId="+mchId+"] record in db.";
            return errorMessage;
        }
        if(mchInfo.getInt("state") != 1) {
            errorMessage = "mchInfo not available [mchId="+mchId+"] record in db.";
            return errorMessage;
        }

        String reqKey = mchInfo.getString("reqKey");
        if (StringUtils.isBlank(reqKey)) {
            errorMessage = "reqKey is null[mchId="+mchId+"] record in db.";
            return errorMessage;
        }
        payContext.put("resKey", mchInfo.getString("resKey"));

        // 验证签名数据
        boolean verifyFlag = PayUtil.verifyPaySign(params, reqKey);
        if(!verifyFlag) {
            errorMessage = "Verify Jianmipay sign failed.";
            return errorMessage;
        }

        return "success";
    }
}
