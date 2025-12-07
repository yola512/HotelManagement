package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.*;
import com.example.hotelmanagement.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final RoomRepository roomRepository;
    private final MealRepository mealRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, ClientRepository clientRepository, RoomRepository roomRepository, MealRepository mealRepository, EmployeeRepository employeeRepository) {
        this.bookingRepository = bookingRepository;
        this.clientRepository = clientRepository;
        this.roomRepository = roomRepository;
        this.mealRepository = mealRepository;
        this.employeeRepository = employeeRepository;
    }

    public double calculateTotalCost(LocalDate checkInDate, LocalDate checkOutDate,
                                     List<Room> rooms, Meal meal) {

        if (checkInDate == null || checkOutDate == null || rooms == null || rooms.isEmpty() || meal == null) {
            throw new IllegalArgumentException("Check-in, check-out dates, rooms, and meal must be specified to calculate total cost.");
        }

        long numberOfNights = java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        if (numberOfNights <= 0) {
            throw new IllegalArgumentException("Check-out date must be strictly after check-in date.");
        }

        // room total cost
        double totalRoomPricePerNight = rooms.stream()
                .mapToDouble(Room::getPricePerNight)
                .sum();
        double totalRoomCost = totalRoomPricePerNight * numberOfNights;

        // meals total cost
        double totalMealPricePerDay = meal.getBreakfastPrice() + meal.getLunchPrice() + meal.getDinnerPrice();
        double totalMealCost = totalMealPricePerDay * numberOfNights;

        // booking total cost
        return totalRoomCost + totalMealCost;
    }

    @Transactional
    public Booking createBooking(Long clientId, List<Integer> roomIds, int mealId,
                                 LocalDate checkInDate, LocalDate checkOutDate,
                                 BookingStatus status, int employeeId) {

        // fetch related entities
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client with ID " + clientId + " not found."));

        List<Room> rooms = roomRepository.findAllById(roomIds);
        if (rooms.size() != roomIds.size()) {
            throw new IllegalArgumentException("One or more rooms specified were not found.");
        }

        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new IllegalArgumentException("Meal plan with ID " + mealId + " not found."));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee with ID " + employeeId + " not found."));


        // create and fill in Booking object
        Booking booking = new Booking();
        booking.setClient(client);
        booking.setRooms(rooms);
        booking.setMeal(meal);
        booking.setEmployee(employee);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setBookingStatus(status);

        // check if there are no collisions
        // null bc we don't exclude any booking, we do so only when we update existing booking
        checkRoomAvailability(rooms, checkInDate, checkOutDate, null);

        booking.setBookingDate(LocalDateTime.now());

        // calculate total booking cost
        double totalCost = calculateTotalCost(checkInDate, checkOutDate, rooms, meal);
        booking.setTotalPrice(totalCost);

        Booking savedBooking = bookingRepository.save(booking);

        // return full object (thanks to entityGraph)
        return bookingRepository.findById(savedBooking.getId())
                .orElseThrow(() -> new RuntimeException("Booking not found after save."));
    }

    @Transactional
    public Booking updateBooking(Long bookingId, Long clientId, List<Integer> roomIds, int mealId,
                                 LocalDate checkInDate, LocalDate checkOutDate,
                                 BookingStatus status, int employeeId) {

        // find exisxting booking
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking with ID " + bookingId + " not found."));

        // fetch related entities to the update
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client with ID " + clientId + " not found."));

        List<Room> rooms = roomRepository.findAllById(roomIds);
        if (rooms.size() != roomIds.size()) {
            throw new IllegalArgumentException("One or more rooms specified were not found.");
        }

        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new IllegalArgumentException("Meal plan with ID " + mealId + " not found."));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee with ID " + employeeId + " not found."));


        // we have to exclude booking with bookingId to make sure admin is allowed to update e.g. only status without error saying that the room is already taken in those dates
        checkRoomAvailability(rooms, checkInDate, checkOutDate, bookingId);

        // update fields
        existingBooking.setClient(client);
        existingBooking.setRooms(rooms);
        existingBooking.setMeal(meal);
        existingBooking.setEmployee(employee);
        existingBooking.setCheckInDate(checkInDate);
        existingBooking.setCheckOutDate(checkOutDate);
        existingBooking.setBookingStatus(status);
        // bookingDate is set only once (when it's created)


        // calculate total booking cost
        double totalCost = calculateTotalCost(checkInDate, checkOutDate, rooms, meal);
        existingBooking.setTotalPrice(totalCost);

        Booking updatedBooking = bookingRepository.save(existingBooking);

        // return full object thanks to EntityGraph
        return bookingRepository.findById(updatedBooking.getId())
                .orElseThrow(() -> new RuntimeException("Booking not found after update."));
    }

    @Transactional
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    // check overlapping bookings
    private void checkRoomAvailability(List<Room> rooms, LocalDate checkInDate, LocalDate checkOutDate, Long bookingIdToExclude) {
        for (Room room : rooms) {
            List<Booking> overlappingBookings = bookingRepository.findOverlappingBookingsForRoom(
                    room, checkInDate, checkOutDate, bookingIdToExclude
            );
            if (!overlappingBookings.isEmpty()) {
                // exception if there's collision
                throw new IllegalArgumentException(
                        "Room ID " + room.getId() + " is already booked during the selected period."
                );
            }
        }
    }
    // business queries
    public List<Booking> getBookingsByMealPlan(MealPlan plan) {
        return bookingRepository.findByMeal_MealPlanName(plan);
    }

    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByBookingStatus(status);
    }

    public List<Booking> getBookingsByCheckInDate(LocalDate date) {
        return bookingRepository.findByCheckInDate(date);
    }

    public List<Booking> getBookingsByCheckOutDate(LocalDate date) {
        return bookingRepository.findByCheckOutDate(date);
    }

    public List<Booking> getBookingsByRoom(int roomId) {
        return bookingRepository.findByRooms_Id(roomId);
    }

    public List<Booking> getBookingsByClientNameSurname(@PathVariable String searchText) {
        return bookingRepository.findByClientNameSurname(searchText);
    }

    // get all bookings - admin only
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

}
