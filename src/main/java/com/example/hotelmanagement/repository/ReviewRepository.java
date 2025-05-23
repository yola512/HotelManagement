package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // reviews wrote by a specific client
    List<Review> findReviewByClientID(Long clientID);

    // reviews regarding specific room
    List<Review> findReviewByRoomID(int roomID);
    // number of reviews regarding specific room
    int countReviewByRoomID(int roomID);

    // reviews with a specific rating
    List<Review> findReviewByRating(double rating);

    // reviews with a specific date
    List<Review>  findReviewByDate(LocalDate date);
    List<Review>  findReviewByDateAfter(LocalDate date);
    List<Review>  findReviewByDateBefore(LocalDate date);
}
