package com.cozentus.training_tracking_application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cozentus.training_tracking_application.model.Batch;
import com.cozentus.training_tracking_application.service.BatchService;

@RestController
@RequestMapping("/batches")
@CrossOrigin
public class BatchController {

    @Autowired
    private BatchService batchService;

    @GetMapping
    public List<Batch> getAllBatches() {
        return batchService.getAllBatches();
    }
        @GetMapping("/{id}")
        public ResponseEntity<Batch> getBatchById(@PathVariable int id) {
            Optional<Batch> batch = batchService.getBatchById(id);
            return batch.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping
        public Batch createBatch(@RequestBody Batch batch) {
            return batchService.createBatch(batch);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Batch> updateBatch(@PathVariable int id, @RequestBody Batch batchDetails) {
            try {
                Batch updatedBatch = batchService.updateBatch(id, batchDetails);
                return ResponseEntity.ok(updatedBatch);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteBatch(@PathVariable int id) {
            try {
                batchService.deleteBatch(id);
                return ResponseEntity.noContent().build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.notFound().build();
            }
        }
}