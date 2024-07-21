package com.cozentus.training_tracking_application.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cozentus.training_tracking_application.model.Topic;
import com.cozentus.training_tracking_application.model.File;
import com.cozentus.training_tracking_application.repository.FileRepository;
import com.cozentus.training_tracking_application.repository.TopicRepository;


@Service
public class FileService {

    private final Path rootLocation = Paths.get("src/main/resources/static");

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private TopicRepository topicRepository;

    public void store(MultipartFile file, int topicId, String uploadedBy) throws Exception {
        try {
            // Ensure the directory exists
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            // Copy the file
            Path destinationFile = this.rootLocation.resolve(
                Paths.get(file.getOriginalFilename()))
                .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new Exception("Cannot store file outside current directory.");
            }

            try (var inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            // Save the file information in the database
            Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new Exception("Topic not found"));
            File topicFile = new File();
            topicFile.setFileName(file.getOriginalFilename());
            topicFile.setTopic(topic);
            fileRepository.save(topicFile);
        } catch (Exception e) {
            throw new Exception("Failed to store file: " + e.getMessage(), e);
        }
    }
}
