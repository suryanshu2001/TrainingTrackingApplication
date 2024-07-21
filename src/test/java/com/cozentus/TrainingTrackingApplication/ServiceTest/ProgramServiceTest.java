package com.cozentus.TrainingTrackingApplication.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
 
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import com.cozentus.training_tracking_application.dto.ProgramWithStudentsDTO;
import com.cozentus.training_tracking_application.dto.StudentDTO;
import com.cozentus.training_tracking_application.model.Batch;
import com.cozentus.training_tracking_application.model.BatchProgramCourse;
import com.cozentus.training_tracking_application.model.Program;
import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.repository.ProgramRepository;
import com.cozentus.training_tracking_application.service.ProgramService;
 
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

 
public class ProgramServiceTest {
 
    @Mock
    private ProgramRepository programRepository;
 
    @InjectMocks
    private ProgramService programService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testGetAllPrograms() {
        Program program1 = new Program();
        Program program2 = new Program();
        List<Program> programs = Arrays.asList(program1, program2);
 
        when(programRepository.findAll()).thenReturn(programs);
 
        List<Program> result = programService.getAllPrograms();
 
        assertEquals(2, result.size());
        verify(programRepository, times(1)).findAll();
    }
   
    @Test
    void testGetProgramByIdFound() {
        Program program = new Program();
        when(programRepository.findById(1)).thenReturn(Optional.of(program));
 
        Optional<Program> result = programService.getProgramById(1);
 
        assertTrue(result.isPresent());
        assertEquals(program, result.get());
        verify(programRepository, times(1)).findById(1);
    }
 
    @Test
    void testGetProgramByIdNotFound() {
        when(programRepository.findById(1)).thenReturn(Optional.empty());
 
        Optional<Program> result = programService.getProgramById(1);
 
        assertFalse(result.isPresent());
        verify(programRepository, times(1)).findById(1);
    }
 
    @Test
    void testSaveProgram() {
        Program program = new Program();
        when(programRepository.save(program)).thenReturn(program);
 
        Program result = programService.saveProgram(program);
 
        assertEquals(program, result);
        verify(programRepository, times(1)).save(program);
    }
 
    @Test
    void testDeleteProgram() {
        doNothing().when(programRepository).deleteById(1);
 
        programService.deleteProgram(1);
 
        verify(programRepository, times(1)).deleteById(1);
    }
 
    @Test
    void testGetProgramsWithStudentsByBatch() {
        // Arrange
        Program program = new Program();
        program.setProgramId(1);
        program.setProgramName("Program 1");
        program.setProgramCode("P001");
        program.setDescription("Description");
        program.setTheoryTime(10);
        program.setPracticeTime(5);
 
        Batch batch = new Batch();
        batch.setBatchId(1);
 
        BatchProgramCourse bpc = new BatchProgramCourse();
        bpc.setBatch(batch);
       
        Student student = new Student();
        student.setStudentId(1);
        student.setName("John Doe");
        student.setEmail("john@example.com");
        student.setStudentCode("S001");
 
        bpc.setStudents(new HashSet<>(Collections.singletonList(student)));
        program.setBatchProgramCourses(new HashSet<>(Collections.singletonList(bpc)));
 
        when(programRepository.findProgramsByBatchId(1)).thenReturn(Collections.singletonList(program));
 
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setStudentCode(student.getStudentCode());
 
        ProgramWithStudentsDTO dto = new ProgramWithStudentsDTO();
        dto.setProgramId(program.getProgramId());
        dto.setProgramName(program.getProgramName());
        dto.setProgramCode(program.getProgramCode());
        dto.setDescription(program.getDescription());
        dto.setBatchProgramCourseId(bpc.getBatch().getBatchId()); // Assuming BatchProgramCourse has batch ID
        dto.setCourses(program.getCourses());
        dto.setStudents(Collections.singleton(studentDTO));
 
        // Act
        List<ProgramWithStudentsDTO> result = programService.getProgramsWithStudentsByBatch(1);
 
        // Assert
        assertEquals(1, result.size());
//        assertEquals(dto, result.get(0));
        verify(programRepository, times(1)).findProgramsByBatchId(1);
    }
}