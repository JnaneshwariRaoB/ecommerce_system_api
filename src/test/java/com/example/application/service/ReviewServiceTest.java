package com.example.application.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.application.entity.Product;
import com.example.application.entity.Review;
import com.example.application.entity.User;
import com.example.application.repository.ProductRepository;
import com.example.application.repository.ReviewRepository;
import com.example.application.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void testCreateReview_success() {
        // Arrange
        Long userId = 1L;
        Long productId = 1L;
        User user = new User();
        user.setId(userId);
        Product product = new Product();
        product.setId(productId);
        Review review = new Review();
        review.setRating(5);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Review createdReview = reviewService.createReview(userId, productId, review);

        // Assert
        assertNotNull(createdReview);
        assertEquals(user, createdReview.getUser());
        assertEquals(product, createdReview.getProduct());
        assertEquals(5, createdReview.getRating());
        
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findById(productId);
        verify(reviewRepository, times(1)).save(review);
    }
    
    @Test
    void testCreateReview_userNotFound() {
        // Arrange
        Long userId = 1L;
        Long productId = 1L;
        Review review = new Review();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> reviewService.createReview(userId, productId, review));
        
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, never()).findById(anyLong());
        verify(reviewRepository, never()).save(any(Review.class));
    }
    
    @Test
    void testCreateReview_productNotFound() {
        // Arrange
        Long userId = 1L;
        Long productId = 1L;
        User user = new User();
        user.setId(userId);
        Review review = new Review();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> reviewService.createReview(userId, productId, review));
        
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findById(productId);
        verify(reviewRepository, never()).save(any(Review.class));
    }
    
    @Test
    void testGetAllReviews() {
        // Arrange
        Review review1 = new Review();
        review1.setId(1L);
        Review review2 = new Review();
        review2.setId(2L);
        List<Review> reviews = Arrays.asList(review1, review2);

        when(reviewRepository.findAll()).thenReturn(reviews);

        // Act
        List<Review> result = reviewService.getAllReviews();

        // Assert
        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testGetReviewsByProductId() {
        // Arrange
        Long productId = 1L;
        Review review1 = new Review();
        review1.setId(1L);
        Review review2 = new Review();
        review2.setId(2L);
        List<Review> reviews = Arrays.asList(review1, review2);
        
        when(reviewRepository.findByProductId(productId)).thenReturn(reviews);

        // Act
        List<Review> result = reviewService.getReviewsByProductId(productId);

        // Assert
        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findByProductId(productId);
    }
}
