package com.cozentus.training_tracking_application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.model.BatchProgramCourse;
import com.cozentus.training_tracking_application.model.BatchProgramCourseTopic;
import com.cozentus.training_tracking_application.model.Course;
import com.cozentus.training_tracking_application.model.Program;
import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.model.Teacher;
import com.cozentus.training_tracking_application.model.Topic;
import com.cozentus.training_tracking_application.repository.BatchProgramCourseRepository;
import com.cozentus.training_tracking_application.repository.BatchProgramCourseTopicRepository;
import com.cozentus.training_tracking_application.repository.TeacherRepository;
import com.cozentus.training_tracking_application.repository.TopicRepository;

@Service
public class BatchProgramCourseService {
    
    @Autowired
    private BatchProgramCourseRepository batchProgramCourseRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private TopicRepository topicRepository;
    
    @Autowired
    private BatchProgramCourseTopicRepository batchProgramCourseTopicRepository;
    
    @Autowired
    private StudentService studentService;

    public List<BatchProgramCourse> getAllBatchProgramCourses() {
        return batchProgramCourseRepository.findAll();
    }

    public BatchProgramCourse getBatchProgramCourseById(Integer id) {
        return batchProgramCourseRepository.findById(id).orElse(null);
    }
    
    public List<Program> getProgramsByBatch(Integer batchId) {
        return batchProgramCourseRepository.findProgramsByBatch(batchId);
    }

    public List<Student> getStudentsByBatchAndProgram(Integer batchId, Integer programId) {
        return batchProgramCourseRepository.findStudentsByBatchAndProgram(batchId, programId);
    }

    public Teacher getTeacherByBatchProgramAndCourse(Integer batchId, Integer programId, Integer courseId) {
        return batchProgramCourseRepository.findTeacherByBatchProgramAndCourse(batchId, programId, courseId);
    }
    
    public BatchProgramCourse saveBatchProgramCourse(BatchProgramCourse batchProgramCourse) {
    	for(Student student: batchProgramCourse.getStudents()) {
    		Optional<Student> currentStudent = studentService.getStudentById(student.getStudentId());
    		if(currentStudent.isPresent()) {
    			currentStudent.get().setBatch(batchProgramCourse.getBatch());
        		studentService.editStudent(currentStudent.get());
    		}
    		
    	}
    	Teacher teacher = batchProgramCourse.getTeacher();
        if (teacher != null && teacher.getTeacherId()== null) {
            teacherRepository.save(teacher);
        }

        // Ensure all students are persisted
        for (Student student : batchProgramCourse.getStudents()) {
            if (student.getStudentId() == null) {
                studentService.saveStudent(student);
            }
        }
        BatchProgramCourse savedBatchProgramCourse=batchProgramCourseRepository.save(batchProgramCourse);
        if(batchProgramCourse.getCourse()!=null) {
        	Course course = savedBatchProgramCourse.getCourse();
            List<Topic> topics = topicRepository.findByCourseCourseId(course.getCourseId());

            for (Topic topic : topics) {
                BatchProgramCourseTopic batchProgramCourseTopic = new BatchProgramCourseTopic();
                batchProgramCourseTopic.setBatchProgramCourse(savedBatchProgramCourse);
                batchProgramCourseTopic.setTopic(topic);
                batchProgramCourseTopic.setPercentageCompleted(0);
                batchProgramCourseTopicRepository.save(batchProgramCourseTopic);
            }
        }
        return savedBatchProgramCourse;
    }

    public void deleteBatchProgramCourse(Integer id) {
    	BatchProgramCourse currentBatchProgramCourse=getBatchProgramCourseById(id);
    	List<BatchProgramCourse> allBatchProgramCourses=getAllBatchProgramCourses();
    	
    	List<BatchProgramCourse> filteredBatchProgramCourses = allBatchProgramCourses.stream()
    	        .filter(bpc -> bpc.getBatch().getBatchId().equals(currentBatchProgramCourse.getBatch().getBatchId())
    	                    && bpc.getProgram().getProgramId().equals(currentBatchProgramCourse.getProgram().getProgramId()))
    	        .collect(Collectors.toList());
    	if(filteredBatchProgramCourses.size()>1) {
    		batchProgramCourseRepository.deleteById(id);
    	}else {
    		currentBatchProgramCourse.setCourse(null);
    		currentBatchProgramCourse.setTeacher(null);
    		batchProgramCourseRepository.save(currentBatchProgramCourse);
    	}
    }
    
    public void deleteBatchProgramCourseProgram(Integer id) {
    	System.out.println("invoked b");
    	BatchProgramCourse currentBatchProgramCourse=getBatchProgramCourseById(id);
    	List<BatchProgramCourse> allBatchProgramCourses=getAllBatchProgramCourses();
    	
    	List<BatchProgramCourse> filteredBatchProgramCourses = allBatchProgramCourses.stream()
    	        .filter(bpc -> bpc.getBatch().getBatchId().equals(currentBatchProgramCourse.getBatch().getBatchId())
    	                    && bpc.getProgram().getProgramId().equals(currentBatchProgramCourse.getProgram().getProgramId()))
    	        .collect(Collectors.toList());
    	for(BatchProgramCourse batchProgramCourse: filteredBatchProgramCourses) {
    		batchProgramCourseRepository.deleteById(batchProgramCourse.getBatchProgramCourseId());
    	}
    }
    
    
}
