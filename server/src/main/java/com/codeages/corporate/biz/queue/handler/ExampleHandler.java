package com.codeages.corporate.biz.queue.handler;

import com.codeages.corporate.biz.queue.annotation.QueueConsumeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@QueueConsumeHandler(type = "example", log = false)
public class ExampleHandler implements ConsumeHandler<ExampleMessage> {

    @Override
    public void handle(ExampleMessage payload) {
        log.info("ExampleConsumeHandler {}", payload);
    }
}