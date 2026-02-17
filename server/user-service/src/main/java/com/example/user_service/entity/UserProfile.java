package com.example.user_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    private UUID userId;

    @Column(nullable = false, unique = true)
    private String email;

    private String status; // ACTIVE, LOGGED_IN

    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
}
