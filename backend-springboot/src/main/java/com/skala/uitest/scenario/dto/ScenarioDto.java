package com.skala.uitest.scenario.dto;

import lombok.*;

import java.time.LocalDateTime;

import com.skala.uitest.project.domain.Project;
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
    private LocalDateTime createdAt;
    private Project project;
    
    public Scenario toEntity() {
        return Scenario.builder()
                .scenarioId(this.scenarioId)
                .scenarioName(this.scenarioName)
                .scenarioDescription(this.scenarioDescription)
                .createdAt(this.createdAt)
                .project(this.project)
                .build();
    }

}
