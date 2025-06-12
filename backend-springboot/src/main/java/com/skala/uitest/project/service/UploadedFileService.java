package com.skala.uitest.project.service;

import com.skala.uitest.project.domain.Project;
import com.skala.uitest.project.domain.UploadedFile;
import com.skala.uitest.project.dto.UploadedFileResponseDto;
import com.skala.uitest.project.enums.FileType;
import com.skala.uitest.project.repository.ProjectRepository;
import com.skala.uitest.project.repository.UploadedFileRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UploadedFileService {

    private final UploadedFileRepository uploadedFileRepository;
    private final ProjectRepository projectRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public List<UploadedFileResponseDto> uploadFiles(Long projectId,
                                                     MultipartFile requirement,
                                                     MultipartFile source,
                                                     MultipartFile validation) throws IOException {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found: " + projectId));

        List<UploadedFileResponseDto> result = new ArrayList<>();
        result.add(saveOrUpdateUploadedFile(requirement, FileType.REQUIREMENT, project));
        result.add(saveOrUpdateUploadedFile(source, FileType.SOURCE, project));

        if (validation != null && !validation.isEmpty()) {
            result.add(saveOrUpdateUploadedFile(validation, FileType.VALIDATION, project));
        }

        return result;
    }

    public ResponseEntity<?> downloadFile(Long projectId, FileType fileType) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found: " + projectId));

        UploadedFile fileEntity = uploadedFileRepository.findByProjectAndFileType(project, fileType)
                .orElseThrow(() -> new RuntimeException("파일이 존재하지 않습니다: " + fileType));

        File file = new File(fileEntity.getFilePath());
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        try {
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                    .body(fileBytes);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("파일을 읽는 도중 오류가 발생했습니다.");
        }
    }

    private UploadedFileResponseDto saveOrUpdateUploadedFile(MultipartFile file, FileType type, Project project) throws IOException {
        String dirPath = uploadDir + "/" + project.getProjectId() + "/";
        File dir = new File(dirPath);
        if (!dir.exists()) dir.mkdirs();

        String storedFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String fullPath = dirPath + storedFileName;
        file.transferTo(new File(fullPath));

        Optional<UploadedFile> existing = uploadedFileRepository.findByProjectAndFileType(project, type);

        UploadedFile fileEntity = existing.orElseGet(() -> UploadedFile.builder()
                .fileId(UUID.randomUUID().toString())
                .fileType(type)
                .project(project)
                .build());

        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFilePath(fullPath);
        fileEntity.setUploadedAt(LocalDateTime.now());

        uploadedFileRepository.save(fileEntity);

        return new UploadedFileResponseDto(type, file.getOriginalFilename());
    }
}
