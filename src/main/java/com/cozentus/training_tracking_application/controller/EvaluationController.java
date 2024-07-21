package com.cozentus.training_tracking_application.controller;
 
import java.util.List;
import java.util.Optional;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.cozentus.training_tracking_application.exceptions.EvaluationServiceException;
import com.cozentus.training_tracking_application.model.ApiResponse;
import com.cozentus.training_tracking_application.model.Evaluation;
import com.cozentus.training_tracking_application.model.StatusMessage;
import com.cozentus.training_tracking_application.service.EvaluationService;
 
import lombok.RequiredArgsConstructor;
 
@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
@CrossOrigin
public class EvaluationController {
	
	private final EvaluationService evaluationService;
 
	/**
     * Retrieves all evaluations.
     * @return ResponseEntity containing a list of evaluations or a not found message if no data is present.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Evaluation>>> getAllEvaluations() {
        try {
            List<Evaluation> evaluations = evaluationService.getAllEvaluations();
            if (evaluations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(new ApiResponse<>(StatusMessage.NOT_FOUND, null));
            } else {
                return ResponseEntity.ok(new ApiResponse<>(StatusMessage.SUCCESS, evaluations));
            }
        } catch (EvaluationServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(StatusMessage.INTERNAL_ERROR, null));
        }
    }
 
    /**
     * Retrieves an evaluation by its ID.
     * @param id The ID of the evaluation.
     * @return ResponseEntity containing the evaluation or a not found message if the evaluation is not present.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Evaluation>> getEvaluationById(@PathVariable int id) {
        try {
            Optional<Evaluation> evaluation = evaluationService.getEvaluationById(id);
            if (evaluation.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(StatusMessage.SUCCESS, evaluation.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(new ApiResponse<>(StatusMessage.NOT_FOUND, null));
            }
        } catch (EvaluationServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(StatusMessage.INTERNAL_ERROR, null));
        }
    }
 
    /**
     * Adds a new evaluation.
     * @param evaluation The evaluation to be added.
     * @return ResponseEntity containing the added evaluation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Evaluation>> addEvaluation(@RequestBody Evaluation evaluation) {
        try {
            if (evaluation != null) {
                Evaluation addedEvaluation = evaluationService.addEvaluation(evaluation);
                return ResponseEntity.status(HttpStatus.CREATED)
                                     .body(new ApiResponse<>(StatusMessage.SUCCESS, addedEvaluation));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body(new ApiResponse<>(StatusMessage.BAD_REQUEST, null));
            }
        } catch (EvaluationServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(StatusMessage.INTERNAL_ERROR, null));
        }
    }
 
    /**
     * Updates an existing evaluation.
     * @param id The ID of the evaluation to be updated.
     * @param updatedEvaluation The updated evaluation data.
     * @return ResponseEntity containing the updated evaluation or a not found message if the evaluation is not present.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Evaluation>> updateEvaluation(@PathVariable Integer id, @RequestBody Evaluation updatedEvaluation) {
        try {
            Optional<Evaluation> existingEvaluation = evaluationService.getEvaluationById(id);
            if (existingEvaluation.isPresent()) {
                if (updatedEvaluation != null) {
                	System.out.println(updatedEvaluation);
                    Evaluation evaluation = evaluationService.updateEvaluation(id, updatedEvaluation);
                    System.out.println("exsecuted"+updatedEvaluation);
                    return ResponseEntity.ok(new ApiResponse<>(StatusMessage.SUCCESS, evaluation));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                         .body(new ApiResponse<>(StatusMessage.BAD_REQUEST, null));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(new ApiResponse<>(StatusMessage.NOT_FOUND, null));
            }
        } catch (EvaluationServiceException e) {
        	System.out.println("catchhhh");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(StatusMessage.INTERNAL_ERROR, null));
        }
    }
 
    /**
     * Deletes an evaluation by its ID.
     * @param id The ID of the evaluation to be deleted.
     * @return ResponseEntity indicating successful deletion or a not found message if the evaluation is not present.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEvaluation(@PathVariable int id) {
        try {
            Optional<Evaluation> existingEvaluation = evaluationService.getEvaluationById(id);
            if (existingEvaluation.isPresent()) {
                evaluationService.deleteEvaluation(id);
                return ResponseEntity.ok(new ApiResponse<>(StatusMessage.SUCCESS, null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(new ApiResponse<>(StatusMessage.NOT_FOUND, null));
            }
        } catch (EvaluationServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(StatusMessage.INTERNAL_ERROR, null));
        }
    }
}
 