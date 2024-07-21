package com.cozentus.training_tracking_application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cozentus.training_tracking_application.model.BatchProgramCourseTopic;
import com.cozentus.training_tracking_application.service.BatchProgramCourseTopicService;

@RestController
@RequestMapping("/batchProgramCourseTopics")
@CrossOrigin
public class BatchProgramCourseTopicController {

    @Autowired
    private BatchProgramCourseTopicService batchProgramCourseTopicService;

    @GetMapping
    public List<BatchProgramCourseTopic> getAllBatchProgramCourseTopics() {
        return batchProgramCourseTopicService.getAllBatchProgramCourseTopics();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchProgramCourseTopic> getBatchProgramCourseTopicById(@PathVariable Integer id) {
        BatchProgramCourseTopic batchProgramCourseTopic = batchProgramCourseTopicService.getBatchProgramCourseTopicById(id);
        if (batchProgramCourseTopic == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(batchProgramCourseTopic);
    }
    
    @GetMapping("/batchProgramCourse/{batchProgramCourseId}")
    public ResponseEntity<List<BatchProgramCourseTopic>> getBatchProgramCourseTopicByBatchProgramCourseId(@PathVariable Integer batchProgramCourseId) {
        List<BatchProgramCourseTopic> batchProgramCourseTopics = batchProgramCourseTopicService.getBatchProgramCourseTopicsByBatchProgramCourse(batchProgramCourseId);
        if (batchProgramCourseTopics == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(batchProgramCourseTopics);
    }

    @PostMapping
    public BatchProgramCourseTopic createBatchProgramCourseTopic(@RequestBody BatchProgramCourseTopic batchProgramCourseTopic) {
        return batchProgramCourseTopicService.saveBatchProgramCourseTopic(batchProgramCourseTopic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BatchProgramCourseTopic> updateBatchProgramCourseTopic(@PathVariable Integer id, @RequestBody BatchProgramCourseTopic batchProgramCourseTopicDetails) {
        BatchProgramCourseTopic batchProgramCourseTopic = batchProgramCourseTopicService.getBatchProgramCourseTopicById(id);
        if (batchProgramCourseTopic == null) {
            return ResponseEntity.notFound().build();
        }

        batchProgramCourseTopic.setBatchProgramCourse(batchProgramCourseTopicDetails.getBatchProgramCourse());
        batchProgramCourseTopic.setTopic(batchProgramCourseTopicDetails.getTopic());
        batchProgramCourseTopic.setPercentageCompleted(batchProgramCourseTopicDetails.getPercentageCompleted());

        final BatchProgramCourseTopic updatedBatchProgramCourseTopic = batchProgramCourseTopicService.saveBatchProgramCourseTopic(batchProgramCourseTopic);
        return ResponseEntity.ok(updatedBatchProgramCourseTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatchProgramCourseTopic(@PathVariable Integer id) {
        BatchProgramCourseTopic batchProgramCourseTopic = batchProgramCourseTopicService.getBatchProgramCourseTopicById(id);
        if (batchProgramCourseTopic == null) {
            return ResponseEntity.notFound().build();
        }

        batchProgramCourseTopicService.deleteBatchProgramCourseTopic(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping
    public ResponseEntity<BatchProgramCourseTopic> updateBatchProgramCourseTopic(@RequestBody BatchProgramCourseTopic batchProgramCourseTopic){
    	BatchProgramCourseTopic updatedBatchProgramCourseTopic = batchProgramCourseTopicService.updateBatchProgramCourseTopic(batchProgramCourseTopic);
    	return ResponseEntity.ok(updatedBatchProgramCourseTopic);
    }
}
