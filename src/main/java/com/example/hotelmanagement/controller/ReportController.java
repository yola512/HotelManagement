
package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.model.Booking;
import com.example.hotelmanagement.model.BookingStatus;
import com.example.hotelmanagement.model.Client;
import com.example.hotelmanagement.model.Employee;
import com.example.hotelmanagement.model.Review;
import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.repository.ClientRepository;
import com.example.hotelmanagement.repository.EmployeeRepository;
import com.example.hotelmanagement.repository.ReviewRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.service.BookingService;
import com.example.hotelmanagement.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private final BookingService bookingService;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final RoomRepository roomRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReportController(BookingService bookingService, ClientRepository clientRepository,
                            EmployeeRepository employeeRepository, RoomRepository roomRepository, ReviewRepository reviewRepository) {
        this.bookingService = bookingService;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.roomRepository = roomRepository;
        this.reviewRepository = reviewRepository;
    }

    // FOR NOW, REPORTS WILL BE ONLY VISIBLE TO ADMIN

    // BOOKING REPORTS
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bookings/client/{clientId}")
    public ResponseEntity<List<Booking>> getBookingsByClient(@PathVariable Long clientId) {
        logger.debug("Fetching bookings for client ID: {}", clientId);
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        if (clientOpt.isEmpty()) {
            logger.warn("Client ID {} not found", clientId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Booking> bookings = bookingService.getBookingsByClient(clientOpt.get());
        return ResponseEntity.ok(bookings);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bookings/client/{clientId}/count")
    public ResponseEntity<Integer> countBookingsByClient(@PathVariable Long clientId) {
        logger.debug("Counting bookings for client ID: {}", clientId);
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        if (clientOpt.isEmpty()) {
            logger.warn("Client ID {} not found", clientId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        int count = bookingService.countBookingsByClient(clientOpt.get());
        return ResponseEntity.ok(count);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bookings/employee/{employeeId}")
    public ResponseEntity<List<Booking>> getBookingsByEmployee(@PathVariable int employeeId) {
        logger.debug("Fetching bookings for employee ID: {}", employeeId);
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            logger.warn("Employee ID {} not found", employeeId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Booking> bookings = bookingService.getBookingsByEmployee(employeeOpt.get());
        return ResponseEntity.ok(bookings);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bookings/status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable BookingStatus status) {
        logger.debug("Fetching bookings with status: {}", status);
        List<Booking> bookings = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(bookings);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bookings/date-range")
    public ResponseEntity<List<Booking>> getBookingsInDateRange(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        logger.debug("Fetching bookings from {} to {}", from, to);
        if (to.isBefore(from)) {
            logger.warn("Invalid date range: to ({}) is before from ({})", to, from);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<Booking> bookings = bookingService.getBookingsInDateRange(from, to);
        return ResponseEntity.ok(bookings);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bookings/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        logger.debug("Checking if booking ID {} exists", id);
        boolean exists = bookingService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    // EMPLOYEE REPORTS
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/employees/salary/greater-than/{salary}")
    public ResponseEntity<List<Employee>> getEmployeesBySalaryGreaterThan(@PathVariable double salary) {
        logger.debug("Fetching employees with salary greater than: {}", salary);
        if (salary < 0) {
            logger.warn("Invalid salary: {}", salary);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<Employee> employees = employeeRepository.findEmployeeBySalaryGreaterThan(salary);
        return ResponseEntity.ok(employees);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/employees/salary/less-than/{salary}")
    public ResponseEntity<List<Employee>> getEmployeesBySalaryLessThan(@PathVariable double salary) {
        logger.debug("Fetching employees with salary less than: {}", salary);
        if (salary < 0) {
            logger.warn("Invalid salary: {}", salary);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<Employee> employees = employeeRepository.findEmployeeBySalaryLessThan(salary);
        return ResponseEntity.ok(employees);
    }

    // REVIEW REPORTS
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reviews/client/{clientId}")
    public ResponseEntity<List<Review>> getReviewsByClient(@PathVariable Long clientId) {
        logger.debug("Fetching reviews for client ID: {}", clientId);
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        if (clientOpt.isEmpty()) {
            logger.warn("Client ID {} not found", clientId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Review> reviews = reviewRepository.findByClient(clientOpt.get());
        return ResponseEntity.ok(reviews);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reviews/room/{roomId}")
    public ResponseEntity<List<Review>> getReviewsByRoom(@PathVariable int roomId) {
        logger.debug("Fetching reviews for room ID: {}", roomId);
        Optional<Room> roomOpt = roomRepository.findById(roomId);
        if (roomOpt.isEmpty()) {
            logger.warn("Room ID {} not found", roomId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Review> reviews = reviewRepository.findByRoom(roomOpt.get());
        return ResponseEntity.ok(reviews);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reviews/room/{roomId}/count")
    public ResponseEntity<Integer> countReviewsByRoom(@PathVariable int roomId) {
        logger.debug("Counting reviews for room ID: {}", roomId);
        Optional<Room> roomOpt = roomRepository.findById(roomId);
        if (roomOpt.isEmpty()) {
            logger.warn("Room ID {} not found", roomId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        int count = reviewRepository.countByRoom(roomOpt.get());
        return ResponseEntity.ok(count);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reviews/rating/{rating}")
    public ResponseEntity<List<Review>> getReviewsByRating(@PathVariable double rating) {
        logger.debug("Fetching reviews with rating: {}", rating);
        if (rating < 1 || rating > 5) {
            logger.warn("Invalid rating: {}", rating);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<Review> reviews = reviewRepository.findByRating(rating);
        return ResponseEntity.ok(reviews);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reviews/date/{date}")
    public ResponseEntity<List<Review>> getReviewsByDate(@PathVariable LocalDate date) {
        logger.debug("Fetching reviews for date: {}", date);
        List<Review> reviews = reviewRepository.findByReviewDate(date);
        return ResponseEntity.ok(reviews);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reviews/after/{date}")
    public ResponseEntity<List<Review>> getReviewsAfterDate(@PathVariable LocalDate date) {
        logger.debug("Fetching reviews after date: {}", date);
        List<Review> reviews = reviewRepository.findByReviewDateAfter(date);
        return ResponseEntity.ok(reviews);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reviews/before/{date}")
    public ResponseEntity<List<Review>> getReviewsBeforeDate(@PathVariable LocalDate date) {
        logger.debug("Fetching reviews before date: {}", date);
        List<Review> reviews = reviewRepository.findByReviewDateBefore(date);
        return ResponseEntity.ok(reviews);
    }

}