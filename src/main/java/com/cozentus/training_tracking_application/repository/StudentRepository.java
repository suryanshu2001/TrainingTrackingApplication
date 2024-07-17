package com.cozentus.training_tracking_application.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cozentus.training_tracking_application.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	Optional<Student> findByEmail(String email); 
	
	@Query("SELECT DISTINCT s FROM Student s JOIN s.batchProgramCourses bpc WHERE bpc.batch.batchId = :batchId AND bpc.program.programId = :programId")
    Set<Student> findStudentsByBatchAndProgram(@Param("batchId") int batchId, @Param("programId") int programId);
}
