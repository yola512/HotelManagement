package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.model.Client;
import com.example.hotelmanagement.model.Review;
import com.example.hotelmanagement.repository.ClientRepository;
import com.example.hotelmanagement.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewService;
    private final ClientRepository clientRepository;

    @Autowired
    public ReviewController(ReviewService reviewService, ClientRepository clientRepository) {
        this.reviewService = reviewService;
        this.clientRepository = clientRepository;
    }

    // ADMIN: get all reviews
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Review> getAllReviews() {
        logger.debug("Fetching all reviews");
        return reviewService.getAllReviews();
    }

    // get review by ID (accessible to all authenticated users)
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        logger.debug("Fetching review ID: {}", id);
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // CLIENT: create a review
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review, Authentication authentication) {
        logger.debug("Creating review for user: {}", authentication.getName());
        try {
            Client client = clientRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            review.setClient(client);
            review.setReviewDate(LocalDate.now());
            Review savedReview = reviewService.saveReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
        } catch (RuntimeException e) {
            logger.error("Error creating review: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // ADMIN: delete any review / CLIENT: delete their own review
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id, Authentication authentication) {
        logger.debug("Deleting review ID: {} by {}", id, authentication.getName());
        try {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                if (reviewService.deleteReview(id)) {
                    return ResponseEntity.ok("Review deleted successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
                }
            } else {
                boolean deleted = reviewService.deleteReviewIfOwnedByClient(id, authentication.getName());
                if (deleted) {
                    return ResponseEntity.ok("Review deleted successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("You can only delete your own reviews or review not found");
                }
            }
        } catch (RuntimeException e) {
            logger.error("Error deleting review: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}