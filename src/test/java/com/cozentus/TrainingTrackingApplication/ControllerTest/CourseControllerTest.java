package com.cozentus.TrainingTrackingApplication.ControllerTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
 
import java.util.Arrays;
import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
 
import com.cozentus.training_tracking_application.controller.CourseController;
import com.cozentus.training_tracking_application.model.Course;
import com.cozentus.training_tracking_application.service.CourseService;
 
public class CourseControllerTest {
 
    @Mock
    private CourseService courseService;
 
    @InjectMocks
    private CourseController courseController;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testGetAllCourses() {
        Course course = new Course();
        List<Course> courses = Arrays.asList(course);
        when(courseService.getAllCourses()).thenReturn(courses);
 
        ResponseEntity<List<Course>> response = courseController.getAllCourses();
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(courseService, times(1)).getAllCourses();
    }
 
    @Test
    void testGetCourseById_Found() {
        Course course = new Course();
        when(courseService.getCourseById(1)).thenReturn(course);
 
        ResponseEntity<Course> response = courseController.getCourseById(1);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(courseService, times(1)).getCourseById(1);
    }
 
    @Test
    void testGetCourseById_NotFound() {
        when(courseService.getCourseById(1)).thenReturn(null);
 
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            courseController.getCourseById(1);
        });
 
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Course not found", exception.getReason());
        verify(courseService, times(1)).getCourseById(1);
    }
 
    @Test
    void testCreateCourse() {
        Course course = new Course();
        when(courseService.saveCourse(course)).thenReturn(course);
 
        ResponseEntity<Course> response = courseController.createCourse(course);
 
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(courseService, times(1)).saveCourse(course);
    }
 
    @Test
    void testUpdateCourse_Found() {
        Course existingCourse = new Course();
        Course updatedCourse = new Course();
        existingCourse.setCourseId(1);
        updatedCourse.setCourseId(1);
 
        when(courseService.getCourseById(1)).thenReturn(existingCourse);
        when(courseService.saveCourse(existingCourse)).thenReturn(updatedCourse);
 
        ResponseEntity<Course> response = courseController.updateCourse(1, updatedCourse);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getCourseId());
        verify(courseService, times(1)).getCourseById(1);
        verify(courseService, times(1)).saveCourse(existingCourse);
    }
 
    @Test
    void testUpdateCourse_NotFound() {
        Course updatedCourse = new Course();
        when(courseService.getCourseById(1)).thenReturn(null);
 
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            courseController.updateCourse(1, updatedCourse);
        });
 
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Course not found", exception.getReason());
        verify(courseService, times(1)).getCourseById(1);
    }
 
    @Test
    void testDeleteCourse_Found() {
        when(courseService.deleteCourse(1)).thenReturn(true);
 
        ResponseEntity<Void> response = courseController.deleteCourse(1);
 
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseService, times(1)).deleteCourse(1);
    }
 
    @Test
    void testDeleteCourse_NotFound() {
        when(courseService.deleteCourse(1)).thenReturn(false);
 
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            courseController.deleteCourse(1);
        });
 
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Course not found", exception.getReason());
        verify(courseService, times(1)).deleteCourse(1);
    }
}