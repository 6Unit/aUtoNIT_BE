package com.skala.uitest.testcase.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCaseDetailDto {
    private String testcaseId;
    private String testcaseName;
    private String uiFlow;
    private String inputData;
    private String expectedResult;
}
