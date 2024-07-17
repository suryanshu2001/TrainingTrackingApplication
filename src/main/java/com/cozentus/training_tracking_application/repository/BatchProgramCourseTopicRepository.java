package com.cozentus.training_tracking_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cozentus.training_tracking_application.model.BatchProgramCourseTopic;

public interface BatchProgramCourseTopicRepository extends JpaRepository<BatchProgramCourseTopic,Integer> {
	
}
