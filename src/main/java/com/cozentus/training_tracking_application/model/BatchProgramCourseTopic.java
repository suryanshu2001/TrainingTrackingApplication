package com.cozentus.training_tracking_application.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "batch_program_course_topic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchProgramCourseTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_program_course_topic_id")
    private Integer batchProgramCourseTopicId;

    @ManyToOne
    @JoinColumn(name = "batch_program_course_id")
    @JsonIgnoreProperties({"batchProgramCourseTopics", "teacher", "batch", "program", "students", "course.teachers"})
    private BatchProgramCourse batchProgramCourse;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    @JsonIgnoreProperties({"batchProgramCourseTopics","course"})
    private Topic topic;

    @Column(name = "percentage_completed")
    @Min(0)
    @Max(100)
    private Integer percentageCompleted=0;
}
