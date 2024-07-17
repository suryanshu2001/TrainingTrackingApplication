package com.cozentus.training_tracking_application.model;

import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Topic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Integer topicId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @NotNull
    @NotBlank
    @JsonIgnoreProperties("topics")
    private Course course;

    @Column(name = "topic_name")
    @NotNull
    @NotBlank
    private String topicName;
    
    @Column(name="topic_order")
    @NotNull
    private Integer order;

    @Column(name = "content")
    @NotNull
    @NotBlank
    private String content;

    @Column(name = "theory_time")
    @NotNull
    private Integer theoryTime;

    @Column(name = "practice_time")
    @NotNull
    private Integer practiceTime;

    @Column(name = "summary")
    @NotNull
    @NotBlank
    private String summary;
    
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("topic")
    private Set<File> files;

    @Column(name = "created_date")
    @CreationTimestamp
    @NotNull
    private Date createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    @NotNull
    private Date updatedDate;

    @Column(name = "created_by")
    private String createdBy="user";

    @Column(name = "updated_by")
    private String updatedBy="user";

}

