package com.example.hotelmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter @Setter
public class Client {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;

    public Client() {}

    public Client(String name, String surname, LocalDate birthDate, String email, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}

