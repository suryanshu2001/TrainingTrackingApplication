package com.cozentus.training_tracking_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.model.DailyTopicCoverage;
import com.cozentus.training_tracking_application.repository.DailyTopicCoverageRepository;

@Service
public class DailyTopicCoverageService {

    @Autowired
    private DailyTopicCoverageRepository dailyTopicCoverageRepository;

    public DailyTopicCoverage saveDailyTopicCoverage(DailyTopicCoverage dailyTopicCoverage) {
        return dailyTopicCoverageRepository.save(dailyTopicCoverage);
    }

    public List<DailyTopicCoverage> getAllDailyTopicCoverages() {
        return dailyTopicCoverageRepository.findAll();
    }

    public Optional<DailyTopicCoverage> getDailyTopicCoverageById(Integer id) {
        return dailyTopicCoverageRepository.findById(id);
    }

    public void deleteDailyTopicCoverage(Integer id) {
        dailyTopicCoverageRepository.deleteById(id);
    }
}
