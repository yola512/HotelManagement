package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.*;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, ClientRepository clientRepository) {
        this.bookingRepository = bookingRepository;
        this.clientRepository = clientRepository;
    }

    public Booking saveBooking(Booking booking) {
        // calculate number of days
        long numberOfDays = booking.getCheckOutDate().toEpochDay() - booking.getCheckInDate().toEpochDay();

        if (numberOfDays <= 0) {
            throw new IllegalArgumentException("Checkout date must be greater than or equal to checkin date");
        }

        // count rooms total cost
        List<Room> rooms = booking.getRooms();
        double roomCost = rooms.stream()
                .mapToDouble(Room::getPricePerNight)
                .sum() * numberOfDays;

        // count meal total cost
        Meal meal = booking.getMeal();
        double mealCost = (meal != null) ? meal.calculateMealsCost((int) numberOfDays) : 0.0;

        // set and save totalCost
        double totalCost = roomCost + mealCost;
        booking.setTotalPrice(totalCost);

        return bookingRepository.save(booking);
    }

    // CRUD
    // get all bookings - admin only
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // get bookings for CLIENT
    public List<Booking> getBookingsForClient(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return bookingRepository.findByClient(client);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking createBooking(Booking booking) {
        booking.setBookingDate(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {
        return bookingRepository.findById(id).map(existing -> {
            existing.setCheckInDate(updatedBooking.getCheckInDate());
            existing.setCheckOutDate(updatedBooking.getCheckOutDate());
            existing.setBookingStatus(updatedBooking.getBookingStatus());
            existing.setTotalPrice(updatedBooking.getTotalPrice());
            existing.setClient(updatedBooking.getClient());
            existing.setEmployee(updatedBooking.getEmployee());
            existing.setMeal(updatedBooking.getMeal());
            existing.setRooms(updatedBooking.getRooms());
            return bookingRepository.save(existing);
        }).orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + id));
    }


    // business queries
    public List<Booking> getBookingsByClient(Client client) {
        logger.debug("Fetching bookings for client ID: {}", client.getId());
        return bookingRepository.findByClient(client);
    }

    public int countBookingsByClient(Client client) {
        logger.debug("Counting bookings for client ID: {}", client.getId());
        return bookingRepository.countByClient(client);
    }

    public List<Booking> getBookingsByEmployee(Employee employee) {
        logger.debug("Fetching bookings for employee ID: {}", employee.getId());
        return bookingRepository.findByEmployee(employee);
    }

    public List<Booking> getBookingsByStatus(BookingStatus status) {
        logger.debug("Fetching bookings with status: {}", status);
        return bookingRepository.findByBookingStatus(status);
    }

    public List<Booking> getBookingsInDateRange(LocalDate from, LocalDate to) {
        logger.debug("Fetching bookings from {} to {}", from, to);
        return bookingRepository.findByCheckInDateBetween(from, to);
    }

    public boolean existsById(Long id) {
        logger.debug("Checking if booking ID {} exists", id);
        return bookingRepository.existsById(id);
    }


}
