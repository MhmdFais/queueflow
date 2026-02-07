package com.example.user_service.Controller;

import com.example.user_service.dto.UserProfileResponse;
import com.example.user_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService service;

    @GetMapping("/{id}")
    public UserProfileResponse getUserById(@PathVariable UUID id) {
        return service.getUserById(id);
    }

    @GetMapping
    public List<UserProfileResponse> getAllUsers() {
        return service.getAllUsers();
    }
}
