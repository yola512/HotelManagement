package com.example.hotelmanagement.repository;
import com.example.hotelmanagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    // object[0] - room id, object[1] - roomType, object[2] - number of bookings
    // used in reports.html
    @Query("SELECT r.id, r.roomType, COUNT(b.id) FROM Booking b JOIN b.rooms r GROUP BY r.id, r.roomType ORDER BY COUNT(b.id) DESC")
    List<Object[]> countBookingsByRoomId();
}
