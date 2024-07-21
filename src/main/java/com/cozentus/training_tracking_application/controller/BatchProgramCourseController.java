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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cozentus.training_tracking_application.model.BatchProgramCourse;
import com.cozentus.training_tracking_application.model.Program;
import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.model.Teacher;
import com.cozentus.training_tracking_application.service.BatchProgramCourseService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/batchProgramCourse")
@CrossOrigin
@Slf4j
public class BatchProgramCourseController {
    
    @Autowired
    private BatchProgramCourseService batchProgramCourseService;

    @GetMapping
    public List<BatchProgramCourse> getAllBatchProgramCourses() {
        return batchProgramCourseService.getAllBatchProgramCourses();
    }

    @GetMapping("/{id}")
    public BatchProgramCourse getBatchProgramCourseById(@PathVariable Integer id) {
        return batchProgramCourseService.getBatchProgramCourseById(id);
    }
    
    @GetMapping("/programs")
    public List<Program> getProgramsByBatch(@RequestParam Integer batchId) {
        return batchProgramCourseService.getProgramsByBatch(batchId);
    }

    @GetMapping("/students")
    public List<Student> getStudentsByBatchAndProgram(@RequestParam Integer batchId, @RequestParam Integer programId) {
        return batchProgramCourseService.getStudentsByBatchAndProgram(batchId, programId);
    }

    @GetMapping("/teacher")
    public Teacher getTeacherByBatchProgramAndCourse(@RequestParam Integer batchId, @RequestParam Integer programId, @RequestParam Integer courseId) {
        return batchProgramCourseService.getTeacherByBatchProgramAndCourse(batchId, programId, courseId);
    }

    @PostMapping
    public BatchProgramCourse createBatchProgramCourse(@RequestBody BatchProgramCourse batchProgramCourse) {
        return batchProgramCourseService.saveBatchProgramCourse(batchProgramCourse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BatchProgramCourse> updateBatchProgramCourse(@PathVariable Integer id, @RequestBody BatchProgramCourse batchProgramCourse) {
        BatchProgramCourse existingBatchProgramCourse = batchProgramCourseService.getBatchProgramCourseById(id);
        if (existingBatchProgramCourse == null) {
            return ResponseEntity.notFound().build();
        }
        batchProgramCourse.setBatchProgramCourseId(id);
        batchProgramCourse.setBatch(existingBatchProgramCourse.getBatch());
        batchProgramCourse.setProgram(existingBatchProgramCourse.getProgram());
        if (batchProgramCourse.getStudents().isEmpty()) {
            batchProgramCourse.setStudents(existingBatchProgramCourse.getStudents());
        }
        BatchProgramCourse updatedBatchProgramCourse = batchProgramCourseService.saveBatchProgramCourse(batchProgramCourse);
        return ResponseEntity.ok(updatedBatchProgramCourse);
    }

    @PutMapping("/teacher/{id}")
    public ResponseEntity<BatchProgramCourse> updateBatchProgramCourseTeacher(@PathVariable Integer id, @RequestBody BatchProgramCourse batchProgramCourse) {
        BatchProgramCourse existingBatchProgramCourse = batchProgramCourseService.getBatchProgramCourseById(id);
        if (existingBatchProgramCourse == null) {
            return ResponseEntity.notFound().build();
        }
        batchProgramCourse.setBatchProgramCourseId(id);
        batchProgramCourse.setBatch(existingBatchProgramCourse.getBatch());
        batchProgramCourse.setProgram(existingBatchProgramCourse.getProgram());
        if (batchProgramCourse.getStudents().isEmpty()) {
            batchProgramCourse.setStudents(existingBatchProgramCourse.getStudents());
        }
        batchProgramCourse.setCourse(existingBatchProgramCourse.getCourse());
        BatchProgramCourse updatedBatchProgramCourse = batchProgramCourseService.saveBatchProgramCourse(batchProgramCourse);
        return ResponseEntity.ok(updatedBatchProgramCourse);
    }

    @DeleteMapping("/{id}")
    public void deleteBatchProgramCourse(@PathVariable Integer id) {
        batchProgramCourseService.deleteBatchProgramCourse(id);
    }
    
    @DeleteMapping("/program/{id}")
    public void deleteBatchProgramCourseProgram(@PathVariable Integer id) {
        batchProgramCourseService.deleteBatchProgramCourseProgram(id);
    }
    
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<BatchProgramCourse>> getBatchProgramCoursesByTeacherId(@PathVariable Integer teacherId) {
        List<BatchProgramCourse> batchProgramCourses = batchProgramCourseService.getBatchProgramCoursesByTeacherId(teacherId);
        return ResponseEntity.ok(batchProgramCourses);
    }
}
