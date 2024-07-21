package com.cozentus.training_tracking_application;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TrainingTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingTrackingApplication.class, args);
	}
	
	@Bean
     ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
