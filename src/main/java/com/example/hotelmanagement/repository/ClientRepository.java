package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // find client based on clientID/phone number/email
    Optional<Client> findById(long id);
    Optional<Client> findByPhoneNumber(String phoneNumber);
    Optional<Client> findByEmail(String email);

    // find client with name + surname
    List<Client> findByNameAndSurname(String name, String surname);

    // check if client with this phone number exists in the database
    boolean existsByPhoneNumber(String phoneNumber);


}
