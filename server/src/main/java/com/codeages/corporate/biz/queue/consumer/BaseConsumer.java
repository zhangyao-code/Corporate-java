package com.codeages.corporate.biz.queue.consumer;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.codeages.corporate.biz.queue.handler.ConsumeHandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

@Slf4j
abstract public class BaseConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    final private ConsumeHandlerRegistry handlerRegister;

    public BaseConsumer(ConsumeHandlerRegistry handlerRegister) {
        this.handlerRegister = handlerRegister;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onMessage(MessageExt message) {
        var type = message.getUserProperty("type");
        if (StrUtil.isEmpty(type)) {
            log.error("Queue consume message failed, message type is empty.");
            return ;
        }

        var handlerContainer = handlerRegister.getHandler(type);
        if (handlerContainer == null) {
            log.error("Queue consume message failed, message type {} handler is not registered.", type);
            return ;
        }

        var handler = handlerContainer.getHandler();
        var msgType = handlerContainer.getMessageType();
        var needLog = handlerContainer.isLog();

        var body = new String(message.getBody());

        Object payload;
        if ("{}".equals(body)) {
            try {
                payload = msgType.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                log.error("Queue consume message failed, new empty object ({}) failed", msgType);
                return ;
            }
        } else {
            try {
                payload = JSONUtil.toBean(new String(message.getBody()), msgType);
            } catch (Exception e) {
                log.error("Queue consume message failed, convert json to bean failed, {}, {}", msgType, new String(message.getBody()));
                return ;
            }
        }

        if (needLog) {
            log.info("--QUEUE-- consume {} {}", type, payload);
        }

        handler.handle(payload);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // https://github.com/apache/rocketmq/blob/master/docs/cn/best_practice.md#3--pushconsumer%E9%85%8D%E7%BD%AE
        // Consumer?????????????????????????????????????????????????????????????????????????????????
        //   * ????????????????????????????????????????????????????????????????????????????????????
        //   * ???????????????????????????????????????????????????????????????????????????????????????
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
    }
}
