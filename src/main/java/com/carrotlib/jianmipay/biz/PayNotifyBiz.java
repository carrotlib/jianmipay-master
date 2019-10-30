package com.carrotlib.jianmipay.biz;

import com.carrotlib.jianmipay.consts.PayConstant;
import com.carrotlib.jianmipay.service.NotifyPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class PayNotifyBiz {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayNotifyBiz.class);

    @Resource
    private NotifyPayService notifyPayService;

    public String doAliPayRes(HttpServletRequest request) throws ServletException, IOException {
        String logPrefix = "【支付宝支付回调通知】";
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        LOGGER.debug("{}通知请求数据:reqStr={}", logPrefix, params);
        if(params.isEmpty()) {
            LOGGER.error("{}请求参数为空", logPrefix);
            return PayConstant.RETURN_ALIPAY_VALUE_FAIL;
        }
        return notifyPayService.handleAliPayNotify(params);
    }

    public String doWxPayRes(HttpServletRequest request) throws ServletException, IOException {
        String logPrefix = "【微信支付回调通知】";
        String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
        LOGGER.debug("{}通知请求数据:reqStr={}", logPrefix, xmlResult);
        return notifyPayService.handleWxPayNotify(xmlResult);
    }
}
