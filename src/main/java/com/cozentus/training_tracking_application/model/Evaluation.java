package com.cozentus.training_tracking_application.model;
 
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Entity
@Table(name = "Evaluation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Evaluation {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "evaluation_id")
	private Integer evaluationId;
 
	@Column(name = "evaluation_name", nullable = false)
	private String evaluationName;
 
	@Column(name = "evaluation_type", nullable = false)
	private String evaluationType;
 
	@Column(name = "total_marks", nullable = false)
	private Integer totalMarks;
 
	@Column(name = "submission_date", nullable = false)
	private LocalDateTime submissionDate;
 
	@Column(name = "evaluation_time", nullable = false)
	private LocalTime evaluationTime;
 
	@ManyToOne
	@JoinColumn(name = "batch_program_course_id")
	@NotNull
	@NotBlank
	// @JsonIgnoreProperties("batchProgramCourses")
	private BatchProgramCourse batchProgramCourse;
 
//	@OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<EvaluationStudent> evaluationStudents;
 
	@Column(name = "created_date", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
 
	@Column(name = "updated_date", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedDate;
 
	@Column(name = "created_by", nullable = false)
	private String createdBy = "teacher";
 
	@Column(name = "updated_by", nullable = false)
	private String updatedBy = "teacher";
 
	// Method to convert LocalDateTime to UTC
	/**
	 * Converts a LocalDateTime to UTC.
	 *
	 * @param localDateTime The local date-time to be converted.
	 * @return The converted date-time in UTC.
	 */
	public LocalDateTime convertToUTC(LocalDateTime localDateTime) {
		// Convert LocalDateTime to ZonedDateTime in the system default time zone
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
		// Convert the ZonedDateTime to UTC
		return zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
	}
 
}