package com.cozentus.training_tracking_application.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cozentus.training_tracking_application.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    Optional<Attendance> findByBatchProgramCourseBatchProgramCourseIdAndDate(int batchProgramCourseId, Date date);
}
