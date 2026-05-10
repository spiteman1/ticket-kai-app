package com.example.ticket_kai_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private String customerName;
    private LocalDateTime purchaseTime;

    
    public Ticket() {}

    public Ticket(Event event, String customerName) {
        this.event = event;
        this.customerName = customerName;
        this.purchaseTime = LocalDateTime.now();
    }
}
