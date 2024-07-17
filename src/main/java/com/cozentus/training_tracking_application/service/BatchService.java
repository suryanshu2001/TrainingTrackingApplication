package com.cozentus.training_tracking_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.cozentus.training_tracking_application.model.Batch;
import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.repository.BatchRepository;
import com.cozentus.training_tracking_application.repository.StudentRepository;

@Service
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    public Optional<Batch> getBatchById(int batchId) {
        return batchRepository.findById(batchId);
    }

    public Batch createBatch(Batch batch) {
        return batchRepository.save(batch);
    }

    public Batch updateBatch(int batchId, Batch batchDetails) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found with id: " + batchId));

        if (batchDetails.getBatchName() != null && !batchDetails.getBatchName().isEmpty()) {
            batch.setBatchName(batchDetails.getBatchName());
        }
        if (batchDetails.getBatchCode() != null && !batchDetails.getBatchCode().isEmpty()) {
            batch.setBatchCode(batchDetails.getBatchCode());
        }
        if (batchDetails.getStartDate() != null) {
            batch.setStartDate(batchDetails.getStartDate());
        }
        if (batchDetails.getCreatedDate() != null) {
            batch.setCreatedDate(batchDetails.getCreatedDate());
        }
        if (batchDetails.getUpdatedDate() != null) {
            batch.setUpdatedDate(batchDetails.getUpdatedDate());
        }
        if (batchDetails.getCreatedBy() != null && !batchDetails.getCreatedBy().isEmpty()) {
            batch.setCreatedBy(batchDetails.getCreatedBy());
        }
        if (batchDetails.getUpdatedBy() != null && !batchDetails.getUpdatedBy().isEmpty()) {
            batch.setUpdatedBy(batchDetails.getUpdatedBy());
        }


        return batchRepository.save(batch);
    }
    
    public void deleteBatch(Integer batchId) {
        // Find the batch by its ID
        Batch batch = batchRepository.findById(batchId).orElseThrow(() -> new ResourceAccessException("Batch not found"));

        // Set the batch reference to null for all students in this batch
        for (Student student : batch.getStudents()) {
            student.setBatch(null);
            studentRepository.save(student);
        }

        // Now delete the batch
        batchRepository.delete(batch);
    }
    
//    @Transactional
//    public void deleteBatch(int batchId) {
//        Batch batch = batchRepository.findById(batchId)
//                .orElseThrow(() -> new IllegalArgumentException("Batch not found with id: " + batchId));
//
//        batchRepository.delete(batch);
//    }
}
