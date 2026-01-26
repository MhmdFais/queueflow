package com.example.auth_service.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

@Service
public class JwtService {

    private final String secret;
    private final long expiration;

    public JwtService(Environment env) {
        this.secret = env.getProperty("jwt.secret");
        this.expiration = Long.parseLong(Objects.requireNonNull(env.getProperty("jwt.expiration")));
    }

    public String generateToken(String userId, String email) {

        return Jwts.builder()
                .subject(userId)
                .claim("email", email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}
