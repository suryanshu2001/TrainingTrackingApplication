package com.cozentus.training_tracking_application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "AttendanceStudent")
@Data
@EqualsAndHashCode(exclude = {"attendance", "student"})
public class AttendanceStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_student_id")
    private Integer attendanceStudentId;

    @ManyToOne
    @JoinColumn(name = "attendance_id")
    @JsonIgnoreProperties({"attendanceStudents","batchProgramCourse"})
    private Attendance attendance;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({"attendanceStudents", "batch", "batchProgramCourses"})
    private Student student;

    @Column(name = "is_present")
    private Boolean isPresent=false;
}
