package com.example.auth_service.dto;

import java.util.UUID;

public record RegisterResponse(
        UUID id,
        String name,
        String email
) {}