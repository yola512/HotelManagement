package com.example.hotelmanagement.model;
import com.example.hotelmanagement.repository.BookingRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Room {
    @Id
    @GeneratedValue
    private int id;
    private double pricePerNight; // in zlotys
    private boolean available;
    private String description;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @ManyToMany
    private List<RoomFeature> roomFeatures = new ArrayList<>();

    public Room() {}

    public Room(double pricePerNight, boolean available, String description, RoomType roomType, List<RoomFeature> roomFeatures) {
        this.pricePerNight = pricePerNight;
        this.available = available;
        this.description = description;
        this.roomType = roomType;
        this.roomFeatures = roomFeatures;
    }

}
