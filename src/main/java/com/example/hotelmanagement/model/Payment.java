package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Payment {
    @Id
    @GeneratedValue
    private long paymentID;
    private double amount;
    private LocalDateTime paymentDate;
    private boolean isCompleted;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne
    @JoinColumn(name="bookingID")
    private Booking booking;

    public Payment() {}

    public Payment(double amount, LocalDateTime paymentDate, boolean isCompleted,
                   PaymentMethod paymentMethod, Booking booking) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.isCompleted = isCompleted;
        this.paymentMethod = paymentMethod;
        this.booking = booking;
    }
}
