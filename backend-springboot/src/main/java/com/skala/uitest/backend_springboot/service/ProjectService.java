package com.skala.uitest.backend_springboot.service;

import com.skala.uitest.backend_springboot.domain.Project;
import com.skala.uitest.backend_springboot.dto.ProjectRequestDto;
import com.skala.uitest.backend_springboot.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project createProject(ProjectRequestDto dto) {
        Project project = Project.builder()
                .projectName(dto.getProjectName())
                .projectCode(dto.getProjectCode())
                .build();

        return projectRepository.save(project);
    }
}
