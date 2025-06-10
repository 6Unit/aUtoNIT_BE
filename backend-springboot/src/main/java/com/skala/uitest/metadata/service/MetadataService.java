package com.skala.uitest.metadata.service;

import com.skala.uitest.metadata.domain.Metadata;
import com.skala.uitest.metadata.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths; 
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MetadataService {

    @Value("${file.metadata-dir}")
    private String metadataDir;

    private final MetadataRepository metadataRepo;

    public void saveMetadata(String projectId, MultipartFile file) throws IOException {
        File projectDir = new File(metadataDir, projectId);
        if (!projectDir.exists()) projectDir.mkdirs();

        File saved = new File(projectDir, "metadata.json");
        file.transferTo(saved);

        Metadata metadata = new Metadata();
        metadata.setProjectId(projectId);
        metadata.setFilePath(saved.getAbsolutePath());
        metadata.setAnalyzedAt(LocalDateTime.now());

        metadataRepo.save(metadata);
    }

    public Resource getMetadata(String projectId) throws IOException {
        Metadata metadata = metadataRepo.findByProjectId(projectId)
                .orElseThrow(() -> new FileNotFoundException("Metadata not found"));
        return new UrlResource(Paths.get(metadata.getFilePath()).toUri());
    }
}

