package com.skala.uitest.testcase.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCaseDto {

    private String testcaseId;
    private String scenarioId;
    private String testcaseName;
    private String testcasePreFlow;
    private String testcaseInputData;
    private String testcaseExpected;
    private Boolean isSuccess;
    private LocalDateTime createdAt;
}
