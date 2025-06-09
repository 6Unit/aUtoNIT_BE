package com.skala.uitest.project.service;

import com.skala.uitest.project.domain.Project;
import com.skala.uitest.project.dto.ProjectRequestDto;
import com.skala.uitest.project.repository.ProjectRepository;
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

    public Project createProject(ProjectRequestDto dto) {
        Project project = Project.builder()
                .projectName(dto.getProjectName())
                .projectCode(dto.getProjectCode())
                .build();
        return projectRepository.save(project);
    }

    public void handleProjectFileUpload(Long projectId, MultipartFile requirementFile, MultipartFile sourceZipFile) throws IOException {
        String baseDir = "uploads/project-" + projectId;
        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File excelFile = new File(baseDir + "/requirement.xlsx");
        File zipFile = new File(baseDir + "/source_code.zip");

        requirementFile.transferTo(excelFile);
        sourceZipFile.transferTo(zipFile);
    }

    public Map<String, Boolean> getDetailedProjectStatus(Long projectId) {
        String baseDir = "uploads/project-" + projectId;

        // 업로드 파일 존재 여부
        File requirement = new File(baseDir + "/requirement.xlsx");
        File sourceZip = new File(baseDir + "/source_code.zip");

        // 생성 여부 판단 (시나리오/테스트케이스 CSV 기준)
        File scenario = new File(baseDir + "/통합테스트시나리오.csv");
        File testcase = new File(baseDir + "/테스트케이스.csv");

        Map<String, Boolean> statusMap = new HashMap<>();
        statusMap.put("requirementUploaded", requirement.exists());
        statusMap.put("sourceZipUploaded", sourceZip.exists());
        statusMap.put("scenarioGenerated", scenario.exists());
        statusMap.put("testcaseGenerated", testcase.exists());

        return statusMap;
    }


}
