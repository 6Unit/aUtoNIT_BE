package com.skala.uitest.scenario.dto;

import lombok.*;

import java.time.LocalDateTime;

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
}
