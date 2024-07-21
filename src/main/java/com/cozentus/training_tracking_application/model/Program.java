package com.cozentus.training_tracking_application.model;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

    @OneToMany(mappedBy = "program",cascade = CascadeType.ALL )
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
