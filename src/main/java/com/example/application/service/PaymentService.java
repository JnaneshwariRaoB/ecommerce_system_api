package com.example.application.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.entity.Order;
import com.example.application.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(Order order, String paymentMethod) {
        // In a real application, you'd call a payment gateway here (e.g., Stripe API)
        // This is a simplified simulation.

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("Completed"); // Simulate a successful payment

        return paymentRepository.save(payment);
    }
}
