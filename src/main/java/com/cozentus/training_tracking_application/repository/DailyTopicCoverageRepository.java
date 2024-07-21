package com.cozentus.training_tracking_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cozentus.training_tracking_application.model.DailyTopicCoverage;

public interface DailyTopicCoverageRepository extends JpaRepository<DailyTopicCoverage, Integer> {
	
}
