package com.cozentus.training_tracking_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cozentus.training_tracking_application.model.BatchProgramCourse;
import com.cozentus.training_tracking_application.model.Program;
import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.model.Teacher;

public interface BatchProgramCourseRepository extends JpaRepository<BatchProgramCourse, Integer> {
	@Query("SELECT DISTINCT bpc.program FROM BatchProgramCourse bpc WHERE bpc.batch.batchId = :batchId")
    List<Program> findProgramsByBatch(@Param("batchId") int batchId);

    @Query("SELECT s FROM Student s JOIN s.batchProgramCourses bpc WHERE bpc.batch.batchId = :batchId AND bpc.program.programId = :programId")
    List<Student> findStudentsByBatchAndProgram(@Param("batchId") int batchId, @Param("programId") int programId);

    @Query("SELECT bpc.teacher FROM BatchProgramCourse bpc WHERE bpc.batch.batchId = :batchId AND bpc.program.programId = :programId AND bpc.course.courseId = :courseId")
    Teacher findTeacherByBatchProgramAndCourse(@Param("batchId") int batchId, @Param("programId") int programId, @Param("courseId") int courseId);
}