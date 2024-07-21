package com.cozentus.TrainingTrackingApplication.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import com.cozentus.training_tracking_application.model.Teacher;
import com.cozentus.training_tracking_application.repository.TeacherRepository;
import com.cozentus.training_tracking_application.service.EmailService;
import com.cozentus.training_tracking_application.service.EmailValidationService;
import com.cozentus.training_tracking_application.service.TeacherService;
 
class TeacherServiceTest {
 
    @Mock
    private TeacherRepository teacherRepository;
 
    @Mock
    private EmailValidationService emailValidationService;
 
    @Mock
    private EmailService emailService;
 
    @InjectMocks
    private TeacherService teacherService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testSaveTeacher_Success() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setEmail("test@example.com");
        teacher.setName("Test Teacher");
        when(teacherRepository.save(teacher)).thenReturn(teacher);
 
        // Act
        Teacher savedTeacher = teacherService.saveTeacher(teacher);
 
        // Assert
        assertNotNull(savedTeacher);
        verify(emailService, times(1)).sendEmail(eq("test@example.com"), anyString(), contains("Welcome to Cozentus Training System"));
        verify(teacherRepository, times(1)).save(teacher);
    }
 
    @Test
    void testUpdateTeacher_Success() {
        // Arrange
        Teacher existingTeacher = new Teacher();
        existingTeacher.setEmail("old@example.com");
        existingTeacher.setName("Existing Teacher");
        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setEmail("new@example.com");
        updatedTeacher.setName("Updated Teacher");
 
        when(teacherRepository.findById(1)).thenReturn(Optional.of(existingTeacher));
        when(teacherRepository.save(updatedTeacher)).thenReturn(updatedTeacher);
 
        // Act
        Teacher result = teacherService.updateTeacher(1, updatedTeacher);
 
        // Assert
        assertNotNull(result);
        verify(emailService, times(1)).sendEmail(eq("new@example.com"), anyString(), contains("Your account information has been updated"));
        verify(teacherRepository, times(1)).save(updatedTeacher);
    }
 
    @Test
    void testUpdateTeacher_EmailSendingSuccess() {
        // Arrange
        Teacher existingTeacher = new Teacher();
        existingTeacher.setEmail("existing@example.com");
        existingTeacher.setName("Existing Teacher");
 
        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setEmail("updated@example.com");
        updatedTeacher.setName("Updated Teacher");
 
        // Mock the teacherRepository to return the existingTeacher when findById is called
        when(teacherRepository.findById(anyInt())).thenReturn(Optional.of(existingTeacher));
 
        // Mock the emailService to simulate successful email sending
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());
 
        // Mock the teacherRepository.save to return the updatedTeacher
        when(teacherRepository.save(any(Teacher.class))).thenReturn(updatedTeacher);
 
        // Act
        Teacher result = teacherService.updateTeacher(1, updatedTeacher);
 
        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(updatedTeacher.getEmail(), result.getEmail(), "The email should be updated");
        assertEquals(updatedTeacher.getName(), result.getName(), "The name should be updated");
 
        // Verify that emailService.sendEmail was called
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
 
        // Verify that teacherRepository.save was called
        verify(teacherRepository, times(1)).save(updatedTeacher);
    }
 
 
 
 
    @Test
    void testUpdateTeacher_NoEmailChange() {
        // Arrange
        Teacher existingTeacher = new Teacher();
        existingTeacher.setEmail("same@example.com");
        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setEmail("same@example.com");
 
        when(teacherRepository.findById(1)).thenReturn(Optional.of(existingTeacher));
        when(teacherRepository.save(updatedTeacher)).thenReturn(updatedTeacher);
 
        // Act
        Teacher result = teacherService.updateTeacher(1, updatedTeacher);
 
        // Assert
        assertNotNull(result);
        verify(emailService, times(0)).sendEmail(anyString(), anyString(), anyString());
        verify(teacherRepository, times(1)).save(updatedTeacher);
    }
 
    @Test
    void testUpdateTeacher_TeacherNotFound() {
        // Arrange
        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setEmail("new@example.com");
 
        when(teacherRepository.findById(1)).thenReturn(Optional.empty());
 
        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            teacherService.updateTeacher(1, updatedTeacher);
        });
        assertEquals("Teacher not found", thrown.getMessage());
        verify(teacherRepository, times(0)).save(updatedTeacher);
    }
 
    @Test
    void testDeleteTeacher_Success() {
        // Arrange
        doNothing().when(teacherRepository).deleteById(1);
 
        // Act
        teacherService.deleteTeacher(1);
 
        // Assert
        verify(teacherRepository, times(1)).deleteById(1);
    }
}