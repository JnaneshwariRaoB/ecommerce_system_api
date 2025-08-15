package com.example.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.application.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	
	// Finds all reviews for a specific product
    List<Review> findByProductId(Long productId);
}
