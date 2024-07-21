package com.cozentus.training_tracking_application.exceptions;

public class EmailAlreadyExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4805483109788636244L;

	public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
