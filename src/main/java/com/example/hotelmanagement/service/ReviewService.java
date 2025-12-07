package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.Review;
import com.example.hotelmanagement.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // CRUD
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

}
