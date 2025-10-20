//package com.example.hotelmanagement.controller;
//
//import com.example.hotelmanagement.model.Booking;
//import com.example.hotelmanagement.model.BookingStatus;
//import com.example.hotelmanagement.repository.BookingRepository;
//import com.example.hotelmanagement.repository.MealRepository;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    private final BookingRepository bookingRepository;
//    private final MealRepository mealRepository;
//
//    public AdminController(BookingRepository bookingRepository, MealRepository mealRepository) {
//        this.bookingRepository = bookingRepository;
//        this.mealRepository = mealRepository;
//    }
//
//    @GetMapping("/home")
//    public String home() {
//        return "home";
//    }
//
//    @GetMapping("/bookings")
//    public String bookingsPage() {
//        return "bookings"; // resolves to src/main/resources/static/bookings.html
//    }
//
//
//}