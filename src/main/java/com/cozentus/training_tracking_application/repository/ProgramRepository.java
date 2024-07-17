package com.cozentus.training_tracking_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cozentus.training_tracking_application.model.Program;

public interface ProgramRepository extends JpaRepository<Program, Integer> {
	 @Query("SELECT DISTINCT p FROM Program p JOIN p.batchProgramCourses bpc WHERE bpc.batch.batchId = :batchId")
	    List<Program> findProgramsByBatchId(@Param("batchId") Integer batchId);
}
