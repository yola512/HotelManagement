package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Booking;
import com.example.hotelmanagement.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // check if booking exists
    boolean existsByBookingId(Long bookingID);

    // RELATED TO CLIENT:
    // show bookings made by specific client
    List<Booking> findBookingByClientID(Long clientID);
    // count how many bookings client made
    List<Booking> countBookingsByClientID(Long clientID);

    // show bookings handled by specific employee
    List<Booking> findBookingByEmployeeID(Long employeeID);

    // find booking by check-in dates
    List<Booking> findBookingByCheckInDateBetween(Date checkInDate, Date checkOutDate);

    // find bookings by booking status (e.g. confirmed or cancelled)
    List<Booking> findBookingByBookingStatus(BookingStatus bookingStatus);

}
