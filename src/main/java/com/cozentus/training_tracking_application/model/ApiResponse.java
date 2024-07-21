package com.cozentus.training_tracking_application.model;
 

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
* Generic class for wrapping API responses.
* @param <T> Type of the data included in the response.
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
 
	 	private int responseCode;
	    private String message;
	    private T data;
	    
	    /**
	     * Constructor to initialize ApiResponse with a StatusMessage and data.
	     * @param statusMessage The status message for the response.
	     * @param data The data to be included in the response.
	     */
 
 
	    public ApiResponse(StatusMessage statusMessage, T data) {
	        this.responseCode = getCodeFromMessage(statusMessage);
	        this.message = statusMessage.getMessage();
	        this.data = data;
	    }
	    
	    /**
	     * Converts StatusMessage to HTTP response code.
	     * @param statusMessage The status message.
	     * @return Corresponding HTTP response code.
	     */
 
	    private int getCodeFromMessage(StatusMessage statusMessage) {
	        switch (statusMessage) {
	            case SUCCESS: return 200;
	            case NOT_FOUND: return 404;
	            case BAD_REQUEST: return 400;
	            case UNAUTHORIZED: return 401;
	            case FORBIDDEN: return 403;
	            case INTERNAL_ERROR: return 417;
	            default: return 500;
	        }
	    }
}
 