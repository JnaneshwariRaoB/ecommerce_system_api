package com.example.application.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.application.entity.Order;
import com.example.application.entity.User;
import com.example.application.repository.OrderRepository;
import com.example.application.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentService paymentService; // Mock the new dependency

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrder_success() {
        // Arrange
        Long userId = 1L;
        double totalAmount = 100.0;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Order createdOrder = orderService.createOrder(userId, totalAmount);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(user, createdOrder.getUser());
        assertEquals(totalAmount, createdOrder.getTotalAmount());
        verify(userRepository, times(1)).findById(userId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testPlaceOrder_success() {
        // Arrange
        Long orderId = 1L;
        String paymentMethod = "Credit Card";
        Order order = new Order();
        order.setId(orderId);
        order.setTotalAmount(150.00);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Order paidOrder = orderService.placeOrder(orderId, paymentMethod);

        // Assert
        assertNotNull(paidOrder);
        // Verify that the payment service was called correctly
        verify(paymentService, times(1)).processPayment(order, paymentMethod);
        // Verify that the order was saved after the payment
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testPlaceOrder_orderNotFound() {
        // Arrange
        Long orderId = 1L;
        String paymentMethod = "Credit Card";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.placeOrder(orderId, paymentMethod));

        // Verify that no further methods were called
        verify(orderRepository, times(1)).findById(orderId);
        verify(paymentService, never()).processPayment(any(Order.class), anyString());
        verify(orderRepository, never()).save(any(Order.class));
    }
}
