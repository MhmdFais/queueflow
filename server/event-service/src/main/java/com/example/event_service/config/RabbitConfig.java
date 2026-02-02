package com.example.event_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String AUTH_EVENTS_QUEUE = "auth.events";

    @Bean
    public Queue authEventsQueue() {
        return new Queue(AUTH_EVENTS_QUEUE, true);
    }
}
