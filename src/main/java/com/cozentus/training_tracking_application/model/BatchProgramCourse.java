package com.cozentus.training_tracking_application.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "BatchProgramCourse")
@Data
public class BatchProgramCourse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="batch_program_course_id")
	private int batchProgramCourseId;
	
	@ManyToOne
    @JoinColumn(name = "batch_id")
    @JsonIgnoreProperties({"batchProgramCourses","courses","students"})
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "program_id")
    @JsonIgnoreProperties({"batchProgramCourses","courses"})
    private Program program;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties({"batchProgramCourses","programs"})
    private Course course;
    
    @OneToMany(mappedBy = "batchProgramCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("batchProgramCourse")
    private List<BatchProgramCourseTopic> batchProgramCourseTopics;
    
    @ManyToOne
    @JoinColumn(name="teacher_id")
    @JsonIgnoreProperties("batchProgramCourses")
    private Teacher teacher;
    
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
        name = "BatchProgramCourseStudent",
        joinColumns = @JoinColumn(name = "batch_program_course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonIgnoreProperties({"teacher","program","topics","batch","course","batchProgramCourses"})
    private Set<Student> students = new HashSet<>();
 
    
}
