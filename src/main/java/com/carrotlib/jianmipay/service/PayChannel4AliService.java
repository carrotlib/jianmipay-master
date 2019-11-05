package com.carrotlib.jianmipay.service;

import java.util.Map;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface PayChannel4AliService {

    Map doAliPayWapReq(String paramJson);

    Map doAliPayPcReq(String paramJson);

    Map doAliPayMobileReq(String paramJson);

    Map doAliPayQrReq(String paramJson);

}
