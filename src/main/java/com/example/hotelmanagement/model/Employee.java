package com.example.hotelmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Employee {
    @Id
    @GeneratedValue
    private long employeeID;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private JobTitle jobTitle;
    private double salary;

    public Employee() {}

    public Employee(String name, String surname, LocalDate birthDate, String email,
                    String phoneNumber, JobTitle jobTitle, double salary) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.jobTitle = jobTitle;
        this.salary = salary;
    }
}
