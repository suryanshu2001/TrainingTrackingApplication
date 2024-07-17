package com.cozentus.training_tracking_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cozentus.training_tracking_application.model.Batch;

public interface BatchRepository extends JpaRepository<Batch,Integer> {

}
