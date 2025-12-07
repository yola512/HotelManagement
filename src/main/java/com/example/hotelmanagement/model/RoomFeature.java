package com.example.hotelmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Setter;
import lombok.Getter;

@Entity
@Getter @Setter
public class RoomFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name; // AIR_CONDITIONING, FRIDGE etc
    private boolean active = true;

    public RoomFeature() {}

    public RoomFeature(String name) {
        this.name = name;
    }

}
