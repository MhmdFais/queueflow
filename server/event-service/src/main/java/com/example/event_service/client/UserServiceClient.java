package com.example.event_service.client;

import com.example.event_service.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserServiceClient {

    @Autowired
    private WebClient webClient;

    private final String USER_SERVICE_URL = "http://localhost:8082/api/users";

    public UserResponse getUserById(Long userId, String token) {
        return webClient.get()
                .uri(USER_SERVICE_URL + "/" + userId)
                .header(HttpHeaders.AUTHORIZATION, token) // 🔥 Forward JWT
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();
    }
}