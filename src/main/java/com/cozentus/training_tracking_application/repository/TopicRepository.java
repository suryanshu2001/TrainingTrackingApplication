package com.cozentus.training_tracking_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cozentus.training_tracking_application.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
	List<Topic> findByCourseCourseId(Integer courseId);
}
