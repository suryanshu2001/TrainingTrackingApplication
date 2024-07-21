package com.cozentus.training_tracking_application.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cozentus.training_tracking_application.dto.ProgramWithStudentsDTO;
import com.cozentus.training_tracking_application.model.Program;
import com.cozentus.training_tracking_application.service.ProgramService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/programs")
@CrossOrigin
@Slf4j
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @GetMapping
    public ResponseEntity<List<Program>> getAllPrograms() {
        List<Program> programs = programService.getAllPrograms();
        log.info("get programs:"+programs);
        return ResponseEntity.ok(programs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Program> getProgramById(@PathVariable int id) {
        Optional<Program> program = programService.getProgramById(id);
        if (program.isPresent()) {
            return ResponseEntity.ok(program.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Program> createProgram(@RequestBody Program program) {
        Program savedProgram = programService.saveProgram(program);
        return ResponseEntity.ok(savedProgram);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Program> updateProgram(@PathVariable int id, @RequestBody Program program) {
        Optional<Program> existingProgram = programService.getProgramById(id);
        if (existingProgram.isPresent()) {
            program.setProgramId(id);
            program.setCreatedBy("admin");
            program.setUpdatedBy("admin");
            program.setCreatedDate(new Date());
            program.setUpdatedDate(new Date());
            Program updatedProgram = programService.saveProgram(program);
            return ResponseEntity.ok(updatedProgram);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable int id) {
        Optional<Program> existingProgram = programService.getProgramById(id);
        if (existingProgram.isPresent()) {
            programService.deleteProgram(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<ProgramWithStudentsDTO>> getProgramsWithStudentsByBatch(@PathVariable Integer batchId) {
        List<ProgramWithStudentsDTO> programs = programService.getProgramsWithStudentsByBatch(batchId);
        return ResponseEntity.ok(programs);
    }
    
}
