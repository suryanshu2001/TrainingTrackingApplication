package com.cozentus.training_tracking_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.dto.UserDTO;
import com.cozentus.training_tracking_application.model.Teacher;
import com.cozentus.training_tracking_application.model.User;
import com.cozentus.training_tracking_application.repository.TeacherRepository;
import com.cozentus.training_tracking_application.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO findByUserEmailAndPassword(String userEmail, String userPassword) {
        Optional<User> user=userRepository.findByUserEmailAndUserPassword(userEmail,userPassword);
        if(user.isPresent()) {
        	UserDTO dto= new UserDTO();
        	dto.setUserEmail(user.get().getUserEmail());
        	dto.setUserName(user.get().getUsername());
        	Teacher teacher=teacherRepository.findByEmail(user.get().getUserEmail());
        	if(teacher!=null) {
        		dto.setUserId(teacher.getTeacherId());
        	}
        	dto.setUserRole(user.get().getUserRole());
        	
        	return dto;
        }
        else {
        	return null;
        }
    }
}
