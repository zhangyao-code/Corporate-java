package com.codeages.corporate.biz.queue.consumer;

import com.codeages.corporate.biz.queue.handler.ConsumeHandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RocketMQMessageListener(topic = "${app.rocketmq.normal-topic}", consumerGroup = "${app.rocketmq.normal-consumer-group}")
public class NormalConsumer extends BaseConsumer {

    public NormalConsumer(ConsumeHandlerRegistry handlerRegister) {
        super(handlerRegister);
    }
}
