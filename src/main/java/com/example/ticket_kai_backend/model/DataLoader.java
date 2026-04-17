package com.example.ticket_kai_backend.model;


import com.example.ticket_kai_backend.model.Event;
import com.example.ticket_kai_backend.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;


@Component
public class DataLoader implements CommandLineRunner {

    private final EventRepository eventRepository;

    public DataLoader(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) throws Exception {
       
        eventRepository.deleteAll();

  
        eventRepository.save(new Event("Demon Slayer: Infinity Castle Premiere", LocalDateTime.now().plusDays(5), 100));
        eventRepository.save(new Event("Glastonbury 2026", LocalDateTime.now().plusMonths(2), 5000));
        
        System.out.println("Dummy data loaded successfully! 🎟️");
    }
}