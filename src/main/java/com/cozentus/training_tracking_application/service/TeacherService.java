package com.cozentus.training_tracking_application.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.model.Teacher;
import com.cozentus.training_tracking_application.model.User;
import com.cozentus.training_tracking_application.repository.TeacherRepository;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    
    
    @Autowired
    private UserService userService;
    
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    private boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    public List<Teacher> getAllTeachers(){
    	return teacherRepository.findAll();
    }
    
    public Optional<Teacher> getTeacherById(int id) {
        return teacherRepository.findById(id);
    }

    public Teacher saveTeacher(Teacher teacher) {

    	
            try {
                
                sendWelcomeEmail(teacher);
                return teacherRepository.save(teacher);
            } catch (MailException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to send email. Email address may be invalid.");
            }

    }

    public Teacher updateTeacher(Integer id,Teacher teacher) {
    	Optional<Teacher> existingTeacherOpt = getTeacherById(id);
    	System.out.println("execution1");
        if (!existingTeacherOpt.isPresent()) {
        	System.out.println("execution2");
            throw new RuntimeException("Teacher not found");
            
        }

        Teacher existingTeacher = existingTeacherOpt.get();
        System.out.println("execution3");
        if (!teacher.getEmail().equals(existingTeacher.getEmail())) {
        	System.out.println("execution4");
        	try {
        		System.out.println("execution5");
                sendUpdatedEmail(teacher);
                System.out.println("execution6");
            } catch (MailException e) {
            	System.out.println("execution7");
                e.printStackTrace();
                System.out.println("execution8");
                throw new RuntimeException("Failed to send email. Email address may be invalid.");
                
            }
            
        } 

        System.out.println("execution10");
        return teacherRepository.save(teacher);
        
    }

    public void deleteTeacher(int id) {
        teacherRepository.deleteById(id);
    }

    private void sendWelcomeEmail(Teacher teacher) {
        try {
            String password = getSaltString();
            String body = String.format("Welcome to Cozentus Training System, %s! \nEmail: %s \nPassword: %s",
                    teacher.getName(), teacher.getEmail(), password);
            emailService.sendEmail(teacher.getEmail(), "Welcome Email for Teacher!", body);
            //creating user for this teacher
            User user = new User();
            user.setUserEmail(teacher.getEmail());
            user.setPassword(password);
            user.setCreatedBy("admin");
            user.setCreatedDate(new Date());
            user.setUpdatedBy("admin");
            user.setUpdatedBy("admin");
            user.setUsername(teacher.getName());
            user.setUserRole("teacher");
            userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    private void sendUpdatedEmail(Teacher teacher) {
        try {
        	String password = UUID.randomUUID().toString();
            String body = String.format("Your account information has been updated.\nName: %s\nEmail: %s \nPassword: %s",
                    teacher.getName(), teacher.getEmail(),password);
            emailService.sendEmail(teacher.getEmail(), "Account Updated", body);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYabcdefghijklmnopqrstuvwxyzZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() <5) { 
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

}
