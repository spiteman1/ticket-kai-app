package com.example.ticket_kai_backend.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ReservationService {
private final StringRedisTemplate redisTemplate;

    public ReservationService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String holdTicket(Long eventId, String customerName) {
        
        String reservationId = UUID.randomUUID().toString();
        String redisKey = "event:" + eventId + ":hold:" + reservationId;
        redisTemplate.opsForValue().set(redisKey, customerName, 5, TimeUnit.MINUTES);

       
        return reservationId;
    }
}
