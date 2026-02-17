package com.example.user_service.dto;


import java.time.LocalDateTime;
import java.util.UUID;

public record UserProfileResponse(
        UUID userId,
        String email,
        String status,
        LocalDateTime createdAt,
        LocalDateTime lastLoginAt
) {}