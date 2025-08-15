package com.example.application.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.application.service.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
