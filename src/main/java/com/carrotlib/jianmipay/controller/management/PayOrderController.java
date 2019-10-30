package com.carrotlib.jianmipay.controller.management;

import com.carrotlib.jianmipay.service.PayOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay_order")
public class PayOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayOrderController.class);

    @Autowired
    private PayOrderService payOrderService;

    @RequestMapping("/list")
    public String list(ModelMap model) {
        return "pay_order";
    }
}
