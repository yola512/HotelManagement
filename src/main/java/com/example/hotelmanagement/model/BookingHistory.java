package com.example.hotelmanagement.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BookingHistory {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Booking booking;

    private LocalDateTime timestamp;
    private String status;
}
