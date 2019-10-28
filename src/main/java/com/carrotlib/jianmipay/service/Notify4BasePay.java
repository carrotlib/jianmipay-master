package com.carrotlib.jianmipay.service;

import com.carrotlib.jianmipay.service.mq.Mq4PayNotify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 支付通知处理基类
 *
 * @author fenghaitao on 2019/10/28
 */
@Component
public class Notify4BasePay extends BasePayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Notify4BasePay.class);

    @Autowired
    private Mq4PayNotify mq4PayNotify;
}
