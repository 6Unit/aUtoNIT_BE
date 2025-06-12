package com.skala.uitest.testcase.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCaseCreateRequestDto {
    private String testcaseName;
    private String uiFlow;
    private String inputData;
    private String expectedResult;
}
