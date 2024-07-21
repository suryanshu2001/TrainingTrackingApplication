package com.cozentus.training_tracking_application.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.model.Attendance;
import com.cozentus.training_tracking_application.model.AttendanceStudent;
import com.cozentus.training_tracking_application.model.BatchProgramCourse;
import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.repository.AttendanceRepository;
import com.cozentus.training_tracking_application.repository.AttendanceStudentRepository;
import com.cozentus.training_tracking_application.repository.BatchProgramCourseRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private AttendanceStudentRepository attendanceStudentRepository;
    
    @Autowired
    private BatchProgramCourseRepository batchProgramCourseRepository;
    
//    public Attendance saveAttendance(Attendance attendance) {
//        attendance.setCreatedDate(new Date());
//        attendance.setUpdatedDate(new Date());
//        
//        // Save the attendance first to get the generated ID
//        Attendance savedAttendance = attendanceRepository.save(attendance);
//
//        Optional<BatchProgramCourse> batchProgramCourse = batchProgramCourseRepository.findById(attendance.getBatchProgramCourse().getBatchProgramCourseId());
//        
//        if (batchProgramCourse == null) {
//            log.error("Batch Program Course is null.");
//            return savedAttendance;
//        }
//
//        Set<Student> students = batchProgramCourse.get().getStudents();
//        
//        if (students == null || students.isEmpty()) {
//            log.error("No students found in Batch Program Course.");
//            return savedAttendance;
//        }
//        Set<AttendanceStudent> attendanceStudents = new HashSet<>();
//        for (Student student : batchProgramCourse.get().getStudents()) {
//        	//log.info("entered into saving students in attendence:"+savedAttendance);
//            AttendanceStudent attendanceStudent = new AttendanceStudent();
//            attendanceStudent.setAttendance(savedAttendance);
//            attendanceStudent.setStudent(student);
//            attendanceStudent.setIsPresent(false);
//            attendanceStudents.add(attendanceStudent);
//        }
//
//        savedAttendance.setAttendanceStudents(attendanceStudents);
//        attendanceStudentRepository.saveAll(attendanceStudents);
//
//        return savedAttendance;
//    }
    
    private static final Logger log = LoggerFactory.getLogger(AttendanceService.class);

    @Transactional
    public Attendance saveAttendance(Attendance attendance) {
        attendance.setCreatedDate(new Date());
        attendance.setUpdatedDate(new Date());

        // Save the attendance first to get the generated ID
        Attendance savedAttendance = attendanceRepository.save(attendance);

        Optional<BatchProgramCourse> batchProgramCourse = batchProgramCourseRepository.findById(attendance.getBatchProgramCourse().getBatchProgramCourseId());
        //log.info("Batch Program Course: " + batchProgramCourse);

        if (batchProgramCourse == null) {
            log.error("Batch Program Course is null.");
            return savedAttendance;
        }

        Set<Student> students = batchProgramCourse.get().getStudents();
        //log.info("Students in Batch Program Course: " + students);

        if (students == null || students.isEmpty()) {
            log.error("No students found in Batch Program Course.");
            return savedAttendance;
        }

        Set<AttendanceStudent> attendanceStudents = new HashSet<>();
        //log.info("Saved attendance: " + savedAttendance);

        for (Student student : students) {
            log.info("Entered into saving students in attendance: " + student.getStudentId());
            AttendanceStudent attendanceStudent = new AttendanceStudent();
            attendanceStudent.setAttendance(savedAttendance);
            attendanceStudent.setStudent(student);
            attendanceStudent.setIsPresent(false);
           attendanceStudentRepository.save(attendanceStudent);
        }
        Optional<Attendance> modifiedAttendance = attendanceRepository.findById(savedAttendance.getAttendanceId());
        if(modifiedAttendance.isPresent()) {
        return modifiedAttendance.get();
        }else {
        	return null;
        }
    }


    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    public Optional<Attendance> getAttendanceById(Integer id) {
        return attendanceRepository.findById(id);
    }

    public Optional<Attendance> getAttendanceByBatchProgramCourseIdAndDate(int batchProgramCourseId, Date date) {
        return attendanceRepository.findByBatchProgramCourseBatchProgramCourseIdAndDate(batchProgramCourseId, date);
    }

    public void deleteAttendance(Integer id) {
        attendanceRepository.deleteById(id);
    }
}
