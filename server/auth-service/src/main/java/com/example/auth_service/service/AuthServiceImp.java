package com.example.auth_service.service;

import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.LoginResponse;
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.dto.RegisterResponse;
import com.example.auth_service.entity.User;
import com.example.auth_service.repository.UserRepository;
import com.example.event_service.event.AuthEvent;
import com.example.auth_service.messaging.event.publisher.AuthEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthEventPublisher eventPublisher;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Email already registered");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        User saved = userRepository.save(user);

        eventPublisher.publish(
                new AuthEvent(
                        saved.getId().toString(),
                        saved.getEmail(),
                        "REGISTERED"
                )
        );

        return new RegisterResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail()
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalStateException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalStateException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                user.getId().toString(),
                user.getEmail()
        );

        eventPublisher.publish(
                new AuthEvent(
                        user.getId().toString(),
                        user.getEmail(),
                        "LOGGED IN"
                )
        );

        return new LoginResponse(token);
    }
}

