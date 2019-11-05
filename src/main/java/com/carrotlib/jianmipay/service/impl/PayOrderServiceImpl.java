package com.carrotlib.jianmipay.service.impl;

import com.carrotlib.jianmipay.service.BasePayService;
import com.carrotlib.jianmipay.service.PayOrderService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fenghaitao on 2019/10/28
 */
@Service
public class PayOrderServiceImpl extends BasePayService implements PayOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayOrderServiceImpl.class);

    @Autowired
    private NotifyPayServiceImpl notifyPayService;

    @Autowired
    private PayChannel4WxServiceImpl payChannel4WxService;

    @Autowired
    private PayChannel4AliServiceImpl payChannel4AliService;

    @Override
    public Map createPayOrder(String paramJson) {
        return new HashMap();
    }

    @Override
    public Map selectPayOrder(String paramJson) {
        return new HashMap();
    }

    @Override
    public Map selectPayOrderByMchIdAndPayOrderId(String paramJson) {
        return new HashMap();
    }

    @Override
    public Map selectPayOrderByMchIdAndMchOrderNo(String paramJson) {
        return new HashMap();
    }

    @Override
    public Map updateStatus4Ing(String paramJson) {
        return new HashMap();
    }

    @Override
    public Map updateStatus4Success(String paramJson) {
        return new HashMap();
    }

    @Override
    public Map updateStatus4Complete(String paramJson) {
        return new HashMap();
    }

    @Override
    public Map updateNotify(String paramJson) {
        return new HashMap();
    }

    @Override
    public int createPayOrder(JSONObject payOrder) {
        return 0;
    }

    @Override
    public JSONObject queryPayOrder(String mchId, String payOrderId, String mchOrderNo, String executeNotify) {
        return new JSONObject();
    }

    @Override
    public String doWxPayReq(String tradeType, JSONObject payOrder, String resKey) {
        return "";
    }

    @Override
    public String doAliPayReq(String channelId, JSONObject payOrder, String resKey) {
        return "";
    }
}
