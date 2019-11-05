package com.carrotlib.jianmipay.service.impl;

import com.carrotlib.jianmipay.service.Notify4BasePay;
import com.carrotlib.jianmipay.service.NotifyPayService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fenghaitao on 2019/10/28
 */
@Service
public class NotifyPayServiceImpl extends Notify4BasePay implements NotifyPayService {

    @Override
    public Map doAliPayNotify(String paramJson) {
        return new HashMap();
    }

    @Override
    public Map doWxPayNotify(String paramJson) {
        return new HashMap();
    }

    @Override
    public Map sendBizPayNotify(String paramJson) {
        {
            return new HashMap();
        }
    }

    @Override
    public String handleAliPayNotify(Map params) {
        return "";
    }

    @Override
    public String handleWxPayNotify(String xmlResult) {
        return "";
    }
}
