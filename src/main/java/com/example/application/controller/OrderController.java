package com.example.application.controller;




//import com.example.application.entity.Order;
//import com.example.application.service.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/orders")
//public class OrderController {
//    @Autowired
//    private OrderService orderService;
//
//    @PostMapping
//    public ResponseEntity<Order> createOrder(@RequestParam Long userId, @RequestParam double totalAmount) {
//        try {
//            Order createdOrder = orderService.createOrder(userId, totalAmount);
//            return ResponseEntity.ok(createdOrder);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @GetMapping
//    public List<Order> getAllOrders() {
//        return orderService.getAllOrders();
//    }
//}





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.application.entity.Order;
import com.example.application.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestParam Long userId, @RequestParam double totalAmount) {
        return orderService.createOrder(userId, totalAmount);
    }

    @PostMapping("/{orderId}/payment")
    public ResponseEntity<Order> processPayment(@PathVariable Long orderId, @RequestParam String paymentMethod) {
        try {
            Order paidOrder = orderService.placeOrder(orderId, paymentMethod);
            return ResponseEntity.ok(paidOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}