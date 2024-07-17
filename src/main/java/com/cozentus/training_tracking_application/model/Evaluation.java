package com.cozentus.training_tracking_application.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Evaluation")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="evaluation_id")
    private int evaluationId;

    @Column(name="evaluation_name",nullable = false)
    private String evaluationName;

    @Column(name="evaluation_type",nullable = false)
    private String evaluationType;
    
    @Column(name="total_marks",nullable = false)
    private Integer totalMarks;
    
    @Column(name="submission_date",nullable = false)
    private Date submissionDate;
    
    @ManyToOne
    @JoinColumn(name = "batch_program_course_id")
    @NotNull
    @NotBlank
    //@JsonIgnoreProperties("batchProgramCourses")
    private BatchProgramCourse batchProgramCourse;

    @Column(name="created_date",nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @Column(name="updated_date",nullable = false)
    @UpdateTimestamp
    private Date updatedDate;

    @Column(name="created_by",nullable = false)
    private String createdBy="teacher";

    @Column(name="updated_by",nullable = false)
    private String updatedBy="teacher";

}

