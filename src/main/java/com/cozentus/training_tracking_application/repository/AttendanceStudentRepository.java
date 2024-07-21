package com.cozentus.training_tracking_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cozentus.training_tracking_application.model.AttendanceStudent;

@Repository
public interface AttendanceStudentRepository extends JpaRepository<AttendanceStudent, Integer> {
    // Custom query methods can be added here if needed
}
