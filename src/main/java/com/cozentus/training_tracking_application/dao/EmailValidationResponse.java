package com.cozentus.training_tracking_application.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailValidationResponse {
	@JsonProperty("is_valid_format")
    private EmailValidationDetail is_valid_format;

    private String deliverability;

    @JsonProperty("is_smtp_valid")
    private EmailValidationDetail is_smtp_valid;

    public EmailValidationDetail isIs_valid_format() {
        return is_valid_format;
    }

    public void setIs_valid_format(EmailValidationDetail is_valid_format) {
        this.is_valid_format = is_valid_format;
    }

    public String getDeliverability() {
        return deliverability;
    }

    public void setDeliverability(String deliverability) {
        this.deliverability = deliverability;
    }

    public EmailValidationDetail isIs_smtp_valid() {
        return is_smtp_valid;
    }

    public void setIs_smtp_valid(EmailValidationDetail is_smtp_valid) {
        this.is_smtp_valid = is_smtp_valid;
    }
}
