package com.carrotlib.jianmipay.service;

import java.util.Map;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface NotifyPayService {

    Map doAliPayNotify(String paramJson);

    Map doWxPayNotify(String paramJson);

    Map sendBizPayNotify(String paramJson);

    String handleAliPayNotify(Map params);

    String handleWxPayNotify(String xmlResult);
}
