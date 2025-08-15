package com.example.application.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.application.entity.Order;
//import com.example.application.entity.User;
//import com.example.application.repository.OrderRepository;
//import com.example.application.repository.UserRepository;
//
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class OrderService {
//	@Autowired
//    private OrderRepository orderRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    public Order createOrder(Long userId, double totalAmount) {
//        // Find the user by ID; if not found, a RuntimeException is thrown
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        Order order = new Order();
//        order.setUser(user);
//        order.setOrderDate(new Date()); // Sets the order date to the current date and time
//        order.setTotalAmount(totalAmount);
//        return orderRepository.save(order);
//    }
//
//    public List<Order> getAllOrders() {
//        return orderRepository.findAll();
//    }
//}





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.entity.Order;
import com.example.application.entity.User;
import com.example.application.repository.OrderRepository;
import com.example.application.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
	private static final  Logger auditLogger = LoggerFactory.getLogger("auditLogger");
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentService paymentService;

    public Order createOrder(Long userId, double totalAmount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        
		auditLogger.info("User transaction: Order created with ID {} for user with ID {}", savedOrder.getId(), userId);
        return savedOrder;
    }

    public Order placeOrder(Long orderId, String paymentMethod) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Use the PaymentService to process the payment
        paymentService.processPayment(order, paymentMethod);

        // Update order status after successful payment (in a real app, this would be more detailed)
        // For simplicity, we assume the payment was successful if the method is called.
        // The order status would change from 'Pending' to 'Processing' or 'Paid'
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}