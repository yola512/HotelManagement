package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Payment;
import com.example.hotelmanagement.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // find payments which are unpaid
    List<Payment> findPaymentByIsCompletedFalse(boolean isCompleted);

    // see how many payments where made based on payment method
    long countPaymentByPaymentMethod(PaymentMethod method);

    // find payments made after specific date
    List<Payment> findPaymentByDateAfter(LocalDate paymentDate);

    // payment regarding specific booking
    List<Payment> findPaymentByBookingID(long bookingID);
}
