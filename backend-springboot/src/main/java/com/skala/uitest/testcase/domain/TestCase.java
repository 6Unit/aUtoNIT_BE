package com.skala.uitest.testcase.domain;

import com.skala.uitest.scenario.domain.Scenario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCase {

    @Id
    @Column(name = "testcase_id", length = 45, nullable = false)
    private String testcaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id", nullable = false)
    private Scenario scenario;

    @Column(name = "testcase_name", length = 100, nullable = false)
    private String testcaseName;

    @Column(name = "ui_flow", columnDefinition = "TEXT")
    private String uiFlow;

    @Column(name = "input_data", length = 200, nullable = false)
    private String inputData;

    @Column(name = "expected_result", columnDefinition = "TEXT", nullable = false)
    private String expectedResult;

    @Column(name = "is_success")
    private Boolean isSuccess;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void generateId() {
        if (this.testcaseId == null) {
            this.testcaseId = "tc-" + UUID.randomUUID().toString().substring(0, 8);
        }
    }
}
