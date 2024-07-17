package com.cozentus.training_tracking_application.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cozentus.training_tracking_application.model.Student;
import com.cozentus.training_tracking_application.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
    private EmailValidationService emailValidationService;
	
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    private boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	public Optional<Student> getStudentById(int id) {
		return studentRepository.findById(id);
	}

	public Student saveStudent(Student student) {
//        if (emailValidationService.validateEmail(student.getEmail())) {
		if(true) {
            try {
                String password = UUID.randomUUID().toString();
                String body = String.format("Welcome to Cozentues Training System, %s! \nEmail: %s \nPassword: %s",
                        student.getName(), student.getEmail(), password);
                sendEmail(student.getEmail(), "Welcome Email for Student!", body);
                return studentRepository.save(student);
            } catch (MailException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to send email. Email address may be invalid.");
            }
        } else {
            throw new RuntimeException("Email address is not valid or deliverable.");
        }
    }

    public Student editStudent(Student student) {
        Optional<Student> existingStudentOpt = getStudentById(student.getStudentId());
        if (!existingStudentOpt.isPresent()) {
            throw new RuntimeException("Student not found");
        }

//        Student existingStudent = existingStudentOpt.get();
//        if (!student.getEmail().equals(existingStudent.getEmail()) & emailValidationService.validateEmail(student.getEmail())) {
//            try {
//                String password = UUID.randomUUID().toString();
//                String body = String.format("Welcome to Cozentues Training System, %s! \nEmail: %s \nPassword: %s",
//                        student.getName(), student.getEmail(), password);
//                sendEmail(student.getEmail(), "Welcome Email for Student!", body);
//                
//            } catch (MailException e) {
//                e.printStackTrace();
//                throw new RuntimeException("Failed to send email. Email address may be invalid.");
//            }
//            
//        } 
//        else if(emailValidationService.validateEmail(student.getEmail())) {
//            throw new RuntimeException("Email address is not valid or deliverable.");
//        }
        return studentRepository.save(student);
    }
    
    public Set<Student> findStudentsByBatchAndProgram(int batchId, int programId) {
        return studentRepository.findStudentsByBatchAndProgram(batchId, programId);
    }

    private void sendEmail(String to, String subject, String body) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

	public void deleteStudent(int id) {
		studentRepository.deleteById(id);
	}

	
}
