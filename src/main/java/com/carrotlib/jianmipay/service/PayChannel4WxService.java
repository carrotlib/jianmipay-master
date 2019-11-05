package com.carrotlib.jianmipay.service;

import java.util.Map;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface PayChannel4WxService {

    Map<String, Object> doWxPayReq(String paramJson);
}
