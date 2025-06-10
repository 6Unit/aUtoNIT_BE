package com.skala.uitest.uploadedfile.domain;

import com.skala.uitest.project.domain.Project;
import com.skala.uitest.uploadedfile.enums.FileType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "uploaded_file",
       uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "file_type"}))
public class UploadedFile {

    @Id
    @Column(length = 45)
    private String fileId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FileType fileType;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;
}
