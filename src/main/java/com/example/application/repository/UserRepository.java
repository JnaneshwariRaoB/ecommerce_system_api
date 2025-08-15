package com.example.application.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.application.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
