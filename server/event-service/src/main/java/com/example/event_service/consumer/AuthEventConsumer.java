package com.example.event_service.consumer;

import com.example.event_service.config.RabbitConfig;
import com.example.event_service.event.AuthEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AuthEventConsumer {

    @RabbitListener(queues = RabbitConfig.AUTH_EVENTS_QUEUE)
    public void handleAuthEvent(AuthEvent event) {
        System.out.println("Auth Event Received");
        System.out.println("User ID: " + event.getUserId());
        System.out.println("Email: " + event.getEmail());
        System.out.println("Type: " + event.getEventType());
    }
}
