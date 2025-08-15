package com.example.application.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.application.entity.Order;
import com.example.application.repository.PaymentRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void testProcessPayment_success() {
        // Arrange
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(150.00);
        String paymentMethod = "Credit Card";

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Payment payment = paymentService.processPayment(order, paymentMethod);

        // Assert
        assertNotNull(payment);
        assertEquals(order, payment.getOrder());
        assertEquals(150.00, payment.getAmount());
        assertEquals(paymentMethod, payment.getPaymentMethod());
        assertEquals("Completed", payment.getStatus());

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository, times(1)).save(paymentCaptor.capture());
        
        Payment capturedPayment = paymentCaptor.getValue();
        assertEquals(order.getId(), capturedPayment.getOrder().getId());
        assertEquals(paymentMethod, capturedPayment.getPaymentMethod());
    }
}
