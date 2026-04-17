package com.example.ticket_kai_backend.service;

import com.example.ticket_kai_backend.model.Event;
import com.example.ticket_kai_backend.repository.EventRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class EventService {

    private final EventRepository eventRepository;

    
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}