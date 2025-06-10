package com.skala.uitest.project.controller;

import com.skala.uitest.project.domain.Project;
import com.skala.uitest.project.dto.ProjectRequestDto;
import com.skala.uitest.project.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Project & Upload API", description = "프로젝트 & 업로드 관련 API")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "프로젝트 생성", description = "새로운 프로젝트 생성 (이름, 코드 입력)")
    public Project createProject(@RequestBody ProjectRequestDto dto) {
        return projectService.createProject(dto);
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "프로젝트 상태 확인", description = "업로드/시나리오/테스트케이스 생성 상태 확인")
    public ResponseEntity<?> getProjectStatus(@PathVariable("id") Long id) {
        return ResponseEntity.ok(projectService.getDetailedProjectStatus(id));
    }



}
