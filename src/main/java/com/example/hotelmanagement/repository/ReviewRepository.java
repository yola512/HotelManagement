package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Client;
import com.example.hotelmanagement.model.Review;
import com.example.hotelmanagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // reviews wrote by a specific client
    List<Review> findByClient(Client client);

    // reviews regarding specific room
    List<Review> findByRoom(Room room);
    // number of reviews regarding specific room
    int countByRoom(Room room);

    // reviews with a specific rating
    List<Review> findByRating(double rating);

    // reviews with a specific date
    List<Review>  findByReviewDate(LocalDate reviewDate);
    List<Review>  findByReviewDateAfter(LocalDate reviewDate);
    List<Review>  findByReviewDateBefore(LocalDate reviewDate);
}
