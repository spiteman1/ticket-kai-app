package com.example.ticket_kai_backend.service;

import com.example.ticket_kai_backend.model.Event;
import com.example.ticket_kai_backend.model.Ticket;
import com.example.ticket_kai_backend.repository.EventRepository;
import com.example.ticket_kai_backend.repository.TicketRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final StringRedisTemplate redisTemplate;
    
    public EventService(EventRepository eventRepository,TicketRepository ticketRepository, StringRedisTemplate redisTemplate) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }


public Ticket confirmPurchase(String reservationId, Long eventId) {
    String redisKey = "event:" + eventId + ":hold:" + reservationId;
    
    
    String customerName = redisTemplate.opsForValue().get(redisKey);
    
    if (customerName == null) {
        throw new RuntimeException("Reservation expired or not found! You were too slow, play better.");
    }

   
    Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event vanished?!"));

    if (event.getAvailableTickets() <= 0) {
        throw new RuntimeException("Sold out while you were waiting!");
    }


    event.setAvailableTickets(event.getAvailableTickets() - 1);
    eventRepository.save(event);

    Ticket finalTicket = new Ticket(event, customerName);
    ticketRepository.save(finalTicket);


    redisTemplate.delete(redisKey);

    return finalTicket;
}


}