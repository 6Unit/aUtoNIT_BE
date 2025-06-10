package com.skala.uitest.project.service;

import com.skala.uitest.project.domain.Project;
import com.skala.uitest.project.dto.ProjectRequestDto;
import com.skala.uitest.project.repository.ProjectRepository;
import com.skala.uitest.scenario.repository.ScenarioRepository;
import com.skala.uitest.testcase.repository.TestCaseRepository;
import com.skala.uitest.uploadedfile.enums.FileType;
import com.skala.uitest.uploadedfile.repository.UploadedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UploadedFileRepository fileRepository;
    private final ScenarioRepository scenarioRepository;
    private final TestCaseRepository testCaseRepository;


    public Project createProject(ProjectRequestDto dto) {
        Project project = Project.builder()
                .projectName(dto.getProjectName())
                .projectCode(dto.getProjectCode())
                .build();
        return projectRepository.save(project);
    }

    public Map<String, Boolean> getDetailedProjectStatus(Long projectId) {
        Map<String, Boolean> statusMap = new HashMap<>();
    
        // 업로드 여부 (projectId 기준으로 검색)
        statusMap.put("requirementUploaded", fileRepository.existsByProject_ProjectIdAndFileType(projectId, FileType.REQUIREMENT));
        statusMap.put("sourceZipUploaded", fileRepository.existsByProject_ProjectIdAndFileType(projectId, FileType.SOURCE));
        statusMap.put("validationUploaded", fileRepository.existsByProject_ProjectIdAndFileType(projectId, FileType.VALIDATION));

    
        // // 생성 여부
        // statusMap.put("scenarioGenerated", scenarioRepository.hasCreatedScenario(projectId));
        // statusMap.put("testcaseGenerated", testCaseRepository.hasCreatedTestcase(projectId));
    
        return statusMap;
    }
    

}
