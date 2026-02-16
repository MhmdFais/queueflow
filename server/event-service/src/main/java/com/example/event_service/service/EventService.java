package com.example.event_service.service;

import com.example.event_service.client.UserServiceClient;
import com.example.event_service.dto.UserResponse;
import com.example.event_service.model.Event;
import com.example.event_service.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserServiceClient userServiceClient;

    public Event createEvent(Event event, String token) {
        UserResponse user = userServiceClient.getUserById(event.getOrganizerId(), token);

        if (user == null) {
            throw new IllegalArgumentException("Organizer not found");
        }

        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}