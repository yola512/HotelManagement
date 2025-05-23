package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Room {
    @Id
    @GeneratedValue
    private int roomID;
    private double pricePerNight;
    private boolean isAvailable;
    private String description;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @ManyToMany
    private List<RoomFeature> roomFeatures = new ArrayList<>();

    public Room() {}

    public Room(double pricePerNight, boolean isAvailable, String description, RoomType roomType, List<RoomFeature> roomFeatures) {
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
        this.description = description;
        this.roomType = roomType;
        this.roomFeatures = roomFeatures;
    }

}
