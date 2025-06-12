package com.skala.uitest.scenario.service;

import org.springframework.stereotype.Service;

import com.skala.uitest.project.domain.UploadedFile;
import com.skala.uitest.project.enums.FileType;
import com.skala.uitest.project.repository.UploadedFileRepository;
import com.skala.uitest.scenario.domain.Scenario;
import com.skala.uitest.scenario.dto.ScenarioDto;
import com.skala.uitest.scenario.dto.ScenarioUpdateRequestDto;
import com.skala.uitest.scenario.repository.ScenarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final UploadedFileRepository fileRepository;
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
    public List<ScenarioDto> generateScenarios(Long projectId) {
        // 1. 요구사항 파일 조회 (.xlsx 파일이 저장되어 있어야 함)
        UploadedFile reqFile = fileRepository.findByProject_ProjectIdAndFileType(projectId, FileType.REQUIREMENT)
            .orElseThrow(() -> new RuntimeException("요구사항 파일이 존재하지 않습니다."));
    
        // 2. 파일 존재 여부 및 확장자 확인 (선택)
        if (!reqFile.getFilePath().endsWith(".xlsx")) {
            throw new RuntimeException("요구사항 파일은 .xlsx 형식이어야 합니다.");
        }
    
        // 3. FastAPI 서버 호출
        String url = "http://fastapi:8000/generate-scenarios";  // FastAPI 경로가 정확한지 확인 필요
    
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(reqFile.getFilePath()));
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ScenarioDto[]> response = restTemplate.postForEntity(url, requestEntity, ScenarioDto[].class);
    
        List<ScenarioDto> scenarios = Arrays.asList(response.getBody());
    
        // 4. DB에 저장
        for (ScenarioDto dto : scenarios) {
            scenarioRepository.save(dto.toEntity());
        }
    
        return scenarios;
    }
    
    // 단일 시나리오 수정
    public ScenarioDto updateScenario(String id, ScenarioUpdateRequestDto dto) {
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
