package com.cozentus.training_tracking_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cozentus.training_tracking_application.model.File;

public interface FileRepository extends JpaRepository<File, Integer> {
	//List<TopicFile> findByTopicId(Integer topicId);
}
