package com.example.ticket_kai_backend;


import com.example.ticket_kai_backend.model.Event;
import com.example.ticket_kai_backend.repository.EventRepository;
import com.example.ticket_kai_backend.repository.TicketRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;



@Component
public class DataLoader implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    public DataLoader(EventRepository eventRepository,TicketRepository ticketRepository) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void run(String... args) throws Exception {
       
        ticketRepository.deleteAll();
        eventRepository.deleteAll();

  
        eventRepository.save(new Event("Demon Slayer: Infinity Castle Premiere", LocalDateTime.now().plusDays(5), 100));
        eventRepository.save(new Event("Glastonbury 2026", LocalDateTime.now().plusMonths(2), 5000));
        
        System.out.println("Dummy data loaded successfully! 🎟️");
    }
}