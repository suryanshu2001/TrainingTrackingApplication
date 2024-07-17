package com.cozentus.training_tracking_application.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cozentus.training_tracking_application.model.Topic;
import com.cozentus.training_tracking_application.model.File;
import com.cozentus.training_tracking_application.repository.FileRepository;
import com.cozentus.training_tracking_application.service.FileService;
import com.cozentus.training_tracking_application.service.TopicService;

@RestController
@RequestMapping("/topics")
@CrossOrigin
public class TopicController {

	@Autowired
	private TopicService topicService;

	@Autowired
	private FileService fileService;

	@Autowired
	private FileRepository fileRepository;

	@GetMapping
	public ResponseEntity<List<Topic>> getAllTopics() {
		List<Topic> topics = topicService.getAllTopics();
		return ResponseEntity.ok(topics);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Topic> getTopicById(@PathVariable Integer id) {
		Topic topic = topicService.getTopicById(id);
		if (topic != null) {
			return ResponseEntity.ok(topic);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/course/{id}")
	public ResponseEntity<Optional<List<Topic>>> getTopicByCourseId(@PathVariable Integer id) {
		Optional<List<Topic>> topics = topicService.getTopicsByCourseId(id);
		if (topics.isPresent()) {
			return ResponseEntity.ok(topics);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
		Topic createdTopic = topicService.saveTopic(topic);
		return ResponseEntity.ok(createdTopic);
	}

	
//	@GetMapping("/files/{id}")
//	public ResponseEntity<Resource> serveFile(@PathVariable int id) {
//		try {
//			TopicFile topicFile = topicFileRepository.findById(id).orElseThrow(() -> new Exception("File not found"));
//			Path file = Paths.get("src/main/resources/static").resolve(topicFile.getFileName());
//			Resource resource = new UrlResource(file.toUri());
//
//			if (resource.exists() || resource.isReadable()) {
//				String contentType = determineContentType(file); // Implement this method to determine content type
//																	// based on file extension
//
//				return ResponseEntity.ok()
//						.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + topicFile.getFileName() + "\"")
//						.contentType(MediaType.parseMediaType(contentType)).body(resource);
//			} else {
//				throw new Exception("Could not read file: " + topicFile.getFileName());
//			}
//		} catch (Exception e) {
//			return ResponseEntity.status(404).body(null);
//		}
//	}

//	private String determineContentType(Path file) {
//		String contentType = "application/octet-stream"; // Default content type
//
//		// Determine content type based on file extension
//		String fileName = file.getFileName().toString().toLowerCase();
//		if (fileName.endsWith(".pdf")) {
//			contentType = "application/pdf";
//		} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
//			contentType = "image/jpeg";
//		} else if (fileName.endsWith(".png")) {
//			contentType = "image/png";
//		} else if (fileName.endsWith(".txt")) {
//			contentType = "text/plain";
//		}
//		// Add more conditions as needed for other file types
//
//		return contentType;
//	}
	
//	@GetMapping("/{topicId}/files")
//    public ResponseEntity<List<Resource>> serveFiles(@PathVariable Integer topicId) {
//        try {
//            List<TopicFile> topicFiles = topicFileRepository.findByTopicId(topicId);
//            List<Resource> resources = new ArrayList<>();
//
//            for (TopicFile topicFile : topicFiles) {
//                Path file = Paths.get("src/main/resources/static").resolve(topicFile.getFileName());
//                Resource resource = new UrlResource(file.toUri());
//
//                if (resource.exists() || resource.isReadable()) {
//                    resources.add(resource);
//                } else {
//                    throw new Exception("Could not read file: " + topicFile.getFileName());
//                }
//            }
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .body(resources);
//        } catch (Exception e) {
//            return ResponseEntity.status(404).body(null);
//        }
//    }

    // Existing single file retrieval method
    @GetMapping("/files/{id}")
    public ResponseEntity<Resource> serveFile(@PathVariable int id) {
        try {
            File topicFile = fileRepository.findById(id)
                    .orElseThrow(() -> new Exception("File not found"));
            Path file = Paths.get("src/main/resources/static").resolve(topicFile.getFileName());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = determineContentType(file);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + topicFile.getFileName() + "\"")
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                throw new Exception("Could not read file: " + topicFile.getFileName());
            }
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    private String determineContentType(Path file) {
        String contentType = "application/octet-stream"; // Default content type

        String fileName = file.getFileName().toString().toLowerCase();
        if (fileName.endsWith(".pdf")) {
            contentType = "application/pdf";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            contentType = "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            contentType = "image/png";
        } else if (fileName.endsWith(".txt")) {
            contentType = "text/plain";
        }
        // Add more conditions as needed for other file types

        return contentType;
    }

	@PostMapping("/{id}/files")
	public ResponseEntity<String> uploadFiles(@PathVariable Integer id, @RequestParam("files") MultipartFile[] files,
			@RequestParam("uploadedBy") String uploadedBy) {
		try {
			for (MultipartFile file : files) {
				fileService.store(file, id, uploadedBy);
			}
			return ResponseEntity.ok().body("Files uploaded successfully");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Failed to upload files: " + e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Topic> updateTopic(@PathVariable Integer id, @RequestBody Topic topic) {
		topic.setTopicId(id);
		topic.setCreatedBy("admin");
    	topic.setUpdatedBy("admin");
    	topic.setCreatedDate(topicService.getTopicById(topic.getTopicId()).getCreatedDate());
    	topic.setUpdatedDate(new Date());
    	topic.setCourse(topicService.getTopicById(topic.getTopicId()).getCourse());
		Topic updatedTopic = topicService.saveTopic(topic);
		return ResponseEntity.ok(updatedTopic);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTopic(@PathVariable int id) {
		boolean deleted = topicService.deleteTopic(id);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
