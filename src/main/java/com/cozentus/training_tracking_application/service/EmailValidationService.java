package com.cozentus.training_tracking_application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cozentus.training_tracking_application.dto.EmailValidationResponse;

@Service
public class EmailValidationService {

    @Value("${email.validation.api.key}")
    private String apiKey;

    @Value("${email.validation.api.url}")
    private String apiUrl;

    public boolean validateEmail(String email) {
        String url = apiUrl + "?api_key=" + apiKey + "&email=" + email;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EmailValidationResponse> response = restTemplate.getForEntity(url, EmailValidationResponse.class);

        if (response.getBody() != null) {
            EmailValidationResponse validationResponse = response.getBody();
            return validationResponse.isIs_valid_format().isValue() &&
                    validationResponse.getDeliverability().equals("DELIVERABLE") &&
                    validationResponse.isIs_smtp_valid().isValue();
        } else {
            throw new RuntimeException("Failed to validate email address.");
        }
    }
}
