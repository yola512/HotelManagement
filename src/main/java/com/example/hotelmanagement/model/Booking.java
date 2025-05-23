package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Booking {
    @Id
    @GeneratedValue
    private long bookingID;
    private LocalDateTime bookingDate;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Meal meal;

    @ManyToMany
    private List<Room> rooms; // there can be multiple rooms on 1 booking

    @ManyToMany
    private List<Service> services = new ArrayList<>();

    public Booking() {}

    public Booking(LocalDateTime bookingDate, LocalDateTime checkInDate, LocalDateTime checkOutDate, double totalPrice, BookingStatus bookingStatus, List<Room> rooms,
                   Client client, Employee employee, Meal meal, List<Service> services) {
        this.bookingDate = bookingDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingStatus = bookingStatus;
        this.rooms = rooms;
        this.client = client;
        this.meal = meal;
        this.totalPrice = totalPrice;
        this.services = services;
    }
}
