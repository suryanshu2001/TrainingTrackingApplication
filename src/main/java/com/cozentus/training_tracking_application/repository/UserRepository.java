package com.cozentus.training_tracking_application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cozentus.training_tracking_application.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserEmailAndUserPassword(String userEmail,String userPassword);
}