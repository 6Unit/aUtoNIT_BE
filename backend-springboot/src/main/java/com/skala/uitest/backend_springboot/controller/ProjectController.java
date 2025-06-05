package com.skala.uitest.backend_springboot.controller;

import com.skala.uitest.backend_springboot.domain.Project;
import com.skala.uitest.backend_springboot.dto.ProjectRequestDto;
import com.skala.uitest.backend_springboot.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
