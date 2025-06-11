package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.model.Booking;
import com.example.hotelmanagement.model.BookingStatus;
import com.example.hotelmanagement.model.Client;
import com.example.hotelmanagement.model.Employee;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.ClientRepository;
import com.example.hotelmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // === CRUD ===
    // get all bookings - admin only
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // get bookings for a specific client
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/myBookings")
    public ResponseEntity<List<Booking>> getBookingsForCurrentClient(Principal principal) {
        try {
            List<Booking> bookings = bookingService.getBookingsForClient(principal.getName());
            return ResponseEntity.ok(bookings);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // client: create their own booking
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking, Principal principal) {
        try {
            // Set client based on authenticated user
            Client client = clientRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            booking.setClient(client);
            Booking savedBooking = bookingService.createBooking(booking);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // admin: update booking (e.g. if client made a mistake and calls the hotel)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        try {
            Booking updatedBooking = bookingService.updateBooking(id, booking);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
