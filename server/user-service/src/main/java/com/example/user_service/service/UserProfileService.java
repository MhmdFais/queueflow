package com.example.user_service.service;

import com.example.user_service.dto.UserProfileResponse;

import java.util.List;
import java.util.UUID;

public interface UserProfileService {

    UserProfileResponse getUserById(UUID userId);

    List<UserProfileResponse> getAllUsers();
}