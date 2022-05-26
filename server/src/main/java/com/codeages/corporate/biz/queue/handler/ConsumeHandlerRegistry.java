package com.codeages.corporate.biz.queue.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConsumeHandlerRegistry {

    private final Map<String, ConsumeHandlerContainer> handlers = new HashMap<>();

    public void register(String type, ConsumeHandler<?> handler, boolean log) {
        if (handlers.containsKey(type)) {
            throw new IllegalCallerException("Queue consume handler (" + type + ") is existed.");
        }

        var container = new ConsumeHandlerContainer(handler, log);
        handlers.put(type, container);
    }

    public ConsumeHandlerContainer getHandler(String type) {
        if (!handlers.containsKey(type)) {
            return null;
        }

        return handlers.get(type);
    }
}
