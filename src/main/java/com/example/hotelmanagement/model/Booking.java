package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime bookingDate;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Meal meal;

    @ManyToMany
    private List<Room> rooms;

    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    public Booking() {}

    public Booking(LocalDateTime bookingDate, LocalDate checkInDate, LocalDate checkOutDate, List<Room> rooms,
                   Client client, Employee employee, Meal meal, double totalPrice, BookingStatus bookingStatus) {
        this.bookingDate = bookingDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.rooms = rooms;
        this.client = client;
        this.employee = employee;
        this.meal = meal;
        this.bookingStatus = bookingStatus;
    }
}
