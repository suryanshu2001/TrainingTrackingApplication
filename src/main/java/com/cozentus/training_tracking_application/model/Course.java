package com.cozentus.training_tracking_application.model;

import java.util.Date;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "course_name")
    @NotBlank
    @NotNull
    private String courseName;

    @Column(name = "code")
    @NotBlank
    @NotNull
    private String code;

    @Column(name = "description")
    @NotBlank
    @NotNull
    private String description;

    @Column(name = "theory_time")
    @NotNull
    private Integer theoryTime;

    @Column(name = "practice_time")
    @NotNull
    private Integer practiceTime;

    @ManyToMany
    @JoinTable(
        name = "TeacherCourse",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private Set<Teacher> teachers;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    private Set<BatchProgramCourse> batchProgramCourses;
    
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("course")
    private Set<Topic> topics;
    
    @ManyToMany
    @JoinTable(
        name = "ProgramCourse",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "program_id")
    )
    private Set<Program> programs;
    
    @Column(name = "created_date")
    @NotNull
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "updated_date")
    @NotNull
    @UpdateTimestamp
    private Date updatedDate;

    @Column(name = "created_by")
    //@NotNull
    private String createdBy="admin";

    @Column(name = "updated_by")
    //@NotNull
    private String updatedBy="admin";

    // Constructors, Getters, and Setters
}

