package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Service {
    @Id
    @GeneratedValue
    private int serviceID;
    private String serviceName;
    private double price;

    public Service() {}

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }
}
