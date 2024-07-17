package com.cozentus.training_tracking_application.model;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Program")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Program {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="program_id")
    private Integer programId;

    @Column(name="program_name",nullable = false)
    private String programName;

    @Column(name="program_code",nullable = false)
    private String programCode;

    @Column(nullable = false)
    private String description;

    @Column(name="theory_time",nullable = false)
    private Integer theoryTime;

    @Column(name="practice_time",nullable = false)
    private Integer practiceTime;
    
    @ManyToMany
    @JoinTable(
        name = "ProgramCourse",
        joinColumns = @JoinColumn(name = "program_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonIgnoreProperties({"programs","topics"})
    private Set<Course> courses;
    
    public Set<Student> getEnrolledStudentsByBatch(Integer batchId) {
        return batchProgramCourses.stream()
                .filter(bpc -> bpc.getBatch().getBatchId().equals(batchId))
                .flatMap(bpc -> bpc.getStudents().stream())
                .collect(Collectors.toSet());
    }
    
    public BatchProgramCourse getBatchProgramCourseByBatch(Integer batchId) {
        return batchProgramCourses.stream()
                .filter(bpc -> bpc.getBatch().getBatchId().equals(batchId))
                .findFirst()
                .orElse(null);
    }
    
    @OneToMany(mappedBy = "program")
    private Set<BatchProgramCourse> batchProgramCourses;

    @Column(name="created_date",nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @Column(name="updated_date",nullable = false)
    @UpdateTimestamp
    private Date updatedDate;

    @Column(name="created_by",nullable = false)
    private String createdBy="admin";

    @Column(name="updated_by",nullable = false)
    private String updatedBy="admin";

}
