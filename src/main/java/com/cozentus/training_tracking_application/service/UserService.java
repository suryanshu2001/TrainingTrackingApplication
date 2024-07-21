package com.cozentus.training_tracking_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.dto.UserDTO;
import com.cozentus.training_tracking_application.model.Teacher;
import com.cozentus.training_tracking_application.model.User;
import com.cozentus.training_tracking_application.repository.TeacherRepository;
import com.cozentus.training_tracking_application.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

    public UserDTO findByUserEmail(String userEmail) {
        Optional<User> user=userRepository.findByUserEmail(userEmail);
        if(user.isPresent()) {
        	log.info("user got:"+user.get());
        	UserDTO dto= new UserDTO();
        	dto.setUserEmail(user.get().getUserEmail());
        	dto.setUserName(user.get().getUserName());
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
