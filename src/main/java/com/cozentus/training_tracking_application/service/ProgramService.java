package com.cozentus.training_tracking_application.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.dto.ProgramWithStudentsDTO;
import com.cozentus.training_tracking_application.dto.StudentDTO;
import com.cozentus.training_tracking_application.model.BatchProgramCourse;
import com.cozentus.training_tracking_application.model.Program;
import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.repository.ProgramRepository;

@Service
public class ProgramService {
	@Autowired
    private ProgramRepository programRepository;

    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    public Optional<Program> getProgramById(int id) {
        return programRepository.findById(id);
    }

    public Program saveProgram(Program program) {
        return programRepository.save(program);
    }

    public void deleteProgram(Integer id) {
        programRepository.deleteById(id);
    }
    
    public List<ProgramWithStudentsDTO> getProgramsWithStudentsByBatch(Integer batchId) {
        List<Program> programs = programRepository.findProgramsByBatchId(batchId);
        
        return programs.stream().map(program -> {
            ProgramWithStudentsDTO dto = new ProgramWithStudentsDTO();
            dto.setProgramId(program.getProgramId());
            dto.setProgramName(program.getProgramName());
            dto.setProgramCode(program.getProgramCode());
            dto.setDescription(program.getDescription());
            dto.setCourses(program.getCourses());
            
            BatchProgramCourse bpc = program.getBatchProgramCourseByBatch(batchId);
            if (bpc != null) {
                dto.setBatchProgramCourseId(bpc.getBatchProgramCourseId());
                
                Set<StudentDTO> studentDTOs = bpc.getStudents().stream()
                    .map(this::convertToStudentDTO)
                    .collect(Collectors.toSet());
                
                dto.setStudents(studentDTOs);
            } else {
                dto.setStudents(new HashSet<>());
            }
            
            return dto;
        }).collect(Collectors.toList());
    }

    private StudentDTO convertToStudentDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setStudentId(student.getStudentId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setStudentCode(student.getStudentCode());
        return dto;
    }
    
  
}
