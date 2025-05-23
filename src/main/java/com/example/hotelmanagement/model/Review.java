package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Review {
    @Id
    @GeneratedValue
    private long reviewID;
    private double rating; // 1.00 - 10.00
    private String comment;
    private LocalDate reviewDate;

    @ManyToOne
    @JoinColumn(name="bookingID")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name="clientID")
    private Client client;

    public Review() {}

    public Review(double rating, String comment, LocalDate reviewDate, Booking booking, Client client) {
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.booking = booking;
        this.client = client;
    }
}
