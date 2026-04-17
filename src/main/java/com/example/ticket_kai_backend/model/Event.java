package com.example.ticket_kai_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "events")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private LocalDateTime eventDate;
    private int totalTickets;
    private int availableTickets;

    
    public Event() {}

    public Event(String name, LocalDateTime eventDate, int totalTickets) {
        this.name = name;
        this.eventDate = eventDate;
        this.totalTickets = totalTickets;
        this.availableTickets = totalTickets;
        
    }

   
}