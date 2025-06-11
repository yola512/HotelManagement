package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.Client;
import com.example.hotelmanagement.model.Review;
import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.repository.ClientRepository;
import com.example.hotelmanagement.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    // CRUD
    public List<Review> getAllReviews() {
        logger.debug("Fetching all reviews");
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        logger.debug("Fetching review with ID: {}", id);
        return reviewRepository.findById(id);
    }

    public Review saveReview(Review review) {
        logger.debug("Saving review for client ID: {}", review.getClient() != null ? review.getClient().getId() : "null");

        if (review.getClient() == null) {
            throw new RuntimeException("Client is required");
        }
        if (review.getComment() == null || review.getComment().trim().isEmpty()) {
            throw new RuntimeException("Comment cannot be empty");
        }
        if (review.getRating() < 1 || review.getRating() > 10) {
            throw new RuntimeException("Rating must be between 1 and 10");
        }
        if (review.getReviewDate() == null) {
            throw new RuntimeException("Review date is required");
        }

        return reviewRepository.save(review);
    }

    public boolean deleteReview(Long id) {
        logger.debug("Deleting review ID: {}", id);
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteReviewIfOwnedByClient(Long id, String clientEmail) {
        logger.debug("Deleting review ID: {} for client: {}", id, clientEmail);
        Optional<Review> reviewOpt = reviewRepository.findById(id);
        if (reviewOpt.isPresent()) {
            Review review = reviewOpt.get();
            if (review.getClient() != null && review.getClient().getEmail().equals(clientEmail)) {
                reviewRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

}
