package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.model.*;
import com.example.hotelmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // BUSINESS QUERIES
    @GetMapping("/plan/{plan}")
    public List<Booking> getBookingsByMealPlan(@PathVariable MealPlan plan) {
        return bookingService.getBookingsByMealPlan(plan);
    }
    @GetMapping("/checkin/{date}")
    // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) informs Spring to suspect YYYY-MM-DD format and convert it correctly to LocalDate;
    // without it path variable 'date' is not consumed
    // used in filtering
    public List<Booking> getBookingsByCheckInDate(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate) {
        return bookingService.getBookingsByCheckInDate(checkInDate);
    }
    @GetMapping("/checkout/{date}")
    public List<Booking> getBookingsByCheckOutDate(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        return bookingService.getBookingsByCheckOutDate(checkOutDate);
    }
    @GetMapping("/room/{room}")
    public List<Booking> getBookingsByRoom(@PathVariable int room) {
        return bookingService.getBookingsByRoom(room);
    }
    @GetMapping("/status/{status}")
    public List<Booking> getBookingsByStatus(@PathVariable BookingStatus status) {
        return bookingService.getBookingsByStatus(status);
    }
    @GetMapping("/client/{searchText}")
    public List<Booking> getBookingsByClientNameSurname(@PathVariable String searchText) {
        return bookingService.getBookingsByClientNameSurname(searchText);
    }

    // === CRUD ===
    // get all bookings - admin only
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // get booking by ID
    @GetMapping("/{id}")
    public Optional<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    // ResponseEntity<?> bc addBooking has to return ResponseEntity<Booking> (if successful) or ResponseEntity<String> (if there's an error)
    public ResponseEntity<?> addBooking(@RequestBody Booking booking) {
        if (booking.getClient() == null || booking.getRooms() == null || booking.getRooms().isEmpty() || booking.getMeal() == null || booking.getEmployee() == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<Integer> roomIds = booking.getRooms().stream()
                    .map(Room::getId)
                    .collect(Collectors.toList());

            int mealId = booking.getMeal().getId();
            int employeeId = booking.getEmployee().getId();
            Long clientId = booking.getClient().getId();

            Booking newBooking = bookingService.createBooking(
                    clientId,
                    roomIds,
                    mealId,
                    booking.getCheckInDate(),
                    booking.getCheckOutDate(),
                    booking.getBookingStatus(),
                    employeeId
            );
            // 201 created
            return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // custom error for error pop-up
            final String CUSTOM_ERROR_MESSAGE = "Can't add booking. Choosen room(s) are either taken or dates are incorrect.";
            // overlapping date, no id, room taken etc
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CUSTOM_ERROR_MESSAGE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        if (booking.getClient() == null || booking.getRooms() == null || booking.getRooms().isEmpty() || booking.getMeal() == null || booking.getEmployee() == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<Integer> roomIds = booking.getRooms().stream()
                    .map(Room::getId)
                    .collect(Collectors.toList());

            int mealId = booking.getMeal().getId();
            int employeeId = booking.getEmployee().getId();
            Long clientId = booking.getClient().getId();

            Booking updatedBooking = bookingService.updateBooking(
                    id,
                    clientId,
                    roomIds,
                    mealId,
                    booking.getCheckInDate(),
                    booking.getCheckOutDate(),
                    booking.getBookingStatus(),
                    employeeId
            );
            return ResponseEntity.ok(updatedBooking);
        } catch (IllegalArgumentException e) {
            // custom error for error pop-up
            final String CUSTOM_ERROR_MESSAGE = "Can't edit booking. Choosen room(s) are either taken or dates are incorrect.";
            // overlapping date, no id, room taken etc
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CUSTOM_ERROR_MESSAGE);
        } catch (RuntimeException e) {
            // if e.g. bookingRepository.findByIdWithDetails won't find an object
            return ResponseEntity.notFound().build();
        }
    }

    // admin: delete booking
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
