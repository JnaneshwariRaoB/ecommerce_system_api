package com.example.application.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.application.entity.Review;
import com.example.application.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/products/{productId}/users/{userId}")
    public ResponseEntity<Review> createReview(
            @PathVariable Long productId,
            @PathVariable Long userId,
            @RequestBody Review review) {
        try {
            Review createdReview = reviewService.createReview(userId, productId, review);
            return ResponseEntity.ok(createdReview);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint to get all reviews
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // Endpoint to get reviews for a specific product
    @GetMapping("/products/{productId}")
    public List<Review> getReviewsByProductId(@PathVariable Long productId) {
        return reviewService.getReviewsByProductId(productId);
    }
}