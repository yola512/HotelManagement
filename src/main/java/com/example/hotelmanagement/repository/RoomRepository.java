package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.RoomFeature;
import com.example.hotelmanagement.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    // available rooms
    List<Room> findRoomByAvailableTrue();

    // available rooms of specific Room Type
    List<Room> findRoomByRoomType(RoomType roomType);

    // rooms with specific feature
    @Query("SELECT r FROM Room r JOIN r.roomFeatures f WHERE f.name = :featureName")
    List<Room> findRoomByFeature(@Param("featureName") RoomFeature featureName);
}
