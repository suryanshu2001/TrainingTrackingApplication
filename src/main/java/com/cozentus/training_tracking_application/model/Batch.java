package com.cozentus.training_tracking_application.model;

import java.util.Date;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Batch")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Batch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batch_id")
	private Integer batchId;

	@Column(name = "batch_name", nullable = false)
	private String batchName;

	@Column(name = "batch_code", nullable = false)
	private String batchCode;

	@Column(name = "start_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@OneToMany(mappedBy = "batch" )
	@JsonIgnoreProperties({"batch","batchProgramCourses"})
	private Set<Student> students;

	@OneToMany(mappedBy = "batch",cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("batch")
	private Set<BatchProgramCourse> batchProgramCourses;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
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
