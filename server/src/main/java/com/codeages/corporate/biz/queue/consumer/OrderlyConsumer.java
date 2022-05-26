package com.codeages.corporate.biz.queue.consumer;

import com.codeages.corporate.biz.queue.handler.ConsumeHandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RocketMQMessageListener(topic = "${app.rocketmq.orderly-topic}", consumerGroup = "${app.rocketmq.orderly-consumer-group}", consumeMode = ConsumeMode.ORDERLY)
public class OrderlyConsumer extends BaseConsumer {

    public OrderlyConsumer(ConsumeHandlerRegistry handlerRegister) {
        super(handlerRegister);
    }
}
