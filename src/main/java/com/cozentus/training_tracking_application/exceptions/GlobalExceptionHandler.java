package com.cozentus.training_tracking_application.exceptions;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
 
import com.cozentus.training_tracking_application.model.ApiResponse;
import com.cozentus.training_tracking_application.model.StatusMessage;
 
@ControllerAdvice
public class GlobalExceptionHandler {
 
	/**
     * Handle EvaluationNotFoundException when an evaluation is not found.
     *
     * @param ex The exception instance.
     * @param request The request object.
     * @return ResponseEntity with NOT_FOUND status and exception message.
     */
    @ExceptionHandler(EvaluationNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEvaluationNotFoundException(EvaluationNotFoundException ex, WebRequest request) {
        ApiResponse<Void> response = new ApiResponse<>(StatusMessage.NOT_FOUND, null);
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
 
    /**
     * Handle EvaluationServiceException for general service errors.
     *
     * @param ex The exception instance.
     * @param request The request object.
     * @return ResponseEntity with INTERNAL_SERVER_ERROR status and exception message.
     */
    @ExceptionHandler(EvaluationServiceException.class)
    public ResponseEntity<ApiResponse<Void>> handleEvaluationServiceException(EvaluationServiceException ex, WebRequest request) {
        ApiResponse<Void> response = new ApiResponse<>(StatusMessage.INTERNAL_ERROR, null);
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
 
    /**
     * Handle ResponseStatusException which might be thrown by Spring.
     *
     * @param ex The exception instance.
     * @param request The request object.
     * @return ResponseEntity with the status and reason from the exception.
     */
//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
//        ApiResponse<Void> response = new ApiResponse<>(StatusMessage.valueOf(ex.getStatusCode().value()), ex.getReason());
//        return ResponseEntity.status(ex.getStatusCode()).body(response);
//    }
 
    /**
     * Handle generic Exception for all other cases.
     *
     * @param ex The exception instance.
     * @param request The request object.
     * @return ResponseEntity with INTERNAL_SERVER_ERROR status and a generic error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex, WebRequest request) {
        ApiResponse<Void> response = new ApiResponse<>(StatusMessage.INTERNAL_ERROR, null);
        response.setMessage("An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
 
 