package com.example.ticket_kai_backend.controller;

import com.example.ticket_kai_backend.model.Event;
import com.example.ticket_kai_backend.service.EventService;
import com.example.ticket_kai_backend.service.ReservationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final ReservationService reservationService;
    private final EventService eventService;

    public EventController(EventService eventService,ReservationService reservationService) {
        this.eventService = eventService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Event> getAvailableEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/{id}/reserve")
public String reserve(@PathVariable Long id, @RequestParam String customerName) {
    String reservationId = reservationService.holdTicket(id, customerName);
    return "Ticket held! You have 5 minutes. Your Reservation ID is: " + reservationId;
}

@PostMapping("/{id}/confirm")
public String confirm(@PathVariable Long id, @RequestParam String reservationId) {
    try {
        eventService.confirmPurchase(reservationId, id);
        return "Purchase complete! Enjoy the show.";
    } catch (Exception e) {
        return "Error: " + e.getMessage();
    }
}


}