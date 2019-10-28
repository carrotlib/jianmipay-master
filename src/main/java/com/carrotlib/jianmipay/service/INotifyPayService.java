package com.carrotlib.jianmipay.service;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface INotifyPayService {

    String doAliPayNotify(String jsonParam);

    String doWxPayNotify(String jsonParam);

    String sendBizPayNotify(String jsonParam);

    String handleAliPayNotify(String params);

    String handleWxPayNotify(String xmlResult);
}
