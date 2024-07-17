package com.cozentus.training_tracking_application.model;

import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import lombok.ToString;

@Entity
@Table(name = "Teacher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class Teacher {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="teacher_id")
    private Integer teacherId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany
    @JoinTable(
        name = "TeacherCourse",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonIgnoreProperties({"teachers","batchProgramCourses","programs"})
    private Set<Course> courses;
    
//    @OneToMany(mappedBy = "teacher")
//    private Set<Evaluation> evaluations;
    
    @OneToMany(mappedBy = "teacher")
    @JsonIgnoreProperties({"students", "batch", "program", "course","teacher"})
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

