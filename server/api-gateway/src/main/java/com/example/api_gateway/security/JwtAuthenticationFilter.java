package com.example.api_gateway.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // ✅ Allow public endpoints (NO JWT REQUIRED)
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ✅ Read Authorization Header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing Authorization Header");
            return;
        }

        String token = authHeader.substring(7);

        try {
            // ✅ Validate Token
            Claims claims = jwtUtil.validateToken(token);

            String userId = claims.getSubject();
            String email = claims.get("email", String.class);

            // ✅ Forward user info to downstream services
            HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(request) {

                @Override
                public String getHeader(String name) {
                    if ("X-User-Id".equals(name)) {
                        return userId;
                    }
                    if ("X-User-Email".equals(name)) {
                        return email;
                    }
                    return super.getHeader(name);
                }
            };

            // Continue request with injected headers
            filterChain.doFilter(wrappedRequest, response);

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT Token");
        }
    }
}