package com.carrotlib.jianmipay.service.mq.impl;

import com.carrotlib.jianmipay.service.mq.Mq4PayNotify;
import com.carrotlib.jianmipay.service.mq.MqConfig;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @author fenghaitao on 2019/10/28
 */
@Component
@Profile(MqConfig.Impl.ACTIVE_MQ)
public class ActiveMq4PayNotify extends Mq4PayNotify {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMq4PayNotify.class);

    @Bean
    public Queue payNotifyQueue () {
        return new ActiveMQQueue(MqConfig.PAY_NOTIFY_QUEUE_NAME);
    }

    @Autowired
    private Queue payNotifyQueue;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void send(String msg) {
        LOGGER.debug("发送MQ消息:msg={}", msg);
        jmsTemplate.convertAndSend(payNotifyQueue, msg);
    }

    @Override
    public void send(String msg, long delay) {
        LOGGER.debug("发送MQ延时消息:msg={},delay={}", msg, delay);
        jmsTemplate.send(this.payNotifyQueue, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage tm = session.createTextMessage(msg);
                tm.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
                tm.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 1*1000);
                tm.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 1);
                return tm;
            }
        });
    }

    @JmsListener(destination = MqConfig.PAY_NOTIFY_QUEUE_NAME)
    public void onMessage(String msg) {
        receive(msg);
    }
}
