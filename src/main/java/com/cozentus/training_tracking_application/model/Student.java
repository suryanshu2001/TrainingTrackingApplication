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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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

