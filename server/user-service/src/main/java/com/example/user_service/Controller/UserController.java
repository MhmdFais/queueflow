package com.example.user_service.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/users/me")
    public String getCurrentUser(
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Email") String email
    ) {
        return "Authenticated User -> ID: " + userId + " Email: " + email;
    }
}