package com.skala.uitest.scenario.service;

import org.springframework.stereotype.Service;

import com.skala.uitest.project.repository.UploadedFileRepository;
import com.skala.uitest.scenario.domain.Scenario;
import com.skala.uitest.scenario.dto.ScenarioDto;
import com.skala.uitest.scenario.dto.ScenarioRequestDto;
import com.skala.uitest.scenario.repository.ScenarioRepository;
import com.skala.uitest.project.domain.Project;
import com.skala.uitest.project.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ProjectRepository projectRepository;
    private final ScenarioRepository scenarioRepository;
    private final RestTemplate restTemplate;

    // 전체 시나리오 조회(시나리오ID, 시나리오명, 시나리오 설명)
    public List<ScenarioDto> getScenariosByProjectId(Long projectId) {
        return scenarioRepository.findAllByProject_ProjectId(projectId).stream()
        .map(s -> ScenarioDto.builder()
                .scenarioId(s.getScenarioId())
                .scenarioName(s.getScenarioName())
                .scenarioDescription(s.getScenarioDescription())
                .build())
        .collect(Collectors.toList());
    }
    
    // 시나리오 생성 (LLM 호출)
    public ResponseEntity<?> delegateScenarioGeneration(Long projectId) {
        // URL 수정 필요
        String fastApiUrl = "http://localhost:8000/generate-and-save?project_id=" + projectId;

        // FastAPI에 요청 보냄 (본문 없이 쿼리파라미터만)
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(fastApiUrl, null, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (RestClientException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("FastAPI 호출 실패: " + ex.getMessage());
        }
    }
    
    // 단일 시나리오 수동 생성
    public ScenarioDto createScenario(Long projectId, ScenarioRequestDto dto) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트 ID가 존재하지 않습니다: " + projectId));

    long count = scenarioRepository.countByProject_ProjectId(projectId);  // 프로젝트별 시나리오 수
    String scenarioId = String.format("scn-%03d", count + 1);  // scn-001, scn-002 ...

    Scenario entity = Scenario.builder()
        .scenarioId(scenarioId)
        .scenarioName(dto.getScenarioName())
        .scenarioDescription(dto.getScenarioDescription())
        .createdAt(LocalDateTime.now())
        .project(project)
        .build();

    return ScenarioDto.fromEntity(scenarioRepository.save(entity));
    }

    // 단일 시나리오 수정
    public ScenarioDto updateScenario(String id, ScenarioRequestDto dto) {
        Scenario scenario = scenarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("시나리오를 찾을 수 없습니다."));
    
        scenario.setScenarioName(dto.getScenarioName());
        scenario.setScenarioDescription(dto.getScenarioDescription());
        Scenario updated = scenarioRepository.save(scenario);
    
        return ScenarioDto.fromEntity(updated);  // 엔티티를 DTO로 변환
    }
    

    // 단일 시나리오 삭제
    public void deleteScenarios(String id) {
        if (!scenarioRepository.existsById(id)) {
            throw new IllegalArgumentException("시나리오 ID " + id + "가 존재하지 않습니다.");
        }
        scenarioRepository.deleteById(id);
    }
}
