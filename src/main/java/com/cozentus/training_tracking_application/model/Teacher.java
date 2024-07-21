package com.cozentus.training_tracking_application.model;

import java.util.Date;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "Teacher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private Date createdDate;
    
    
    @Column(name="updated_date")
    private Date updatedDate;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="updated_by")
    private String updatedBy;
    
    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
        updatedDate = new Date();
        createdBy = getCurrentUsername(); // Use the current logged-in user
        updatedBy = getCurrentUsername(); // Use the current logged-in user
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = new Date();
        updatedBy = getCurrentUsername(); // Use the current logged-in user
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return "anonymous";
    }

}

