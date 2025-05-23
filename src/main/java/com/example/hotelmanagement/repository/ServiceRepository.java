package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    // services by name
    List<Service> findServiceByName(String name);

    // services cheaper than specific price
    List<Service> findServiceByPriceCheaperThan(double price);

}
