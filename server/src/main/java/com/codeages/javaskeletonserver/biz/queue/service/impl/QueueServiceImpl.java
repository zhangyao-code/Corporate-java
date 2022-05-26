package com.codeages.javaskeletonserver.biz.queue.service.impl;

import com.codeages.javaskeletonserver.biz.queue.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Service
public class QueueServiceImpl implements QueueService {

    @Value("${app.rocketmq.normal-topic}")
    private String normalTopic;

    @Value("${app.rocketmq.delay-topic}")
    private String delayTopic;

    @Value("${app.rocketmq.orderly-topic}")
    private String orderlyTopic;

    final RocketMQTemplate rocketMQTemplate;

    final long SEND_TIMEOUT = 3000;

    public QueueServiceImpl(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @Override
    public <T> void send(String type, T payload) {
        send(type, payload, null);
    }

    @Override
    public <T> void send(String type, T payload, String tag) {
        var message = MessageBuilder
                .withPayload(payload)
                .setHeader("type", type)
                .build();
        var sendOrderlyRes = rocketMQTemplate.syncSend(makeDestination(normalTopic, tag), message, SEND_TIMEOUT);
        log.info("sendOrderlyRes :"+sendOrderlyRes.toString() + "type:" + type);
    }

    @Override
    public <T> void sendDelay(String type, T payload, int delayLevel) {
        sendDelay(type, payload, delayLevel, null);
    }

    @Override
    public <T> void sendDelay(String type, T payload, int delayLevel, String tag) {
        var message = MessageBuilder
                .withPayload(payload)
                .setHeader("type", type)
                .build();
        rocketMQTemplate.syncSend(makeDestination(delayTopic, tag), message, SEND_TIMEOUT, delayLevel);
    }

    @Override
    public <T> void sendOrderly(String type, T payload, String sharedKey) {
        sendOrderly(type, payload, sharedKey, null);
    }

    @Override
    public <T> void sendOrderly(String type, T payload, String sharedKey, String tag) {
        var message = MessageBuilder
                .withPayload(payload)
                .setHeader("type", type)
                .build();
        var sendOrderlyRes = rocketMQTemplate.syncSendOrderly(makeDestination(orderlyTopic, tag), message, sharedKey, SEND_TIMEOUT);
        log.info("sendOrderlyRes :"+sendOrderlyRes.toString() + "type:" + type);
    }

    private String makeDestination(String topic, String tag) {
        return tag == null ? topic : (topic + ":" + tag);
    }
}
