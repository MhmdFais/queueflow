package com.example.auth_service.service;

import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
}