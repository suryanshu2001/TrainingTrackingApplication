package com.cozentus.training_tracking_application.dto;

public class EmailValidationDetail {
	private boolean value;
    private String text;

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
