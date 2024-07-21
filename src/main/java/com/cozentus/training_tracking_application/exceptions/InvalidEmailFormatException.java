package com.cozentus.training_tracking_application.exceptions;

public class InvalidEmailFormatException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -361125514349468201L;

	public InvalidEmailFormatException(String message) {
        super(message);
    }
}
