package com.example.user_service.service;

import com.example.user_service.dto.UserProfileResponse;
import com.example.user_service.entity.UserProfile;
import com.example.user_service.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository repository;

    @Override
    public UserProfileResponse getUserById(UUID userId) {

        UserProfile user = repository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public List<UserProfileResponse> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private UserProfileResponse mapToResponse(UserProfile user) {
        return new UserProfileResponse(
                user.getUserId(),
                user.getEmail(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getLastLoginAt()
        );
    }
}
