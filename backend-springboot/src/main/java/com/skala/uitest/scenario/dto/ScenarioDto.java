package com.skala.uitest.scenario.dto;

import lombok.*;

import java.time.LocalDateTime;

import com.skala.uitest.project.domain.Project;

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
}
