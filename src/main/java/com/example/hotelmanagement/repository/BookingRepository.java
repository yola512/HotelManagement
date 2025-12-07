package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // get full object
    @EntityGraph(attributePaths = {"client", "employee", "meal", "rooms"})
    Optional<Booking> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"client", "employee", "meal", "rooms"})
    List<Booking> findAll();

    // prevent overlapping bookings for the same room
    @Query("SELECT b FROM Booking b JOIN b.rooms r " +
            "WHERE r.id = :#{#room.id} AND " +
            "(:checkInDate < b.checkOutDate AND :checkOutDate > b.checkInDate) AND " +
            "(b.bookingStatus <> com.example.hotelmanagement.model.BookingStatus.CANCELLED) AND " +  // ignore cancelled bookings
            "(:bookingIdToExclude IS NULL OR b.id <> :bookingIdToExclude)") // exclude booking if we update booking
    List<Booking> findOverlappingBookingsForRoom(
            @Param("room") Room room,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("bookingIdToExclude") Long bookingIdToExclude);

    // used in reports.html
    // returns list of matrixes Object[], Object[0] - string "YYYY-MM"; Object[1] number of bookings in the month
    @Query(value = "SELECT FORMATDATETIME(b.booking_date, 'yyyy-MM') AS monthYear, COUNT(b.id) " +
            "FROM Booking b " +
            "GROUP BY FORMATDATETIME(b.booking_date, 'yyyy-MM') " +
            "ORDER BY monthYear ASC", nativeQuery = true)
    List<Object[]> countMonthlyBookings();

    // find bookings by meal included
    @EntityGraph(attributePaths = {"client", "employee", "meal", "rooms"})
    List<Booking> findByMeal(Meal meal);

    // find bookings by room
    @EntityGraph(attributePaths = {"client", "employee", "meal", "rooms"})
    List<Booking> findByRooms(Room room);

    @EntityGraph(attributePaths = {"client", "employee", "meal", "rooms"})
    List<Booking> findByBookingStatus(BookingStatus status);
    @EntityGraph(attributePaths = {"client", "employee", "meal", "rooms"})
    List<Booking> findByCheckInDate(LocalDate date);
    @EntityGraph(attributePaths = {"client", "employee", "meal", "rooms"})
    List<Booking> findByCheckOutDate(LocalDate date);
    @EntityGraph(attributePaths = {"client", "employee", "meal", "rooms"})
    List<Booking> findByRooms_Id(int roomId);
    @EntityGraph(attributePaths = {"client", "employee", "meal", "rooms"})
    List<Booking> findByMeal_MealPlanName(MealPlan plan);

    // used for filtering (find client that name + surname matches provided text)
    @Query("SELECT b FROM Booking b JOIN b.client c " +
            "WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(c.surname) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            // Name Surname
            "LOWER(CONCAT(c.name, ' ', c.surname)) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            // Surname Name
            "LOWER(CONCAT(c.name, ' ', c.surname)) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Booking> findByClientNameSurname(@PathVariable String searchText);


}
