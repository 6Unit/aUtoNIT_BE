package com.skala.uitest.testcase.dto;

import lombok.*;

import java.time.LocalDateTime;

import com.skala.uitest.scenario.domain.Scenario;
import com.skala.uitest.testcase.domain.TestCase;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCaseDto {
    private String testcaseId;
    private String scenarioId;
    private String testcaseName;
    private String uiFlow;
    private String inputData;
    private String expectedResult;
    private Boolean isSuccess;
    private LocalDateTime createdAt;

    public static TestCaseDto fromEntity(TestCase entity) {
        return TestCaseDto.builder()
            .testcaseId(entity.getTestcaseId())
            .scenarioId(entity.getScenario().getScenarioId())
            .testcaseName(entity.getTestcaseName())
            .uiFlow(entity.getUiFlow())
            .inputData(entity.getInputData())
            .expectedResult(entity.getExpectedResult())
            .isSuccess(entity.getIsSuccess())
            .createdAt(entity.getCreatedAt())
            .build();
    }

    public TestCase toEntity(Scenario scenario) {
        return TestCase.builder()
            .testcaseId(this.testcaseId)
            .scenario(scenario)
            .testcaseName(this.testcaseName)
            .uiFlow(this.uiFlow)
            .inputData(this.inputData)
            .expectedResult(this.expectedResult)
            .isSuccess(this.isSuccess)
            .createdAt(this.createdAt != null ? this.createdAt : LocalDateTime.now())
            .build();
    }

}