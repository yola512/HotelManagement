package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.model.Review;
import com.example.hotelmanagement.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // ADMIN: get all reviews
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }


}