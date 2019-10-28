package com.carrotlib.jianmipay.service;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface IPayOrderService {

    String createPayOrder(String jsonParam);

    String selectPayOrder(String jsonParam);

    String selectPayOrderByMchIdAndPayOrderId(String jsonParam);

    String selectPayOrderByMchIdAndMchOrderNo(String jsonParam);

    String updateStatus4Ing(String jsonParam);

    String updateStatus4Success(String jsonParam);

    String updateStatus4Complete(String jsonParam);

    String updateNotify(String jsonParam);

    //int createPayOrder(String payOrder);

    String queryPayOrder(String mchId, String payOrderId, String mchOrderNo, String executeNotify);

    String doWxPayReq(String tradeType, String payOrder, String resKey);

    String doAliPayReq(String channelId, String payOrder, String resKey);
}
