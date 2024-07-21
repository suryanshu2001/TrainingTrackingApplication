package com.cozentus.training_tracking_application.dto;

import java.util.Set;

import com.cozentus.training_tracking_application.model.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProgramWithStudentsDTO {
    private Integer programId;
    private String programName;
    private String programCode;
    private String description;
    private Integer batchProgramCourseId;
    private Set<Course> courses;
    private Set<StudentDTO> students;

}
