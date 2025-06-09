package com.skala.uitest.testcase.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCase {

    @Id
    @Column(name = "testcase_id", length = 45, nullable = false)
    private String testcaseId;

    @Column(name = "scenario_id", length = 45, nullable = false)
    private String scenarioId;

    @Column(name = "testcase_name", length = 100, nullable = false)
    private String testcaseName;

    @Column(name = "testcase_pre_flow", columnDefinition = "TEXT")
    private String testcasePreFlow;

    @Column(name = "testcase_input_data", length = 200, nullable = false)
    private String testcaseInputData;

    @Column(name = "testcase_expected", columnDefinition = "TEXT", nullable = false)
    private String testcaseExpected;

    @Column(name = "is_success")
    private Boolean isSuccess;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
