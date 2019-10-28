package com.carrotlib.jianmipay.service;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface IPayChannel4AliService {

    String doAliPayWapReq(String jsonParam);

    String doAliPayPcReq(String jsonParam);

    String doAliPayMobileReq(String jsonParam);

    String doAliPayQrReq(String jsonParam);
}
