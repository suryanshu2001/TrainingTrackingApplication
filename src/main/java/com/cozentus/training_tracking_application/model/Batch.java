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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Batch")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
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

	@OneToMany(mappedBy = "batch",cascade = CascadeType.ALL)
	@JsonIgnoreProperties("batch")
	private Set<BatchProgramCourse> batchProgramCourses;

	@Column(name = "created_date", nullable = false)
	@CreationTimestamp
	private Date createdDate;

	@Column(name = "updated_date", nullable = false)
	@UpdateTimestamp
	private Date updatedDate;

	@Column(name = "created_by", nullable = false)
	private String createdBy = "admin";

	@Column(name = "updated_by", nullable = false)
	private String updatedBy = "admin";

}
