package com.cozentus.TrainingTrackingApplication.ControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
 

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
 
import com.cozentus.training_tracking_application.controller.TopicController;
import com.cozentus.training_tracking_application.model.Topic;
import com.cozentus.training_tracking_application.repository.FileRepository;
import com.cozentus.training_tracking_application.service.FileService;
import com.cozentus.training_tracking_application.service.TopicService;
 
public class TopicControllerTest {
 
    @Mock
    private TopicService topicService;
 
    @Mock
    private FileService fileService;
 
    @Mock
    private FileRepository fileRepository;
 
    @InjectMocks
    private TopicController topicController;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    public void testGetAllTopics_Success() {
        // Arrange
        Topic topic1 = new Topic();
        Topic topic2 = new Topic();
        when(topicService.getAllTopics()).thenReturn(Arrays.asList(topic1, topic2));
 
        // Act
        ResponseEntity<List<Topic>> response = topicController.getAllTopics();
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
 
    @Test
    public void testGetTopicById_Success() {
        // Arrange
        Topic topic = new Topic();
        when(topicService.getTopicById(1)).thenReturn(topic);
 
        // Act
        ResponseEntity<Topic> response = topicController.getTopicById(1);
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(topic, response.getBody());
    }
 
    @Test
    public void testGetTopicById_NotFound() {
        // Arrange
        when(topicService.getTopicById(1)).thenReturn(null);
 
        // Act
        ResponseEntity<Topic> response = topicController.getTopicById(1);
 
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    public void testGetTopicByCourseId_Success() {
        // Arrange
        List<Topic> topics = Arrays.asList(new Topic(), new Topic());
        when(topicService.getTopicsByCourseId(1)).thenReturn(Optional.of(topics));
 
        // Act
        ResponseEntity<Optional<List<Topic>>> response = topicController.getTopicByCourseId(1);
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(topics, response.getBody().get());
    }
 
    @Test
    public void testGetTopicByCourseId_NotFound() {
        // Arrange
        when(topicService.getTopicsByCourseId(1)).thenReturn(Optional.empty());
 
        // Act
        ResponseEntity<Optional<List<Topic>>> response = topicController.getTopicByCourseId(1);
 
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    public void testCreateTopic_Success() {
        // Arrange
        Topic topic = new Topic();
        when(topicService.saveTopic(any(Topic.class))).thenReturn(topic);
 
        // Act
        ResponseEntity<Topic> response = topicController.createTopic(topic);
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(topic, response.getBody());
    }
 
    @Test
    public void testServeFile_NotFound() throws Exception {
        // Arrange
        when(fileRepository.findById(1)).thenReturn(Optional.empty());
 
        // Act
        ResponseEntity<Resource> response = topicController.serveFile(1);
 
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    public void testUploadFiles_Success() {
        // Arrange
        MultipartFile file1 = new MockMultipartFile("file1", "file1.txt", "text/plain", "file1 content".getBytes());
        MultipartFile file2 = new MockMultipartFile("file2", "file2.txt", "text/plain", "file2 content".getBytes());
 
        // Act
        ResponseEntity<String> response = topicController.uploadFiles(1, new MultipartFile[]{file1, file2}, "admin");
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Files uploaded successfully", response.getBody());
    }
 
    @Test
    public void testUploadFiles_Failure() {
        // Arrange
        MultipartFile file = new MockMultipartFile("file", "file.txt", "text/plain", "file content".getBytes());
        try {
            doThrow(new RuntimeException("Upload error")).when(fileService).store(file, 1, "admin");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        // Act
        ResponseEntity<String> response = topicController.uploadFiles(1, new MultipartFile[]{file}, "admin");
 
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to upload files: Upload error", response.getBody());
    }
 
 
    @Test
    public void testUpdateTopic_Success() {
        // Arrange
        Topic topic = new Topic();
        topic.setTopicId(1);
        topic.setCreatedDate(new Date());
        when(topicService.getTopicById(1)).thenReturn(topic);
        when(topicService.saveTopic(any(Topic.class))).thenReturn(topic);
 
        // Act
        ResponseEntity<Topic> response = topicController.updateTopic(1, topic);
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(topic, response.getBody());
    }
 
    @Test
    public void testDeleteTopic_Success() {
        // Arrange
        when(topicService.deleteTopic(1)).thenReturn(true);
 
        // Act
        ResponseEntity<Void> response = topicController.deleteTopic(1);
 
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
 
    @Test
    public void testDeleteTopic_NotFound() {
        // Arrange
        when(topicService.deleteTopic(1)).thenReturn(false);
 
        // Act
        ResponseEntity<Void> response = topicController.deleteTopic(1);
 
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}