package com.cozentus.training_tracking_application.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.cozentus.training_tracking_application.exceptions.EmailAlreadyExistsException;
import com.cozentus.training_tracking_application.exceptions.InvalidEmailFormatException;
import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.service.StudentService;

@RestController
@RequestMapping("/students")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws EmailAlreadyExistsException, InvalidEmailFormatException {
        Student savedStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(savedStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer id, @RequestBody Student student) throws EmailAlreadyExistsException, InvalidEmailFormatException {
        Optional<Student> existingStudentOptional = studentService.getStudentById(id);

        if (existingStudentOptional.isPresent()) {
            Student existingStudent = existingStudentOptional.get();

            // Check each field and assign existing data if null
            if (student.getName() == null) {
                student.setName(existingStudent.getName());
            }
            if (student.getEmail() == null) {
                student.setEmail(existingStudent.getEmail());
            }
            if (student.getStudentCode() == null) {
                student.setStudentCode(existingStudent.getStudentCode());
            }
            if (student.getCreatedDate() == null) {
                student.setCreatedDate(existingStudent.getCreatedDate());
            }
            if (student.getUpdatedDate() == null) {
                student.setUpdatedDate(existingStudent.getUpdatedDate());
            }
            if (student.getCreatedBy() == null) {
                student.setCreatedBy(existingStudent.getCreatedBy());
            }
            if (student.getUpdatedBy() == null) {
                student.setUpdatedBy(existingStudent.getUpdatedBy());
            }

            student.setStudentId(id);
            Student updatedStudent = studentService.editStudent(student);

            return ResponseEntity.ok(updatedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/batch/{batchId}/program/{programId}")
    public Set<Student> getStudentsByBatchAndProgram(@PathVariable int batchId, @PathVariable int programId) {
        return studentService.findStudentsByBatchAndProgram(batchId, programId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        Optional<Student> existingStudent = studentService.getStudentById(id);
        if (existingStudent.isPresent()) {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
