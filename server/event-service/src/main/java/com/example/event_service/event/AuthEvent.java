package com.example.event_service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthEvent {

    private String userId;
    private String email;
    private String eventType; // REGISTERED, LOGIN, LOGOUT

}
