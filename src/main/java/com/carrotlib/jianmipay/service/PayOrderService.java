package com.carrotlib.jianmipay.service;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface PayOrderService {

    Map createPayOrder(String paramJson);

    Map selectPayOrder(String paramJson);

    Map selectPayOrderByMchIdAndPayOrderId(String paramJson);

    Map selectPayOrderByMchIdAndMchOrderNo(String paramJson);

    Map updateStatus4Ing(String paramJson);

    Map updateStatus4Success(String paramJson);

    Map updateStatus4Complete(String paramJson);

    Map updateNotify(String paramJson);

    int createPayOrder(JSONObject payOrder);

    JSONObject queryPayOrder(String mchId, String payOrderId, String mchOrderNo, String executeNotify);

    String doWxPayReq(String tradeType, JSONObject payOrder, String resKey);

    String doAliPayReq(String channelId, JSONObject payOrder, String resKey);
}
