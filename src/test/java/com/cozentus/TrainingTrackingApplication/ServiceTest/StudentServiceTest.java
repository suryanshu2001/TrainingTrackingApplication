package com.cozentus.TrainingTrackingApplication.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
import java.util.*;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
 
import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.repository.StudentRepository;
import com.cozentus.training_tracking_application.service.EmailValidationService;
import com.cozentus.training_tracking_application.service.StudentService;
 
public class StudentServiceTest {
 
    @Mock
    private StudentRepository studentRepository;
 
    @Mock
    private JavaMailSender mailSender;
 
    @Mock
    private EmailValidationService emailValidationService;
 
    @InjectMocks
    private StudentService studentService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testGetAllStudents() {
        // Arrange
        Student student = new Student();
        student.setStudentId(1);
        List<Student> students = Collections.singletonList(student);
        when(studentRepository.findAll()).thenReturn(students);
 
        // Act
        List<Student> result = studentService.getAllStudents();
 
        // Assert
        assertEquals(1, result.size());
        assertEquals(student, result.get(0));
        verify(studentRepository, times(1)).findAll();
    }
 
    @Test
    void testGetStudentById() {
        // Arrange
        Student student = new Student();
        student.setStudentId(1);
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
 
        // Act
        Optional<Student> result = studentService.getStudentById(1);
 
        // Assert
        assertTrue(result.isPresent());
        assertEquals(student, result.get());
        verify(studentRepository, times(1)).findById(1);
    }
 
    @Test
    void testSaveStudent_Success() {
        // Arrange
        Student student = new Student();
        student.setStudentId(1);
        student.setEmail("test@example.com");
        student.setName("Test Student");
 
        when(emailValidationService.validateEmail(student.getEmail())).thenReturn(true);
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        when(studentRepository.save(student)).thenReturn(student);
 
        // Act
        Student result = studentService.saveStudent(student);
 
        // Assert
        assertEquals(student, result);
        verify(studentRepository, times(1)).save(student);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
 
    @Test
    void testSaveStudent_FailToSendEmail() {
        // Arrange
        Student student = new Student();
        student.setStudentId(1);
        student.setEmail("test@example.com");
        student.setName("Test Student");
 
        when(emailValidationService.validateEmail(student.getEmail())).thenReturn(true);
 
        // Simulate MailException during email sending
        doThrow(new MailException("Error sending email") {}).when(mailSender).send(any(SimpleMailMessage.class));
 
        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            studentService.saveStudent(student);
        });
        assertEquals("Failed to send email. Email address may be invalid.", thrown.getMessage());
        verify(studentRepository, times(0)).save(student); // Ensure repository save is not called
    }
 
 
    @Test
    void testEditStudent_Success() {
        // Arrange
        Student student = new Student();
        student.setStudentId(1);
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);
 
        // Act
        Student result = studentService.editStudent(student);
 
        // Assert
        assertEquals(student, result);
        verify(studentRepository, times(1)).findById(1);
        verify(studentRepository, times(1)).save(student);
    }
 
    @Test
    void testEditStudent_NotFound() {
        // Arrange
        Student student = new Student();
        student.setStudentId(1);
        when(studentRepository.findById(1)).thenReturn(Optional.empty());
 
        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            studentService.editStudent(student);
        });
        assertEquals("Student not found", thrown.getMessage());
        verify(studentRepository, times(1)).findById(1);
        verify(studentRepository, times(0)).save(student);
    }
 
    @Test
    void testFindStudentsByBatchAndProgram() {
        // Arrange
        Student student = new Student();
        student.setStudentId(1);
        Set<Student> students = Collections.singleton(student);
        when(studentRepository.findStudentsByBatchAndProgram(1, 1)).thenReturn(students);
 
        // Act
        Set<Student> result = studentService.findStudentsByBatchAndProgram(1, 1);
 
        // Assert
        assertEquals(students, result);
        verify(studentRepository, times(1)).findStudentsByBatchAndProgram(1, 1);
    }
 
    @Test
    void testDeleteStudent() {
        // Arrange
        doNothing().when(studentRepository).deleteById(1);
 
        // Act
        studentService.deleteStudent(1);
 
        // Assert
        verify(studentRepository, times(1)).deleteById(1);
    }
}