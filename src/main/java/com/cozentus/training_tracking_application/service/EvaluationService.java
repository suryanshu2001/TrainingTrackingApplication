package com.cozentus.training_tracking_application.service;
 
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.exceptions.EvaluationNotFoundException;
import com.cozentus.training_tracking_application.exceptions.EvaluationServiceException;
import com.cozentus.training_tracking_application.model.Evaluation;
import com.cozentus.training_tracking_application.repository.EvaluationRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class EvaluationService {
 
	 private final EvaluationRepository evaluationRepository;
 
	    /**
	     * Retrieves all evaluations.
	     * @return List of all evaluations.
	     */
	    public List<Evaluation> getAllEvaluations() {
	        try {
	            return evaluationRepository.findAll();
	        } catch (Exception e) {
	            throw new EvaluationServiceException("Failed to retrieve evaluations", e);
	        }
	    }
 
	    /**
	     * Retrieves an evaluation by its ID.
	     * @param id The ID of the evaluation.
	     * @return Optional containing the evaluation if found.
	     * @throws EvaluationNotFoundException if evaluation with the given ID is not found.
	     */
	    public Optional<Evaluation> getEvaluationById(int id) {
	        try {
	            Optional<Evaluation> evaluation = evaluationRepository.findById(id);
	            if (!evaluation.isPresent()) {
	                throw new EvaluationNotFoundException("Evaluation not found with id " + id);
	            }
	            return evaluation;
	        } catch (Exception e) {
	            throw new EvaluationServiceException("Failed to retrieve evaluation with id " + id, e);
	        }
	    }
 
	    /**
	     * Adds a new evaluation.
	     * @param evaluation The evaluation to be added.
	     * @return The added evaluation.
	     * @throws EvaluationServiceException if there's an error while saving the evaluation.
	     */
	    public Evaluation addEvaluation(Evaluation evaluation) {
	        try {
	            return evaluationRepository.save(evaluation);
	        } catch (Exception e) {
	            throw new EvaluationServiceException("Failed to add evaluation", e);
	        }
	    }
 
	    /**
	     * Updates an existing evaluation.
	     * @param id The ID of the evaluation to be updated.
	     * @param updatedEvaluation The updated evaluation data.
	     * @return The updated evaluation.
	     * @throws EvaluationNotFoundException if evaluation with the given ID is not found.
	     * @throws EvaluationServiceException if there's an error while updating the evaluation.
	     */
	    public Evaluation updateEvaluation(Integer id, Evaluation updatedEvaluation) {
	        try {
	            if (evaluationRepository.existsById(id)) {
	                updatedEvaluation.setEvaluationId(id);
	                System.out.println("before update"+updatedEvaluation);
	                Evaluation finalEval = evaluationRepository.save(updatedEvaluation);
	                System.out.println("savedddddd"+updatedEvaluation);
	                return finalEval;
	            } else {
	            	System.out.println("else update");
	                throw new EvaluationNotFoundException("Evaluation not found with id " + id);
	            }
	        } catch (Exception e) {
	        	System.out.println("savedddddd catch"+e);
	            throw new EvaluationServiceException("Failed to update evaluation with id " + id, e);
	        }
	    }
 
	    /**
	     * Deletes an evaluation by its ID.
	     * @param id The ID of the evaluation to be deleted.
	     * @throws EvaluationNotFoundException if evaluation with the given ID is not found.
	     * @throws EvaluationServiceException if there's an error while deleting the evaluation.
	     */
	    public void deleteEvaluation(int id) {
	        try {
	            if (evaluationRepository.existsById(id)) {
	                evaluationRepository.deleteById(id);
	            } else {
	                throw new EvaluationNotFoundException("Evaluation not found with id " + id);
	            }
	        } catch (Exception e) {
	            throw new EvaluationServiceException("Failed to delete evaluation with id " + id, e);
	        }
	    }
}
 