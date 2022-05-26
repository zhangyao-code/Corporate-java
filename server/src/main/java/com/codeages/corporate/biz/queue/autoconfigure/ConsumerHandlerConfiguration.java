package com.codeages.corporate.biz.queue.autoconfigure;

import com.codeages.corporate.biz.queue.annotation.QueueConsumeHandler;
import com.codeages.corporate.biz.queue.handler.ConsumeHandler;
import com.codeages.corporate.biz.queue.handler.ConsumeHandlerRegistry;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ConsumerHandlerConfiguration  implements ApplicationContextAware, SmartInitializingSingleton  {

    private ConfigurableApplicationContext applicationContext;

    private final ConsumeHandlerRegistry registry;

    public ConsumerHandlerConfiguration(ConsumeHandlerRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(QueueConsumeHandler.class)
                .entrySet().stream().filter(entry -> !ScopedProxyUtils.isScopedTarget(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        beans.forEach(this::registerContainer);
    }

    private void registerContainer(String beanName, Object bean) {
        Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);

        if (!ConsumeHandler.class.isAssignableFrom(bean.getClass())) {
            throw new IllegalStateException(clazz + " is not instance of " + ConsumeHandler.class.getName());
        }

        QueueConsumeHandler annotation = clazz.getAnnotation(QueueConsumeHandler.class);

        registry.register(annotation.type(), (ConsumeHandler<?>) bean, annotation.log());
    }
}
