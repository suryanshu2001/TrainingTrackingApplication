package com.cozentus.training_tracking_application.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cozentus.training_tracking_application.model.Attendance;
import com.cozentus.training_tracking_application.model.AttendanceStudent;
import com.cozentus.training_tracking_application.service.AttendanceStudentService;

@RestController
@RequestMapping("/attendance-student")
@CrossOrigin
public class AttendanceStudentController {

	@Autowired
    private AttendanceStudentService attendanceStudentService;

    @PostMapping
    public ResponseEntity<AttendanceStudent> createAttendanceStudent(@RequestBody AttendanceStudent attendanceStudent) {
        AttendanceStudent createdAttendanceStudent = attendanceStudentService.saveAttendanceStudent(attendanceStudent);
        return ResponseEntity.ok(createdAttendanceStudent);
    }

    @GetMapping
    public ResponseEntity<List<AttendanceStudent>> getAllAttendanceStudents() {
        List<AttendanceStudent> attendanceStudents = attendanceStudentService.getAllAttendanceStudents();
        return ResponseEntity.ok(attendanceStudents);
    }

    @GetMapping("/batch-program-course/{batchProgramCourseId}/date/{date}")
    public ResponseEntity<Attendance> getAttendanceByBatchProgramCourseIdAndDate(@PathVariable int batchProgramCourseId, @PathVariable Date date) {
        Optional<Attendance> attendance = attendanceStudentService.getAttendanceByBatchProgramCourseIdAndDate(batchProgramCourseId, date);
        return attendance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/attendance")
    public ResponseEntity<Attendance> createAttendanceWithStudents(@RequestBody Attendance attendance, @RequestBody List<Integer> studentIds) {
        Attendance createdAttendance = attendanceStudentService.saveAttendanceWithStudents(attendance, studentIds);
        return ResponseEntity.ok(createdAttendance);
    }
    
    @PatchMapping("/update/attendence")
    public ResponseEntity<List<AttendanceStudent>> updateAllAttendanceStudents(@RequestBody List<AttendanceStudent> attendenceStudents){
    	List<AttendanceStudent> updatedAttendanceStudents = attendanceStudentService.updateAllAttendanceStudents(attendenceStudents);
    	return ResponseEntity.ok(updatedAttendanceStudents);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendanceStudent(@PathVariable Integer id) {
        attendanceStudentService.deleteAttendanceStudent(id);
        return ResponseEntity.noContent().build();
    }

}
