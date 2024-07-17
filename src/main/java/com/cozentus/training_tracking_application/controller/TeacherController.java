package com.cozentus.training_tracking_application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.model.Teacher;
import com.cozentus.training_tracking_application.service.TeacherService;

@RestController
@RequestMapping("/teachers")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable int id) {
        Optional<Teacher> teacher = teacherService.getTeacherById(id);
        return teacher.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        try {
            Teacher savedTeacher = teacherService.saveTeacher(teacher);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTeacher);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Integer id, @RequestBody Teacher teacher) {
    	Optional<Teacher> existingTeacherOptional = teacherService.getTeacherById(id);

        if (existingTeacherOptional.isPresent()) {
            Teacher existingStudent = existingTeacherOptional.get();

            // Check each field and assign existing data if null
            if (teacher.getName() == null) {
            	teacher.setName(existingStudent.getName());
            }
            if (teacher.getEmail() == null) {
            	teacher.setEmail(existingStudent.getEmail());
            }
            if (teacher.getCreatedDate() == null) {
            	teacher.setCreatedDate(existingStudent.getCreatedDate());
            }
            if (teacher.getUpdatedDate() == null) {
            	teacher.setUpdatedDate(existingStudent.getUpdatedDate());
            }
            if (teacher.getCreatedBy() == null) {
            	teacher.setCreatedBy(existingStudent.getCreatedBy());
            }
            if (teacher.getUpdatedBy() == null) {
            	teacher.setUpdatedBy(existingStudent.getUpdatedBy());
            }
            
            teacher.setTeacherId(id);
            Teacher updatedTeacher = teacherService.updateTeacher(id,teacher);

            return ResponseEntity.ok(updatedTeacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable int id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
