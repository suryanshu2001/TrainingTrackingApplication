package com.cozentus.training_tracking_application.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.cozentus.training_tracking_application.model.Evaluation;
 
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer>{
 
}