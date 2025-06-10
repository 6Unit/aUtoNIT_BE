package com.skala.uitest.scenario.dto;

import lombok.*;
import com.skala.uitest.scenario.domain.Scenario;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScenarioDto {

    private String scenarioId;
    private String scenarioName;
    private String scenarioDescription;
    
    public Scenario toEntity() {
        return Scenario.builder()
                .scenarioId(this.scenarioId)
                .scenarioName(this.scenarioName)
                .scenarioDescription(this.scenarioDescription)
                .build();
    }
    public static ScenarioDto fromEntity(Scenario scenario) {
        return new ScenarioDto(
            scenario.getScenarioId(),
            scenario.getScenarioName(),
            scenario.getScenarioDescription()
        );
    }
    
}
