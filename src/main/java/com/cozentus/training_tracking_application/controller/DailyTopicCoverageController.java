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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cozentus.training_tracking_application.model.DailyTopicCoverage;
import com.cozentus.training_tracking_application.service.DailyTopicCoverageService;

@RestController
@RequestMapping("/daily-topic-coverages")
@CrossOrigin
public class DailyTopicCoverageController {

    @Autowired
    private DailyTopicCoverageService dailyTopicCoverageService;

    @PostMapping
    public ResponseEntity<DailyTopicCoverage> createDailyTopicCoverage(@RequestBody DailyTopicCoverage dailyTopicCoverage) {
        DailyTopicCoverage createdDailyTopicCoverage = dailyTopicCoverageService.saveDailyTopicCoverage(dailyTopicCoverage);
        return ResponseEntity.ok(createdDailyTopicCoverage);
    }

    @GetMapping
    public ResponseEntity<List<DailyTopicCoverage>> getAllDailyTopicCoverages() {
        List<DailyTopicCoverage> dailyTopicCoverages = dailyTopicCoverageService.getAllDailyTopicCoverages();
        return ResponseEntity.ok(dailyTopicCoverages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DailyTopicCoverage> getDailyTopicCoverageById(@PathVariable Integer id) {
        Optional<DailyTopicCoverage> dailyTopicCoverage = dailyTopicCoverageService.getDailyTopicCoverageById(id);
        return dailyTopicCoverage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDailyTopicCoverage(@PathVariable Integer id) {
        dailyTopicCoverageService.deleteDailyTopicCoverage(id);
        return ResponseEntity.noContent().build();
    }
}
