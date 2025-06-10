package com.skala.uitest.metadata.controller;
import com.skala.uitest.metadata.service.MetadataService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import java.io.IOException;

@RestController
@RequestMapping("/api/metadata")
@RequiredArgsConstructor
@Tag(name = "Metadata API", description = "메타데이터 관련 API")
public class MetadataController {

    private final MetadataService metadataService;

    @PostMapping("/upload/{projectId}")
    public ResponseEntity<?> upload(
            @PathVariable String projectId,
            @RequestPart("file") MultipartFile file 
    ) throws IOException {
        metadataService.saveMetadata(projectId, file);
        return ResponseEntity.ok("Metadata saved.");
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Resource> download(@PathVariable String projectId) throws IOException {
        Resource file = metadataService.getMetadata(projectId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(file);
    }
}

