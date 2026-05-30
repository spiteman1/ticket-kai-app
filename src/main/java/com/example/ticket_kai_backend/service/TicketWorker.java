package com.example.ticket_kai_backend.service;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TicketWorker {

    private final EventService eventService;

    public TicketWorker(EventService eventService) {
        this.eventService = eventService;
    }

    @RabbitListener(queuesToDeclare = @Queue("ticketQueue"))
    public void handleConfirm(String message) {
        String[] parts = message.split(":");
        String reservationId = parts[0];
        Long id = Long.parseLong(parts[1]);
        
        try {
            eventService.confirmPurchase(reservationId, id);
            System.out.println("Successfully processed purchase for reservation: " + reservationId);
        } catch (Exception e) {
            System.err.println("Failed to process purchase: " + e.getMessage());
        }
    }
}