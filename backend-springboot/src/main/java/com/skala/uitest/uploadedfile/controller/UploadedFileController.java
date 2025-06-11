package com.skala.uitest.uploadedfile.controller;

import com.skala.uitest.project.domain.Project;
import com.skala.uitest.project.repository.ProjectRepository;
import com.skala.uitest.uploadedfile.domain.UploadedFile;
import com.skala.uitest.uploadedfile.dto.UploadedFileResponseDto;
import com.skala.uitest.uploadedfile.enums.FileType;
import com.skala.uitest.uploadedfile.repository.UploadedFileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class UploadedFileController {

    private final UploadedFileRepository uploadedFileRepository;
    private final ProjectRepository projectRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final Logger log = LoggerFactory.getLogger(UploadedFileController.class);

    @PostConstruct
    public void init() {
        log.info("✅ uploadDir 설정값: {}", uploadDir);
    }

    @Operation(summary = "프로젝트 파일 업로드", description = "요구사항 엑셀, 소스 ZIP, (선택) 검증 파일 업로드")
    @PostMapping(path = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<?> uploadFiles(
            @PathVariable("id") Long projectId,
            @RequestParam("requirement") MultipartFile requirement,
            @RequestParam("source") MultipartFile source,
            @RequestParam(value = "validation", required = false) MultipartFile validation
    ) throws IOException {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found: " + projectId));

        List<UploadedFileResponseDto> uploadedFiles = new ArrayList<>();

        uploadedFiles.add(saveOrUpdateUploadedFile(requirement, FileType.REQUIREMENT, project));
        uploadedFiles.add(saveOrUpdateUploadedFile(source, FileType.SOURCE, project));

        if (validation != null && !validation.isEmpty()) {
            uploadedFiles.add(saveOrUpdateUploadedFile(validation, FileType.VALIDATION, project));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "파일 업로드 성공");
        response.put("files", uploadedFiles);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/files/{fileType}")
    @Operation(summary = "업로드된 파일 다운로드", description = "프로젝트 ID와 파일 타입으로 업로드된 파일을 조회합니다.")
    public ResponseEntity<?> downloadFile(
            @PathVariable("id") Long projectId,
            @PathVariable("fileType") FileType fileType
    ) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found: " + projectId));

        UploadedFile fileEntity = uploadedFileRepository.findByProjectAndFileType(project, fileType)
                .orElseThrow(() -> new RuntimeException("파일이 존재하지 않습니다: " + fileType));

        File file = new File(fileEntity.getFilePath());
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        try {
            byte[] fileBytes = java.nio.file.Files.readAllBytes(file.toPath());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                    .body(fileBytes);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("파일을 읽는 도중 오류가 발생했습니다.");
        }
    }


    private UploadedFileResponseDto saveOrUpdateUploadedFile(MultipartFile file, FileType type, Project project) throws IOException {
        // 저장 경로 조합 코드
        String dirPath = uploadDir + "/" + project.getProjectId() + "/"; // 예: /data/upload/1
        File dir = new File(dirPath);
        if (!dir.exists()) dir.mkdirs();

        // 저장 파일 이름
        String storedFileName = UUID.randomUUID() + "_" + file.getOriginalFilename(); // 예: f8c0a18e-12ab-4939-b123-dbd1234abcde_spec.xlsx
        
        // 최종 경로 
        String fullPath = dirPath + storedFileName; // 예: /data/upload/1/f8c0a18e-12ab-4939-b123-dbd1234abcde_spec.xlsx
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
