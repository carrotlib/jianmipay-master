package com.carrotlib.jianmipay.controller.api;

import com.carrotlib.jianmipay.biz.PayNotifyBiz;
import com.carrotlib.jianmipay.service.BasePayService;
import com.carrotlib.jianmipay.service.NotifyPayService;
import com.carrotlib.jianmipay.service.PayChannel4AliService;
import com.carrotlib.jianmipay.service.PayChannel4WxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 接收处理支付渠道通知
 *
 * @author fenghaitao on 2019/10/28
 */
public class NotifyController extends BasePayService implements PayChannel4AliService, PayChannel4WxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyController.class);

    @Autowired
    private NotifyPayService notifyPayService;

    @Autowired
    private PayNotifyBiz payNotifyBiz;

    /**
     * 支付宝移动支付后台通知响应
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/notify/pay/aliPayNotifyRes.html")
    public String aliPayNotifyRes(HttpServletRequest request) throws ServletException, IOException {
        LOGGER.debug("====== 开始接收支付宝支付回调通知 ======");
        String notifyRes = payNotifyBiz.doAliPayRes(request);
        LOGGER.debug("响应给支付宝:{}", notifyRes);
        LOGGER.debug("====== 完成接收支付宝支付回调通知 ======");
        return notifyRes;
    }

    /**
     * 微信支付(统一下单接口)后台通知响应
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/notify/pay/wxPayNotifyRes.htm")
    public String wxPayNotifyRes(HttpServletRequest request) throws ServletException, IOException {
        LOGGER.error("====== 开始接收微信支付回调通知 ======");
        String notifyRes = payNotifyBiz.doWxPayRes(request);
        LOGGER.debug("响应给微信:{}", notifyRes);
        LOGGER.debug("====== 完成接收微信支付回调通知 ======");
        return notifyRes;
    }
}
