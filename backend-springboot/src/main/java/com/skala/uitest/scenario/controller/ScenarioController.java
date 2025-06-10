package com.skala.uitest.scenario.controller;

import com.skala.uitest.scenario.dto.ScenarioDto;
import com.skala.uitest.scenario.dto.ScenarioUpdateRequestDto;
import com.skala.uitest.scenario.service.ScenarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scenario")
@RequiredArgsConstructor
@Tag(name = "Scenario API", description = "시나리오 관리 API")
public class ScenarioController {

    private final ScenarioService scenarioService;

    @Operation(summary = "시나리오 전체 조회", description = "프로젝트 전체 시나리오 리스트를 조회합니다.")
    @GetMapping
    public List<ScenarioDto> getAllScenarios() {
        return scenarioService.getAllScenarios();
    }

    @Operation(summary = "시나리오 자동 생성", description = "요구사항 zip 기반으로 시나리오 리스트 생성 (LLM 호출)")
    @PostMapping("/generate")
    public List<ScenarioDto> generateScenarios(@RequestParam("projectId") Long projectId) {
        return scenarioService.generateScenarios(projectId);
    }


    @Operation(summary = "시나리오 수정", description = "시나리오 ID를 기준으로 시나리오명 및 내용을 수정합니다.")
    @PutMapping("/{id}")
    public void updateScenario(@PathVariable String id, @RequestBody ScenarioUpdateRequestDto dto) {
        scenarioService.updateScenario(id, dto);
    }

    @Operation(summary = "시나리오 삭제", description = "선택된 시나리오 ID 목록을 기준으로 삭제합니다.")
    @DeleteMapping
    public void deleteScenarios(@RequestBody List<String> ids) {
        scenarioService.deleteScenarios(ids);
    }

}
