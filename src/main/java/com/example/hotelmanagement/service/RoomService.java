package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    // prevent overlapping bookings for the same room
    public boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        if (!room.isAvailable()) {
            return false;
        }
        return bookingRepository.findOverlappingBookingsForRoom(room, checkInDate, checkOutDate).isEmpty();
    }

    // === CRUD ===
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(int id) {
        return roomRepository.findById(id);
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(int id, Room updatedRoom) {
        return roomRepository.findById(id).map(room -> {
            room.setAvailable(updatedRoom.isAvailable());
            room.setPricePerNight(updatedRoom.getPricePerNight());
            room.setDescription(updatedRoom.getDescription());
            room.setRoomType(updatedRoom.getRoomType());
            room.setRoomFeatures(updatedRoom.getRoomFeatures());
            return roomRepository.save(room);
        }).orElseThrow(() -> new RuntimeException("Room not found with id " + id));
    }

    public void deleteRoom(int id) {
        roomRepository.deleteById(id);
    }

    public List<Room> filterRooms(String type, List<String> features, Boolean availability, String priceOrder) {
        List<Room> allRooms = roomRepository.findAll();

        return allRooms.stream()
                .filter(room -> {
                    boolean matchesType = (type == null || type.isEmpty()) ||
                            room.getRoomType().name().equalsIgnoreCase(type);

                    boolean matchesFeatures = (features == null || features.isEmpty()) ||
                            features.stream().allMatch(f -> {
                                String normalizedFeature = normalize(f);
                                return room.getRoomFeatures().stream()
                                           .anyMatch(rf -> normalize(rf.getName()).equals(normalizedFeature));
                            });

                    boolean matchesAvailability = (availability == null || room.isAvailable() == availability);

                    return matchesType && matchesFeatures && matchesAvailability;
                })
                .sorted((r1, r2) -> {
                    if ("desc".equalsIgnoreCase(priceOrder)) {
                        return Double.compare(r2.getPricePerNight(), r1.getPricePerNight());
                    } else {
                        return Double.compare(r1.getPricePerNight(), r2.getPricePerNight());
                    }
                })
                .toList();
    }

    // removes space, "-", ",", "_", "." and capitalises
    private String normalize(String text) {
        if (text == null) return "";
        return text
                .trim()
                .replaceAll("[\\s\\-_,.]+", "")
                .toUpperCase();
    }

}
