package com.cozentus.training_tracking_application.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.model.Attendance;
import com.cozentus.training_tracking_application.model.AttendanceStudent;
import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.repository.AttendanceRepository;
import com.cozentus.training_tracking_application.repository.AttendanceStudentRepository;
import com.cozentus.training_tracking_application.repository.BatchProgramCourseRepository;
import com.cozentus.training_tracking_application.repository.StudentRepository;

@Service
public class AttendanceStudentService {

	@Autowired
    private AttendanceStudentRepository attendanceStudentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private BatchProgramCourseRepository batchProgramCourseRepository;

    @Autowired
    private StudentRepository studentRepository;

    public AttendanceStudent saveAttendanceStudent(AttendanceStudent attendanceStudent) {
        return attendanceStudentRepository.save(attendanceStudent);
    }

    public List<AttendanceStudent> getAllAttendanceStudents() {
        return attendanceStudentRepository.findAll();
    }

    public void deleteAttendanceStudent(Integer id) {
        attendanceStudentRepository.deleteById(id);
    }

    public Optional<Attendance> getAttendanceByBatchProgramCourseIdAndDate(int batchProgramCourseId, Date date) {
        return attendanceRepository.findByBatchProgramCourseBatchProgramCourseIdAndDate(batchProgramCourseId, date);
    }

    public Attendance saveAttendanceWithStudents(Attendance attendance, List<Integer> studentIds) {
        // Save Attendance
        Attendance savedAttendance = attendanceRepository.save(attendance);

        // Save AttendanceStudent entries
        for (Integer studentId : studentIds) {
            AttendanceStudent attendanceStudent = new AttendanceStudent();
            attendanceStudent.setAttendance(savedAttendance);
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));
            attendanceStudent.setStudent(student);
            attendanceStudent.setIsPresent(true); // Default value, can be customized
            attendanceStudentRepository.save(attendanceStudent);
        }

        return savedAttendance;
    }
    
    public List<AttendanceStudent> updateAllAttendanceStudents (List<AttendanceStudent> attendenceStudents){
    	return attendanceStudentRepository.saveAllAndFlush(attendenceStudents);
    }

}
