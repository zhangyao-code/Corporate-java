package com.codeages.corporate.biz.queue.handler;

public interface ConsumeHandler<T> {
    void handle(T payload);
}
