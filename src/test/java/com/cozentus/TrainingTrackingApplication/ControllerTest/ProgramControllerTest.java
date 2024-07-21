package com.cozentus.TrainingTrackingApplication.ControllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
 
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import com.cozentus.training_tracking_application.controller.ProgramController;
import com.cozentus.training_tracking_application.dto.ProgramWithStudentsDTO;
import com.cozentus.training_tracking_application.model.Program;
import com.cozentus.training_tracking_application.service.ProgramService;
 
class ProgramControllerTest {
 
    @Mock
    private ProgramService programService;
 
    @InjectMocks
    private ProgramController programController;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testGetAllPrograms() {
        Program program = new Program();
        List<Program> programs = Arrays.asList(program);
        when(programService.getAllPrograms()).thenReturn(programs);
 
        ResponseEntity<List<Program>> response = programController.getAllPrograms();
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(programService, times(1)).getAllPrograms();
    }
 
    @Test
    void testGetProgramById_Found() {
        Program program = new Program();
        when(programService.getProgramById(1)).thenReturn(Optional.of(program));
 
        ResponseEntity<Program> response = programController.getProgramById(1);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(programService, times(1)).getProgramById(1);
    }
 
    @Test
    void testGetProgramById_NotFound() {
        when(programService.getProgramById(1)).thenReturn(Optional.empty());
 
        ResponseEntity<Program> response = programController.getProgramById(1);
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(programService, times(1)).getProgramById(1);
    }
 
    @Test
    void testCreateProgram() {
        Program program = new Program();
        when(programService.saveProgram(any(Program.class))).thenReturn(program);
 
        ResponseEntity<Program> response = programController.createProgram(program);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(programService, times(1)).saveProgram(any(Program.class));
    }
 
    @Test
    void testUpdateProgram_Found() {
        Program program = new Program();
        when(programService.getProgramById(1)).thenReturn(Optional.of(program));
        when(programService.saveProgram(any(Program.class))).thenReturn(program);
 
        ResponseEntity<Program> response = programController.updateProgram(1, program);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(programService, times(1)).getProgramById(1);
        verify(programService, times(1)).saveProgram(any(Program.class));
    }
 
    @Test
    void testUpdateProgram_NotFound() {
        when(programService.getProgramById(1)).thenReturn(Optional.empty());
 
        ResponseEntity<Program> response = programController.updateProgram(1, new Program());
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(programService, times(1)).getProgramById(1);
        verify(programService, never()).saveProgram(any(Program.class));
    }
 
    @Test
    void testDeleteProgram_Found() {
        Program program = new Program();
        when(programService.getProgramById(1)).thenReturn(Optional.of(program));
        doNothing().when(programService).deleteProgram(1);
 
        ResponseEntity<Void> response = programController.deleteProgram(1);
 
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(programService, times(1)).getProgramById(1);
        verify(programService, times(1)).deleteProgram(1);
    }
 
    @Test
    void testDeleteProgram_NotFound() {
        when(programService.getProgramById(1)).thenReturn(Optional.empty());
 
        ResponseEntity<Void> response = programController.deleteProgram(1);
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(programService, times(1)).getProgramById(1);
        verify(programService, never()).deleteProgram(1);
    }
 
    @Test
    void testGetProgramsWithStudentsByBatch() {
        ProgramWithStudentsDTO dto = new ProgramWithStudentsDTO();
        List<ProgramWithStudentsDTO> dtos = Arrays.asList(dto);
        when(programService.getProgramsWithStudentsByBatch(1)).thenReturn(dtos);
 
        ResponseEntity<List<ProgramWithStudentsDTO>> response = programController.getProgramsWithStudentsByBatch(1);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(programService, times(1)).getProgramsWithStudentsByBatch(1);
    }
}