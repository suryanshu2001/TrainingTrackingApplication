package com.cozentus.TrainingTrackingApplication.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
 
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
 
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import com.cozentus.training_tracking_application.model.Course;
import com.cozentus.training_tracking_application.model.Program;
import com.cozentus.training_tracking_application.repository.CourseRepository;
import com.cozentus.training_tracking_application.service.CourseService;
import com.cozentus.training_tracking_application.service.ProgramService;
 
public class CourseServiceTest {
 
    @Mock
    private CourseRepository courseRepository;
 
    @Mock
    private ProgramService programService;
 
    @InjectMocks
    private CourseService courseService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testGetAllCourses() {
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> courses = Arrays.asList(course1, course2);
 
        when(courseRepository.findAll()).thenReturn(courses);
 
        List<Course> result = courseService.getAllCourses();
 
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findAll();
    }
 
    @Test
    void testGetAllCoursesEmpty() {
        when(courseRepository.findAll()).thenReturn(Collections.emptyList());
 
        List<Course> result = courseService.getAllCourses();
 
        assertTrue(result.isEmpty());
        verify(courseRepository, times(1)).findAll();
    }
 
    @Test
    void testGetCourseById() {
        Course course = new Course();
        course.setCourseId(1);
 
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
 
        Course result = courseService.getCourseById(1);
 
        assertNotNull(result);
        assertEquals(1, result.getCourseId());
        verify(courseRepository, times(1)).findById(1);
    }
 
    @Test
    void testGetCourseByIdNotFound() {
        when(courseRepository.findById(1)).thenReturn(Optional.empty());
 
        Course result = courseService.getCourseById(1);
 
        assertNull(result);
        verify(courseRepository, times(1)).findById(1);
    }
 
    @Test
    void testSaveCourse() {
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(course);
 
        Course result = courseService.saveCourse(course);
 
        assertNotNull(result);
        verify(courseRepository, times(1)).save(course);
    }
 
    @Test
    void testDeleteCourse() {
        Course course = new Course();
        course.setCourseId(1);
        when(courseRepository.existsById(1)).thenReturn(true);
 
        // Mock the Program object
        Program program = mock(Program.class);
        when(programService.getAllPrograms()).thenReturn(Arrays.asList(program));
        when(program.getCourses()).thenReturn(Collections.emptySet()); // Use emptySet() instead of emptyList()
        doNothing().when(programService).deleteProgram(anyInt());
 
        boolean result = courseService.deleteCourse(1);
 
        assertTrue(result);
        verify(courseRepository, times(1)).deleteById(1);
        verify(programService, times(1)).getAllPrograms();
        verify(programService, times(1)).deleteProgram(program.getProgramId());
    }
 
 
    @Test
    void testDeleteCourseNotExists() {
        when(courseRepository.existsById(1)).thenReturn(false);
 
        boolean result = courseService.deleteCourse(1);
 
        assertFalse(result);
        verify(courseRepository, times(0)).deleteById(1);
        verify(programService, times(0)).getAllPrograms();
        verify(programService, times(0)).deleteProgram(anyInt());
    }
 
    @Test
    void testDeleteCourseWithPrograms() {
        Course course = new Course();
        course.setCourseId(1);
        when(courseRepository.existsById(1)).thenReturn(true);
 
        // Mock the Program object
        Program program = mock(Program.class);
        when(programService.getAllPrograms()).thenReturn(Arrays.asList(program));
        when(program.getCourses()).thenReturn(Collections.singleton(course)); // Use singleton() instead of singletonList()
        doNothing().when(programService).deleteProgram(anyInt());
 
        boolean result = courseService.deleteCourse(1);
 
        assertTrue(result);
        verify(courseRepository, times(1)).deleteById(1);
        verify(programService, times(1)).getAllPrograms();
        verify(programService, times(0)).deleteProgram(anyInt());
    }
 
}