package com.cozentus.training_tracking_application.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Attendance")
@Data
@EqualsAndHashCode(exclude = {"batchProgramCourse", "attendanceStudents"})
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="attendance_id")
    private Integer attendanceId;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "batch_program_course_id")
    @JsonIgnoreProperties({"teacher","batch","program","students","course"})
    private BatchProgramCourse batchProgramCourse;
    
    @OneToMany(mappedBy = "attendance", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties
    private Set<AttendanceStudent> attendanceStudents = new HashSet<>();
    
    @OneToMany(mappedBy = "attendance")
    @JsonIgnoreProperties("attendance")
    private List<DailyTopicCoverage> dailyTopicCoverages;
    
    @Column(name="created_date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name="updated_date")
    @UpdateTimestamp
    private Date updatedDate;

    @Column(name="created_by")
    private String createdBy="teacher";

    @Column(name="updated_by")
    private String updatedBy="teacher";

}

