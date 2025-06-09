package com.skala.uitest.scenario.service;

import com.skala.uitest.scenario.domain.Scenario;
import com.skala.uitest.scenario.dto.ScenarioDto;
import com.skala.uitest.scenario.repository.ScenarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;

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

    public Scenario saveScenario(ScenarioDto dto) {
        Scenario scenario = Scenario.builder()
                .scenarioId(dto.getScenarioId())
                .scenarioName(dto.getScenarioName())
                .scenarioDescription(dto.getScenarioDescription())
                .createdAt(dto.getCreatedAt())
                .build();
        return scenarioRepository.save(scenario);
    }

    public Scenario updateScenario(String id, ScenarioDto dto) {
        Optional<Scenario> optional = scenarioRepository.findById(id);
        if (optional.isPresent()) {
            Scenario scenario = optional.get();
            scenario.setScenarioName(dto.getScenarioName());
            scenario.setScenarioDescription(dto.getScenarioDescription());
            return scenarioRepository.save(scenario);
        }
        throw new IllegalArgumentException("해당 ID의 시나리오가 존재하지 않습니다: " + id);
    }

    public void deleteScenario(String id) {
        scenarioRepository.deleteById(id);
    }
}
