package com.example.application.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.entity.Product;
import com.example.application.entity.Review;
import com.example.application.entity.User;
import com.example.application.repository.ProductRepository;
import com.example.application.repository.ReviewRepository;
import com.example.application.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public Review createReview(Long userId, Long productId, Review review) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        review.setUser(user);
        review.setProduct(product);
        
        return reviewRepository.save(review);
    }

    // New method to get all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // New method to get reviews for a specific product
    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
}