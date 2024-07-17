package com.cozentus.training_tracking_application.model;

import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class Student {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id")
    private Integer studentId;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(name="student_code")
    private String studentCode;
    
    @ManyToOne
    @JoinColumn(name="batch_id")
    @JsonIgnoreProperties({"students","program", "course"})
    private Batch batch;
    
    @ManyToMany
    @JoinTable(
        name = "BatchProgramCourseStudent",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "batch_program_course_id")
    )
    @JsonIgnoreProperties({"students", "batch", "program", "course"})
    private Set<BatchProgramCourse> batchProgramCourses;

    @Column(name="created_date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name="updated_date")
    @UpdateTimestamp
    private Date updatedDate;

    @Column(name="created_by")
    private String createdBy="admin";

    @Column(name="updated_by")
    private String updatedBy="admin";

}

