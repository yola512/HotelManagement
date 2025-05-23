package com.example.hotelmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Setter;
import lombok.Getter;

@Entity
@Getter @Setter
public class RoomFeature {
    @Id
    @GeneratedValue
    private int roomFeatureID;

    private String name; // AIR_CONDITIONING, FRIDGE etc
    private String description;

    public RoomFeature() {}

    public RoomFeature(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
