package com.cozentus.training_tracking_application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.model.BatchProgramCourse;
import com.cozentus.training_tracking_application.model.BatchProgramCourseTopic;
import com.cozentus.training_tracking_application.repository.BatchProgramCourseTopicRepository;

import jakarta.transaction.Transactional;

@Service
public class BatchProgramCourseTopicService {

    @Autowired
    private BatchProgramCourseTopicRepository batchProgramCourseTopicRepository;

    public List<BatchProgramCourseTopic> getAllBatchProgramCourseTopics() {
        return batchProgramCourseTopicRepository.findAll();
    }

    public BatchProgramCourseTopic getBatchProgramCourseTopicById(Integer id) {
        return batchProgramCourseTopicRepository.findById(id).orElse(null);
    }

    public BatchProgramCourseTopic saveBatchProgramCourseTopic(BatchProgramCourseTopic batchProgramCourseTopic) {
        return batchProgramCourseTopicRepository.save(batchProgramCourseTopic);
    }

    public void deleteBatchProgramCourseTopic(Integer id) {
        batchProgramCourseTopicRepository.deleteById(id);
    }
    
    public List<BatchProgramCourseTopic> getBatchProgramCourseTopicsByBatchProgramCourse(Integer batchProgramCourseId) {
    	return batchProgramCourseTopicRepository.findByBatchProgramCourseBatchProgramCourseId(batchProgramCourseId);
    }
    
    public BatchProgramCourseTopic updateBatchProgramCourseTopic(BatchProgramCourseTopic batchProgramCourseTopic) {
        return batchProgramCourseTopicRepository.save(batchProgramCourseTopic);
    }
    
    
}
