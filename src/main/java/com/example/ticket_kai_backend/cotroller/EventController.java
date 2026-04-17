package com.example.ticket_kai_backend.cotroller;

import com.example.ticket_kai_backend.model.Event;
import com.example.ticket_kai_backend.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAvailableEvents() {
        return eventService.getAllEvents();
    }
}