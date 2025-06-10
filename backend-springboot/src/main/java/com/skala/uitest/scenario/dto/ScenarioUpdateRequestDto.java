package com.skala.uitest.scenario.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScenarioUpdateRequestDto {
    private String scenarioName;
    private String scenarioDescription;
}
