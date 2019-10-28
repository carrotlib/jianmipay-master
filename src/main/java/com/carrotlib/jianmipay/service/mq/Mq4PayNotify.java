package com.carrotlib.jianmipay.service.mq;

import com.carrotlib.jianmipay.service.BasePayService;
import com.carrotlib.jianmipay.utils.GsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务通知MQ实现
 *
 * @author fenghaitao on 2019/10/28
 */
public abstract class Mq4PayNotify extends BasePayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Mq4PayNotify.class);

    private static final String RESPONSE_URL_KEY = "url";

    private static final String ORDER_ID_KEY = "orderId";

    private static final String COUNT_KEY = "count";

    @Resource
    private RestTemplate restTemplate;

    /**
     * 发送消息
     * @param msg
     */
    public abstract void send(String msg);

    /**
     * 发送延迟消息
     * @param msg
     * @param delay
     */
    public abstract void send(String msg, long delay);

    public void  receive(String msg) {
        LOGGER.debug("do notify task, msg={}", msg);

        Map<String, Object> msgMap = new HashMap<>();

        try {
            msgMap = GsonUtil.getInstance().fromJson(msg, Map.class);
        } catch (Exception e) {
            LOGGER.error("parse msg error! msg={}", msg, e);
        }

        if (null == msgMap) {
            return;
        }

        String responseUrl = String.valueOf(msgMap.get(RESPONSE_URL_KEY));
        String orderId = String.valueOf(msgMap.get(ORDER_ID_KEY));
        int count = (Integer) msgMap.get(COUNT_KEY);

        if (StringUtils.isBlank(responseUrl)) {
            LOGGER.debug("notify url is empty!");
            return;
        }

        try {
            String notifyResult = "";
            LOGGER.debug("===>MQ通知业务系统开始[orderId={}],[count={}],[time={}]",
                    orderId, count, new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
            try {
                URI uri = new URI(responseUrl);
                notifyResult = restTemplate.postForObject(uri, null, String.class);
            } catch (Exception e) {
                LOGGER.error("通知商户信息异常");
            }
            LOGGER.debug("<===MQ通知业务系统结束[orderId={}],[count={}],[time={}]",
                    orderId, count, new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));

            //验证结果
            LOGGER.debug("notify response, orderId={}", orderId);
            if (notifyResult.trim().equalsIgnoreCase("success")) {
                //修改订单表
                try {
                    int result = super.baseUpdateStatus4Complete(orderId);
                    LOGGER.debug("update payOrderId={}, payOrder status complete -> {}", orderId, result == 1 ? "success" : "fail");
                } catch (Exception e) {
                    LOGGER.error("update payOrder status to complete error! payOrderId={}", orderId, e);
                }
                //修改通知次数
                try {
                    int result = super.baseUpdateNotify(orderId, (byte) 1);
                    LOGGER.debug("update payOrderId={}, payOrder notify count -> {}", orderId, result == 1 ? "success" : "fail");
                } catch (Exception e) {
                    LOGGER.error("update payOrder notify count error! payOrderId={}", orderId, e);
                }

                //通知成功，结束
                return;
            } else {
                //通知失败，延时再通知
                int cnt = count + 1;
                LOGGER.debug("notify count={]", cnt);
                //修改通知次数
                try {
                    int result = super.baseUpdateNotify(orderId, (byte) cnt);
                    LOGGER.debug("update payOrderId={}, payOrder notify count -> {}", orderId, result == 1 ? "success" : "fail");
                } catch (Exception e) {
                    LOGGER.error("update payOrder notify count error! payOrderId={}", orderId, e);
                }

                if (cnt >5) {
                    LOGGER.debug("notify count > 5 stop. responseUrl={}", responseUrl);
                    return;
                }

                msgMap.put(COUNT_KEY, cnt);
                this.send(GsonUtil.getInstance().toJson(msgMap), cnt * 60 * 1000);
            }

            LOGGER.debug("notify fail! responseUrl={}, responseBody={}",responseUrl, notifyResult);
        } catch (Exception e) {
            LOGGER.debug("<===MQ通知业务系统结束[orderId={}],[count={}],[time={}]",
                    orderId, count, new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
            LOGGER.error("notify exception! responseUrl={}", responseUrl);
        }
    }
}
