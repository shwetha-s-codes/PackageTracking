package com.Sclera.PackageService.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "package-tracking-exchange";
    public static final String PACKAGE_CREATED_ROUTING_KEY = "package.created";
    public static final String TRACKING_UPDATED_QUEUE = "package-service.tracking-updated-queue";
    public static final String TRACKING_UPDATED_ROUTING_KEY = "tracking.updated";

    @Bean
    public TopicExchange packageTrackingExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue trackingUpdatedQueue() {
        return new Queue(TRACKING_UPDATED_QUEUE, true);
    }

    @Bean
    public Binding trackingUpdatedBinding(Queue trackingUpdatedQueue, TopicExchange packageTrackingExchange) {
        return BindingBuilder.bind(trackingUpdatedQueue)
                .to(packageTrackingExchange)
                .with(TRACKING_UPDATED_ROUTING_KEY);
    }

    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        return new JacksonJsonMessageConverter("com.Sclera.PackageService");
    }
}