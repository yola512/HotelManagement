package com.example.hotelmanagement.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double rating; // 1.00 - 10.00
    private String comment;
    private LocalDate reviewDate;

    @ManyToOne
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    public Review() {}

    public Review(double rating, String comment, LocalDate reviewDate, Booking booking, Room room, Client client) {
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.booking = booking;
        this.room = room;
        this.client = client;
    }
}
