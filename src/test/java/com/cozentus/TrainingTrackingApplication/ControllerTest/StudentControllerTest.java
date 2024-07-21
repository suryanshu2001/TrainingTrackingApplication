package com.cozentus.TrainingTrackingApplication.ControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
 
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import com.cozentus.training_tracking_application.exceptions.EmailAlreadyExistsException;
import com.cozentus.training_tracking_application.exceptions.InvalidEmailFormatException;
import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.service.StudentService;
import com.cozentus.training_tracking_application.controller.StudentController;
 
class StudentControllerTest {
 
    @Mock
    private StudentService studentService;
 
    @InjectMocks
    private StudentController studentController;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testGetAllStudents() {
        Student student1 = new Student();
        Student student2 = new Student();
        List<Student> students = Arrays.asList(student1, student2);
        when(studentService.getAllStudents()).thenReturn(students);
 
        ResponseEntity<List<Student>> response = studentController.getAllStudents();
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(students, response.getBody());
    }
 
    @Test
    void testGetStudentById_Found() {
        Student student = new Student();
        when(studentService.getStudentById(1)).thenReturn(Optional.of(student));
 
        ResponseEntity<Student> response = studentController.getStudentById(1);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
    }
 
    @Test
    void testGetStudentById_NotFound() {
        when(studentService.getStudentById(1)).thenReturn(Optional.empty());
 
        ResponseEntity<Student> response = studentController.getStudentById(1);
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    void testCreateStudent() throws EmailAlreadyExistsException, InvalidEmailFormatException {
        Student student = new Student();
        when(studentService.saveStudent(any(Student.class))).thenReturn(student);
 
        ResponseEntity<Student> response = studentController.createStudent(student);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
    }
 
 
 
    @Test
    void testUpdateStudent_Success() throws EmailAlreadyExistsException, InvalidEmailFormatException {
        Student existingStudent = new Student();
        Student updatedStudent = new Student();
        when(studentService.getStudentById(1)).thenReturn(Optional.of(existingStudent));
        when(studentService.editStudent(any(Student.class))).thenReturn(updatedStudent);
 
        ResponseEntity<Student> response = studentController.updateStudent(1, updatedStudent);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedStudent, response.getBody());
    }
 
    @Test
    void testUpdateStudent_NotFound() throws EmailAlreadyExistsException, InvalidEmailFormatException {
        Student updatedStudent = new Student();
        when(studentService.getStudentById(1)).thenReturn(Optional.empty());
 
        ResponseEntity<Student> response = studentController.updateStudent(1, updatedStudent);
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    void testDeleteStudent_Success() {
        when(studentService.getStudentById(1)).thenReturn(Optional.of(new Student()));
 
        ResponseEntity<Void> response = studentController.deleteStudent(1);
 
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
 
    @Test
    void testDeleteStudent_NotFound() {
        when(studentService.getStudentById(1)).thenReturn(Optional.empty());
 
        ResponseEntity<Void> response = studentController.deleteStudent(1);
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    void testGetStudentsByBatchAndProgram() {
        Set<Student> students = Set.of(new Student(), new Student());
        when(studentService.findStudentsByBatchAndProgram(1, 1)).thenReturn(students);
 
        Set<Student> result = studentController.getStudentsByBatchAndProgram(1, 1);
 
        assertEquals(students, result);
    }
}