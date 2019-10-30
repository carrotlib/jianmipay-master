package com.carrotlib.jianmipay.service;

import java.util.Map;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface NotifyPayService {

    String doAliPayNotify(String jsonParam);

    String doWxPayNotify(String jsonParam);

    String sendBizPayNotify(String jsonParam);

    String handleAliPayNotify(Map params);

    String handleWxPayNotify(String xmlResult);
}
