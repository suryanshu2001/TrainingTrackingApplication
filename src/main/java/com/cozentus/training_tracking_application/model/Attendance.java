package com.cozentus.training_tracking_application.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Attendance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    
    @OneToMany(mappedBy = "attendance",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("attendance")
    private List<DailyTopicCoverage> dailyTopicCoverages;
    
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

