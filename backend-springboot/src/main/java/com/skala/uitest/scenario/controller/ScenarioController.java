package com.skala.uitest.scenario.controller;

import com.skala.uitest.scenario.dto.ScenarioDto;
import com.skala.uitest.scenario.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scenario")
@RequiredArgsConstructor
public class ScenarioController {

    private final ScenarioService scenarioService;

    @GetMapping
    public List<ScenarioDto> getAllScenarios() {
        return scenarioService.getAllScenarios();
    }

    @PostMapping
    public void saveScenario(@RequestBody ScenarioDto dto) {
        scenarioService.saveScenario(dto);
    }

    @PutMapping("/{id}")
    public void updateScenario(@PathVariable String id, @RequestBody ScenarioDto dto) {
        scenarioService.updateScenario(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteScenario(@PathVariable String id) {
        scenarioService.deleteScenario(id);
    }
}
