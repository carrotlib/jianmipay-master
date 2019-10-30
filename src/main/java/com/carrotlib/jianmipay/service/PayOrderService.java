package com.carrotlib.jianmipay.service;

import net.sf.json.JSONObject;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface PayOrderService {

    JSONObject createPayOrder(String jsonParam);

    String selectPayOrder(String jsonParam);

    String selectPayOrderByMchIdAndPayOrderId(String jsonParam);

    String selectPayOrderByMchIdAndMchOrderNo(String jsonParam);

    String updateStatus4Ing(String jsonParam);

    String updateStatus4Success(String jsonParam);

    String updateStatus4Complete(String jsonParam);

    String updateNotify(String jsonParam);

    int createPayOrder(JSONObject payOrder);

    JSONObject queryPayOrder(String mchId, String payOrderId, String mchOrderNo, String executeNotify);

    String doWxPayReq(String tradeType, JSONObject payOrder, String resKey);

    String doAliPayReq(String channelId, JSONObject payOrder, String resKey);
}
