package com.example.user_service.messaging;

import com.example.user_service.entity.UserProfile;
import com.example.user_service.event.AuthEvent;
import com.example.user_service.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthEventListener {

    private final UserProfileRepository repository;

    @RabbitListener(queues = "auth.queue")
    public void handleAuthEvent(AuthEvent event) {

        UUID userId = UUID.fromString(event.getUserId());

        switch (event.getEventType()) {

            case "REGISTERED" -> {
                UserProfile profile = UserProfile.builder()
                        .userId(userId)
                        .email(event.getEmail())
                        .status("ACTIVE")
                        .createdAt(LocalDateTime.now())
                        .build();

                repository.save(profile);
            }

            case "LOGGED_IN" -> {
                repository.findById(userId).ifPresent(profile -> {
                    profile.setStatus("LOGGED_IN");
                    profile.setLastLoginAt(LocalDateTime.now());
                    repository.save(profile);
                });
            }
        }
    }
}