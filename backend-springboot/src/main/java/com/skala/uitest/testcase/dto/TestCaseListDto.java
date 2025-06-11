package com.skala.uitest.testcase.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCaseListDto {
    private String testcaseId;
    private String testcaseName;
}
