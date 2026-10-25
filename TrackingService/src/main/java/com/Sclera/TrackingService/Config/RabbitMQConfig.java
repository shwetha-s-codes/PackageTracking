package com.Sclera.TrackingService.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "package-tracking-exchange";
    public static final String PACKAGE_CREATED_QUEUE = "tracking-service.package-created-queue";
    public static final String PACKAGE_CREATED_ROUTING_KEY = "package.created";
    public static final String TRACKING_UPDATED_ROUTING_KEY = "tracking.updated";

    @Bean
    public TopicExchange packageTrackingExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue packageCreatedQueue() {
        return new Queue(PACKAGE_CREATED_QUEUE, true);
    }

    @Bean
    public Binding packageCreatedBinding(Queue packageCreatedQueue, TopicExchange packageTrackingExchange) {
        return BindingBuilder.bind(packageCreatedQueue)
                .to(packageTrackingExchange)
                .with(PACKAGE_CREATED_ROUTING_KEY);
    }

    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        return new JacksonJsonMessageConverter("com.Sclera.TrackingService");
    }
}