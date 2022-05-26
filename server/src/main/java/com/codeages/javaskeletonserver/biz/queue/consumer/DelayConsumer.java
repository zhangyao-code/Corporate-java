package com.codeages.javaskeletonserver.biz.queue.consumer;

import com.codeages.javaskeletonserver.biz.queue.handler.ConsumeHandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RocketMQMessageListener(topic = "${app.rocketmq.delay-topic}", consumerGroup = "${app.rocketmq.delay-consumer-group}")
public class DelayConsumer extends BaseConsumer {
    public DelayConsumer(ConsumeHandlerRegistry handlerRegister) {
        super(handlerRegister);
    }
}
