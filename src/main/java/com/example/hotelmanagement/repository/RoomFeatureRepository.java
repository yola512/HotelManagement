package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.RoomFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomFeatureRepository extends JpaRepository<RoomFeature, Integer> {
}
