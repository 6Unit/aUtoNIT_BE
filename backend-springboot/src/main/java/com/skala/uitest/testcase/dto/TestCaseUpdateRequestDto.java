package com.skala.uitest.testcase.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCaseUpdateRequestDto {
    private String testcaseName;
    private String uiFlow;
    private String inputData;
    private String expectedResult;

    public static TestCaseUpdateRequestDto fromEntity(com.skala.uitest.testcase.domain.TestCase entity) {
        return TestCaseUpdateRequestDto.builder()
                .testcaseName(entity.getTestcaseName())
                .uiFlow(entity.getUiFlow())
                .inputData(entity.getInputData())
                .expectedResult(entity.getExpectedResult())
                .build();
    }
}



