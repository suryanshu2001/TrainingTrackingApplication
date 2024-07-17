package com.cozentus.training_tracking_application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.model.Course;
import com.cozentus.training_tracking_application.model.Program;
import com.cozentus.training_tracking_application.repository.CourseRepository;


@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private ProgramService programService;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public boolean deleteCourse(int id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            List<Program> programs=programService.getAllPrograms();
            programs.forEach(program -> {
                if (program.getCourses().isEmpty()) {
                	programService.deleteProgram(program.getProgramId());
                }
            });
            return true;
        }
        return false;
    }
}


