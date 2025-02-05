package com.cozentus.training_tracking_application.model;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Daily_Topic_Coverage")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DailyTopicCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_topic_coverage_id")
    private Integer dailyTopicCoverageId;

    @ManyToOne
    @JoinColumn(name = "attendance_id")
    @JsonIgnoreProperties({"batchProgramCourse", "created_by", "created_date", "updated_by", "updated_date"})
    private Attendance attendance;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    @JsonIgnoreProperties({"batchProgramCourseTopics", "course"})
    private Topic topic;

    @Column(name = "daily_coverage")
    private Integer dailyCoverage;
}
