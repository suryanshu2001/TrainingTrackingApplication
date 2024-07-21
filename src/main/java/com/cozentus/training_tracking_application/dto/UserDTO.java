package com.cozentus.training_tracking_application.dto;

import com.cozentus.training_tracking_application.model.User;

import lombok.Data;

@Data
public class UserDTO {
	private String userName;
	private String userEmail;
	private String userRole;
	private Integer userId;

}
