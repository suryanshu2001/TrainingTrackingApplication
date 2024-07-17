package com.cozentus.training_tracking_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.model.Topic;
import com.cozentus.training_tracking_application.repository.TopicRepository;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic getTopicById(int id) {
        return topicRepository.findById(id).orElse(null);
    }

    public Topic saveTopic(Topic topic) {
    	
        return topicRepository.save(topic);
    }

    public boolean deleteTopic(int id) {
        Optional<Topic> topicOptional = topicRepository.findById(id);
        if (topicOptional.isPresent()) {
            topicRepository.deleteById(id);
            return true; // Deletion successful
        }
        return false; // Topic with given id not found
    }
    
    public Optional<List<Topic>> getTopicsByCourseId(Integer courseId){
    	return Optional.of(topicRepository.findByCourseCourseId(courseId));
    }
}

