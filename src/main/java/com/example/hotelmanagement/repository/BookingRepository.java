package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // check if booking exists
    boolean existsById(long id);

    // prevent overlapping bookings for the same room
    @Query("SELECT b FROM Booking b JOIN b.rooms r WHERE r = :room AND b.bookingStatus != 'CANCELLED' AND NOT (b.checkOutDate < :checkInDate OR b.checkInDate > :checkOutDate)")
    List<Booking> findOverlappingBookingsForRoom(@Param("room") Room room,
                                                 @Param("checkInDate") LocalDate checkInDate,
                                                 @Param("checkOutDate") LocalDate checkOutDate);
    // RELATED TO CLIENT:
    // show bookings made by specific client
    List<Booking> findByClient(Client client);
    // count how many bookings client made
    int countByClient(Client client);

    // show bookings handled by specific employee
    List<Booking> findByEmployee(Employee employee);

    // find booking by check-in dates
    List<Booking> findByCheckInDateBetween(LocalDate checkInDate, LocalDate checkInDate2);

    // find bookings by booking status (e.g. confirmed or cancelled)
    List<Booking> findByBookingStatus(BookingStatus bookingStatus);

}
