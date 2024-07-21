package com.cozentus.training_tracking_application.model;
 
import lombok.Getter;
 
@Getter
public enum StatusMessage {
 
    SUCCESS("Success"),
    NOT_FOUND("Resource not found"),
    BAD_REQUEST("Bad request"),
    UNAUTHORIZED("Unauthorized access"),
    FORBIDDEN("Forbidden access"),
    INTERNAL_ERROR("Custom error");
 
    private final String message;
 
    StatusMessage(String message) {
        this.message = message;
    }
}