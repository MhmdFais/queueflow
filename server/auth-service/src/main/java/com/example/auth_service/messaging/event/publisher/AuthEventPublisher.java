package com.example.auth_service.messaging.event.publisher;

import com.example.event_service.event.AuthEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.example.event_service.config.RabbitConfig.AUTH_EVENTS_QUEUE;

@Component
public class AuthEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public AuthEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(AuthEvent event) {
        rabbitTemplate.convertAndSend(AUTH_EVENTS_QUEUE, event);
    }
}