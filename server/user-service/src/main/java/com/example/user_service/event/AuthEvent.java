package com.example.user_service.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthEvent {

    private String userId;
    private String email;
    private String eventType;

    public AuthEvent() {}
}
