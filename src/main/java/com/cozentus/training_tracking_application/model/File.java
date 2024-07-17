package com.cozentus.training_tracking_application.model;

import java.util.Date;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Files")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private int fileId;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
    
    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    @Column(name = "file_name", nullable = false)
    private String fileName;

//    @Column(name = "file_path", nullable = false)
//    private String filePath;
    
//    @Column(name = "file_type", nullable = false)
//    private String fileType;

//    @Column(name = "uploaded_date", nullable = false)
//    private Date uploadedDate;
//
//    @Column(name = "uploaded_by", nullable = false)
//    private String uploadedBy;

}
