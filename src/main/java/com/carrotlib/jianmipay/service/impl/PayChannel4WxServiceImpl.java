package com.carrotlib.jianmipay.service.impl;

import com.carrotlib.jianmipay.model.wechat.WxPayProperties;
import com.carrotlib.jianmipay.service.BasePayService;
import com.carrotlib.jianmipay.service.PayChannel4WxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 微信支付渠道接口
 * @author fenghaitao on 2019/10/28
 */
@Service
public class PayChannel4WxServiceImpl extends BasePayService implements PayChannel4WxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayChannel4WxService.class);

    @Resource
    private WxPayProperties wxPayProperties;

    public Map doWxPayReq(String paramJson) {

        String logPrefix = "[微信支付统一下单]";


    }
}
