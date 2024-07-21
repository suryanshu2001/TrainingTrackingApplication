package com.cozentus.TrainingTrackingApplication.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
 
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import com.cozentus.training_tracking_application.model.Topic;
import com.cozentus.training_tracking_application.repository.TopicRepository;
import com.cozentus.training_tracking_application.service.TopicService;
 
public class TopicServiceTest {
 
    @Mock
    private TopicRepository topicRepository;
 
    @InjectMocks
    private TopicService topicService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testGetAllTopics() {
        // Arrange
        Topic topic1 = new Topic();
        Topic topic2 = new Topic();
        List<Topic> topics = Arrays.asList(topic1, topic2);
        when(topicRepository.findAll()).thenReturn(topics);
 
        // Act
        List<Topic> result = topicService.getAllTopics();
 
        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(2, result.size(), "The size of the topics list should be 2");
    }
 
    @Test
    void testGetTopicById_TopicExists() {
        // Arrange
        Topic topic = new Topic();
        when(topicRepository.findById(anyInt())).thenReturn(Optional.of(topic));
 
        // Act
        Topic result = topicService.getTopicById(1);
 
        // Assert
        assertNotNull(result, "The result should not be null");
    }
 
    @Test
    void testGetTopicById_TopicDoesNotExist() {
        // Arrange
        when(topicRepository.findById(anyInt())).thenReturn(Optional.empty());
 
        // Act
        Topic result = topicService.getTopicById(1);
 
        // Assert
        assertNull(result, "The result should be null when the topic does not exist");
    }
 
    @Test
    void testSaveTopic() {
        // Arrange
        Topic topic = new Topic();
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);
 
        // Act
        Topic result = topicService.saveTopic(topic);
 
        // Assert
        assertNotNull(result, "The result should not be null");
    }
 
    @Test
    void testDeleteTopic_TopicExists() {
        // Arrange
        when(topicRepository.findById(anyInt())).thenReturn(Optional.of(new Topic()));
 
        // Act
        boolean result = topicService.deleteTopic(1);
 
        // Assert
        assertTrue(result, "The topic should be deleted successfully");
        verify(topicRepository, times(1)).deleteById(anyInt());
    }
 
    @Test
    void testDeleteTopic_TopicDoesNotExist() {
        // Arrange
        when(topicRepository.findById(anyInt())).thenReturn(Optional.empty());
 
        // Act
        boolean result = topicService.deleteTopic(1);
 
        // Assert
        assertFalse(result, "The topic should not be deleted as it does not exist");
        verify(topicRepository, times(0)).deleteById(anyInt());
    }
 
    @Test
    void testGetTopicsByCourseId() {
        // Arrange
        Topic topic1 = new Topic();
        Topic topic2 = new Topic();
        List<Topic> topics = Arrays.asList(topic1, topic2);
        when(topicRepository.findByCourseCourseId(anyInt())).thenReturn(topics);
 
        // Act
        Optional<List<Topic>> result = topicService.getTopicsByCourseId(1);
 
        // Assert
        assertTrue(result.isPresent(), "The result should be present");
        assertEquals(2, result.get().size(), "The size of the topics list should be 2");
    }
 
    @Test
    void testGetTopicsByCourseId_NoTopics() {
        // Arrange
        when(topicRepository.findByCourseCourseId(anyInt())).thenReturn(Collections.emptyList());
 
        // Act
        Optional<List<Topic>> result = topicService.getTopicsByCourseId(1);
 
        // Assert
        assertTrue(result.isPresent(), "The result should be present even if the list is empty");
        assertTrue(result.get().isEmpty(), "The list of topics should be empty");
    }
}