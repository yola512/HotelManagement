package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // CRUD
    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable int id) {
        return roomService.getRoomById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    // only admin can create/update/delete
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable int id, @RequestBody Room updatedRoom) {
        return roomService.updateRoom(id, updatedRoom);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
    }

    // BUSINESS QUERIES
    @GetMapping("/filter")
    public List<Room> filterRooms(@RequestParam(required=false) String type,
                                  @RequestParam(required = false) String features,
                                  @RequestParam(required = false) Boolean availability,
                                  @RequestParam(required = false) String priceOrder) {
        List<String> featureList = (features != null && !features.isEmpty())
                ? Arrays.asList(features.split(","))
                : Collections.emptyList();

        return roomService.filterRooms(type, featureList, availability, priceOrder);
    }

}