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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double pricePerNight;
    private boolean available;
    private String description;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    // cascade - an operation (like save or update (hibernate doesn't allow for delete in @ManyToMany relationships with cascade) performed on the parent entity
    // is automatically extended to the associated child entities
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "room_room_feature", // name of linking table
            joinColumns = @JoinColumn(name = "room_id"), // column with foreign key to Room
            inverseJoinColumns = @JoinColumn(name="feature_id") // column with foreign key to RoomFeature
    )
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
