package com.carrotlib.jianmipay.service.mq.impl;

import com.carrotlib.jianmipay.service.mq.Mq4PayNotify;
import com.carrotlib.jianmipay.service.mq.MqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author fenghaitao on 2019/10/28
 */
@Component
@Profile(MqConfig.Impl.RABBIT_MQ)
public class RabbitMq4PayNotify extends Mq4PayNotify {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMq4PayNotify.class);

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        DirectExchange exchange = new DirectExchange(MqConfig.PAY_NOTIFY_EXCHANGE_NAME);
        exchange.setDelayed(true);
        Queue queue = new Queue(MqConfig.PAY_NOTIFY_QUEUE_NAME);
        Binding binding = BindingBuilder.bind(queue).to(exchange).withQueueName();
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
    }

    @Override
    public void send(String msg) {
        LOGGER.debug("send mq message, msg={}", msg);
        rabbitTemplate.convertAndSend(MqConfig.PAY_NOTIFY_QUEUE_NAME, msg);
    }

    @Override
    public void send(String msg, long delay) {
        LOGGER.debug("send mq delay message, msg={}, delay={}", msg, delay);
        rabbitTemplate.convertAndSend(MqConfig.PAY_NOTIFY_EXCHANGE_NAME, MqConfig.PAY_NOTIFY_QUEUE_NAME, msg, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay((int) delay);
                 return message;
            }
        });
    }

    @RabbitListener(queues = MqConfig.PAY_NOTIFY_QUEUE_NAME)
    public void onMessage(String msg) {
        receive(msg);
    }
}
