package com.skala.uitest.scenario.service;

import org.springframework.stereotype.Service;
import com.skala.uitest.scenario.domain.Scenario;
import com.skala.uitest.scenario.dto.ScenarioDto;
import com.skala.uitest.scenario.dto.ScenarioUpdateRequestDto;
import com.skala.uitest.scenario.repository.ScenarioRepository;
import com.skala.uitest.uploadedfile.domain.UploadedFile;
import com.skala.uitest.uploadedfile.enums.FileType;
import com.skala.uitest.uploadedfile.repository.UploadedFileRepository;

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

    public List<ScenarioDto> getAllScenarios() {
        return scenarioRepository.findAll().stream()
                .map(s -> ScenarioDto.builder()
                        .scenarioId(s.getScenarioId())
                        .scenarioName(s.getScenarioName())
                        .scenarioDescription(s.getScenarioDescription())
                        .createdAt(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ScenarioDto> generateScenarios(Long projectId) {
        UploadedFile reqFile = fileRepository.findByProject_ProjectIdAndFileType(projectId, FileType.REQUIREMENT)
            .orElseThrow(() -> new RuntimeException("요구사항 파일이 존재하지 않습니다."));

        String url = "http://fastapi:8000/generate-scenarios";
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(reqFile.getFilePath()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ScenarioDto[]> response = restTemplate.postForEntity(url, requestEntity, ScenarioDto[].class);

        List<ScenarioDto> scenarios = Arrays.asList(response.getBody());
        for (ScenarioDto dto : scenarios) {
            scenarioRepository.save(dto.toEntity());
        }

        return scenarios;
    }

    public void updateScenario(String id, ScenarioUpdateRequestDto dto) {
        Scenario scenario = scenarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("시나리오가 존재하지 않습니다."));

        scenario.setScenarioName(dto.getScenarioName());
        scenario.setScenarioDescription(dto.getScenarioDescription());

        scenarioRepository.save(scenario);
    }

    public void deleteScenarios(List<String> ids) {
        scenarioRepository.deleteAllById(ids);
    }
}
