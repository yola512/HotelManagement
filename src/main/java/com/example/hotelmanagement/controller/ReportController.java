
package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.model.*;
import com.example.hotelmanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// to-do
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    // although normally it's recommended to:
    // - let controller handle http requests and delegate tasks, communicating ONLY with the service layer
    // - service should contain business logic, validations etc; service communicates with the repository layer
    // - repository communicates with database directly
    // however, since those are for statistical reports only and involve no data validation, complicated calculations or other business logic
    // we can simplify the architecture by calling the repository directly instead of going through service which will delegate the call
    private final RoomRepository roomRepository;
    private final EmployeeRepository employeeRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public ReportController(RoomRepository roomRepository, EmployeeRepository employeeRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.employeeRepository = employeeRepository;
        this.bookingRepository = bookingRepository;
    }

    // EMPLOYEES REPORTS
    @GetMapping("/job-titles")
    public List<Map<String, Object>> getEmployeeJobTitleStats() {
        List<Object[]> rawStats = employeeRepository.countEmployeesByJobTitle();

        //convert to a map list to return 'nice' JSON
        return rawStats.stream()
                .map(row -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jobTitle", ((JobTitle) row[0]).toString());
                    map.put("count", (Long) row[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }

    // ROOM REPORTS
    @GetMapping("/popular-rooms")
    public List<Map<String, Object>> getPopularRoomsStats() {
        List<Object[]> rawStats = roomRepository.countBookingsByRoomId();

        //convert to a map list to return 'nice' JSON
        return rawStats.stream()
                .map(row -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("roomId", (int) row[0]);
                    map.put("roomType", ((RoomType) row[1]).toString()); // enum to string
                    map.put("count", (Long) row[2]);
                    return map;
                }).collect(Collectors.toList());
    }

    // BOOKINGS REPORTS
    @GetMapping("/bookings-monthly")
    public List<Map<String, Object>> getBookingsMonthlyStats() {
        List<Object[]> rawStats = bookingRepository.countMonthlyBookings();

        //convert to a map list to return 'nice' JSON
        return rawStats.stream()
                .map(row -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("monthYear", (String) row[0]);
                    map.put("count", (Long) row[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/employees/salary/{x}/{val}")
    public List<Employee> fetchEmployeesBySalary(
            @PathVariable String x,
            @PathVariable double val) {

        if (x.equalsIgnoreCase("greater-than")) {
            return employeeRepository.findEmployeeBySalaryGreaterThan(val);
        } else if (x.equalsIgnoreCase("less-than")) {
            return employeeRepository.findEmployeeBySalaryLessThan(val);
        } else {
            throw new IllegalArgumentException("ERROR");
        }
    }
}