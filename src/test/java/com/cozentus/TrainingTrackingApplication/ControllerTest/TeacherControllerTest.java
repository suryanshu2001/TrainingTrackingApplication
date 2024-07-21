package com.cozentus.TrainingTrackingApplication.ControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
 
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import com.cozentus.training_tracking_application.controller.TeacherController;
import com.cozentus.training_tracking_application.model.Teacher;
import com.cozentus.training_tracking_application.service.TeacherService;
 
public class TeacherControllerTest {
 
    @InjectMocks
    private TeacherController teacherController;
 
    @Mock
    private TeacherService teacherService;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    public void testGetAllTeachers_Success() {
        // Arrange
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        when(teacherService.getAllTeachers()).thenReturn(Arrays.asList(teacher1, teacher2));
 
        // Act
        List<Teacher> response = teacherController.getAllTeachers();
 
        // Assert
        assertEquals(2, response.size());
    }
 
    @Test
    public void testGetTeacherById_Success() {
        // Arrange
        Teacher teacher = new Teacher();
        when(teacherService.getTeacherById(1)).thenReturn(Optional.of(teacher));
 
        // Act
        ResponseEntity<Teacher> response = teacherController.getTeacherById(1);
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teacher, response.getBody());
    }
 
    @Test
    public void testGetTeacherById_NotFound() {
        // Arrange
        when(teacherService.getTeacherById(1)).thenReturn(Optional.empty());
 
        // Act
        ResponseEntity<Teacher> response = teacherController.getTeacherById(1);
 
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    public void testCreateTeacher_Success() {
        // Arrange
        Teacher teacher = new Teacher();
        when(teacherService.saveTeacher(teacher)).thenReturn(teacher);
 
        // Act
        ResponseEntity<Teacher> response = teacherController.createTeacher(teacher);
 
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(teacher, response.getBody());
    }
 
    @Test
    public void testCreateTeacher_Failure() {
        // Arrange
        Teacher teacher = new Teacher();
        when(teacherService.saveTeacher(teacher)).thenThrow(new RuntimeException("Creation error"));
 
        // Act
        ResponseEntity<Teacher> response = teacherController.createTeacher(teacher);
 
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
 
    @Test
    public void testUpdateTeacher_Success() {
        // Arrange
        Teacher existingTeacher = new Teacher();
        existingTeacher.setTeacherId(1);
        existingTeacher.setName("Existing Teacher");
       
        Teacher teacherToUpdate = new Teacher();
        teacherToUpdate.setTeacherId(1);
        teacherToUpdate.setName(null); // This should use existingTeacher's name
 
        when(teacherService.getTeacherById(1)).thenReturn(Optional.of(existingTeacher));
        when(teacherService.updateTeacher(1, teacherToUpdate)).thenReturn(teacherToUpdate);
 
        // Act
        ResponseEntity<Teacher> response = teacherController.updateTeacher(1, teacherToUpdate);
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingTeacher.getName(), response.getBody().getName());
    }
 
    @Test
    public void testUpdateTeacher_NotFound() {
        // Arrange
        Teacher teacherToUpdate = new Teacher();
        when(teacherService.getTeacherById(1)).thenReturn(Optional.empty());
 
        // Act
        ResponseEntity<Teacher> response = teacherController.updateTeacher(1, teacherToUpdate);
 
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    public void testDeleteTeacher_Success() {
        // Arrange
        doNothing().when(teacherService).deleteTeacher(1);
 
        // Act
        ResponseEntity<Void> response = teacherController.deleteTeacher(1);
 
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
 
}